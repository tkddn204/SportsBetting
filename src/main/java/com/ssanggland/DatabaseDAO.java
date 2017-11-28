package com.ssanggland;

import com.ssanggland.models.*;
import com.ssanggland.models.enumtypes.KindOfDividend;
import com.ssanggland.models.enumtypes.MatchStadium;
import com.ssanggland.util.HibernateUtil;
import com.ssanggland.views.DividendAlgorithm;
import com.ssanggland.views.LoginSession;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DatabaseDAO {
    private static Session session;

    public static void appOpenSession() {
        session = HibernateUtil.getSessionFactory().openSession();
    }

    public static void appStopSessions() {
        session.close();
        HibernateUtil.getSessionFactory().close();
    }

    public static boolean registerIdCheck(String userRegisterId) {
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from User u where u.loginId = ?");
        query.setParameter(0, userRegisterId);
        boolean isExisted = query.uniqueResult() != null;
        transaction.commit();
        return isExisted;
    }

    public static void registerCommit(String loginId, String userPassword, String userName) {
        Transaction transaction = session.beginTransaction();
        User user = new User(loginId, userPassword, userName);
        session.save(user);
        transaction.commit();
    }

    public static boolean loginCheck(String loginId, String userPassword) {
        // 쿼리 보내서 id랑 일치하는거 하나만 받음
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from User u" +
                " where u.loginId = ? and u.password = ?");
        query.setParameter(0, loginId);
        query.setParameter(1, userPassword);
        boolean isExisted = query.uniqueResult() != null;
        transaction.commit();
        return isExisted;
    }

    public static User getUser(String loginId) {
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from User u where u.loginId = ?");
        query.setParameter(0, loginId);
        User user = (User) query.uniqueResult();
        transaction.commit();
        return user;
    }

    public static Long getLeagueCount() {
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("select count(*) from League league");
        Long result = (Long) query.uniqueResult();
        System.out.println(result);
        transaction.commit();
        return result;
    }

    public static List<PlayMatch> getPlayMatchList() {
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from PlayMatch");
        List<PlayMatch> playMatchList = query.list();
        transaction.commit();
        return playMatchList;
    }

    public static List<Team> getRandomTeams(long randomLeagueId) {
        Random random = new Random();
        Query query = session.createQuery("from Team team" +
                                            " where team.league.id = ?");
        query.setParameter(0, randomLeagueId);
        List<Team> teamList = query.list();

        ArrayList<Team> resultTeamList = new ArrayList<>();

        // 홈팀
        int randomTeamId = random.nextInt(teamList.size());
        Team homeTeam = teamList.get(randomTeamId);
        resultTeamList.add(homeTeam);

        //어웨이팀
        do {
            randomTeamId = random.nextInt(teamList.size());
        } while(homeTeam.getId() == randomTeamId);
        resultTeamList.add(teamList.get(randomTeamId));

        return resultTeamList;
    }

    public static PlayMatch makeRandomPlayMatch() {
        long randomLeagueId = new Random().nextInt(getLeagueCount().intValue());
        List<Team> randomTeamList = getRandomTeams(randomLeagueId);

        Calendar calendar = Calendar.getInstance();
        Random random = new Random();
        Date startDate = calendar.getTime();
        calendar.add(Calendar.DATE, random.nextInt(10)+1);
        calendar.add(Calendar.HOUR_OF_DAY, random.nextInt(24));
        calendar.add(Calendar.SECOND, random.nextInt(60));
        Date endDate = calendar.getTime();

        PlayMatch playMatch = new PlayMatch(randomTeamList.get(0),
                randomTeamList.get(1),
                MatchStadium.randomMatchStadium(),
                startDate, endDate);
        session.save(playMatch);
        return playMatch;
    }

    public static List<PlayMatch> getRandomPlayMatchList() {
        List<PlayMatch> resultList = getPlayMatchList();
        if(resultList.isEmpty()) {
            Transaction transaction = session.beginTransaction();
            for (int i = 0; i < 20; i++) {
                makeRandomPlayMatch();
            }
            resultList = getPlayMatchList();
            transaction.commit();
        }
        return resultList;
    }

    public static List<Dividend> getDiviendList() {
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from Dividend");
        List<Dividend> dividendList = query.list();
        transaction.commit();
        return dividendList;
    }

    public static List<Dividend> getRandomDividendList() {
        List<PlayMatch> playMatchList = getPlayMatchList();
        List<Dividend> resultDividendList = getDiviendList();
        if(resultDividendList.isEmpty()) {
            Transaction transaction = session.beginTransaction();
            for (PlayMatch playMatch : playMatchList) {
                List<Double> dividendList = DividendAlgorithm.calculate(
                        playMatch.getHomeTeam().getOverall(),
                        playMatch.getAwayTeam().getOverall());
                Dividend dividendWin = new Dividend(KindOfDividend.WIN, playMatch,
                        dividendList.get(0));
                Dividend dividendDraw = new Dividend(KindOfDividend.DRAW, playMatch,
                        dividendList.get(1));
                Dividend dividendLose = new Dividend(KindOfDividend.LOSE, playMatch,
                        dividendList.get(2));
                session.save(dividendWin);
                session.save(dividendDraw);
                session.save(dividendLose);
            }
            transaction.commit();
        }
        return resultDividendList;
    }

    public static boolean bettingMoney(Dividend dividend, int money) {
        Transaction transaction = session.beginTransaction();
        User user = getUser(String.valueOf(LoginSession.getInstance().getSessionUserId()));
        if(user.getMoney() > money) {
            Betting betting = new Betting(user, dividend, money);
            user.setMoney(user.getMoney() - money);
            session.update(user);
            session.save(betting);
        } else {
            transaction.commit();
            return false;
        }
        transaction.commit();
        return true;
    }

    public static void loadLeagueTeamSQL() {
        Transaction transaction = session.beginTransaction();
        String buf;
        try (BufferedReader bufferedReader = new BufferedReader(
                new FileReader("leagueTeamList.sql"))){
            while((buf = bufferedReader.readLine()) != null) {
                session.createQuery(buf).executeUpdate();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        transaction.commit();
    }

    public static PlayMatch getPlayMatch(long playMatchId) {
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("From PlayMatch playMatch Where playMatch.id = ?");
        query.setParameter(0, playMatchId);
        PlayMatch result = (PlayMatch) query.uniqueResult();

        transaction.commit();
        return result;
    }
}
