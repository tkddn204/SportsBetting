package com.ssanggland;

import com.ssanggland.models.*;
import com.ssanggland.models.enumtypes.KindOfDividend;
import com.ssanggland.models.enumtypes.MatchStadium;
import com.ssanggland.models.enumtypes.PlayMatchState;
import com.ssanggland.util.HibernateUtil;
import com.ssanggland.algorithms.DividendAlgorithm;
import com.ssanggland.util.LoginSession;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.*;
import java.util.*;

import static com.ssanggland.Main.cal;

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

    public static User getUser() {
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from User u where u.id = ?");
        query.setParameter(0, LoginSession.getInstance().getSessionUserId());
        User user = (User) query.uniqueResult();
        transaction.commit();
        return user;
    }


    public static User getUser(String loginId) {
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from User u where u.loginId = ?");
        query.setParameter(0, loginId);
        User user = (User) query.uniqueResult();
        transaction.commit();
        return user;
    }

    private static long leagueCount = 0L;
    public static long getLeagueCount() {
        if(leagueCount == 0L) {
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("select count(*) from League league");
            leagueCount = (long) query.uniqueResult();
            transaction.commit();
        }
        return leagueCount;
    }

    public static List<PlayMatch> getPlayMatchList(Calendar cal) {
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from PlayMatch as playMatch" +
                " where playMatch.kickoffDate between :from and :to");

        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 1);
        query.setTimestamp("from", cal.getTime());

        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        query.setTimestamp("to", cal.getTime());

        List<PlayMatch> playMatchList = query.list();
        transaction.commit();
        return playMatchList;
    }

    public static List<Team> makeRandomTeams(long randomLeagueId) {
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
        } while (homeTeam.getId() == randomTeamId);
        resultTeamList.add(teamList.get(randomTeamId));

        return resultTeamList;
    }

    public static PlayMatch makeRandomPlayMatch(Calendar cal) {
        long randomLeagueId = new Random().nextInt(leagueCount == 0L ? 6 : (int)leagueCount);
        List<Team> randomTeamList = makeRandomTeams(randomLeagueId);

        Random random = new Random();
        cal.set(Calendar.HOUR_OF_DAY, random.nextInt(24));
        cal.set(Calendar.MINUTE, random.nextInt(60));
//        Date startDate = cal.getTime();
//        Date endDate = cal.getTime();

        PlayMatch playMatch = new PlayMatch(randomTeamList.get(0),
                randomTeamList.get(1),
                MatchStadium.randomMatchStadium(),
                cal.getTime(), cal.getTime());
        return playMatch;
    }

    public static void getRandomPlayMatchList(Calendar cal) {
        Transaction transaction = session.beginTransaction();
        for(int day = 0; day < 10; day++) {
            Random random = new Random();
            int matchCount = random.nextInt(15) + 5;
            for (int i = 0; i < matchCount; i++) {
                PlayMatch playMatch = makeRandomPlayMatch(cal);
                playMatch.setDividendList(makeRandomDividendList(playMatch));
                session.save(playMatch);
                if (i % (matchCount / 3) == 0) { //20, same as the JDBC batch size
                    //flush a batch of inserts and release memory:
                    session.flush();
                    session.clear();
                }
            }
            cal.add(Calendar.DATE, 1);
        }
        transaction.commit();
    }

    public static List<Dividend> getDiviendList(PlayMatch playMatch) {
        Query query = session.createQuery("from Dividend dividend" +
                " where dividend.playMatch.id = ?");
        query.setParameter(0, playMatch.getId());
        List<Dividend> dividendList = query.list();
        return dividendList;
    }

    public static List<Dividend> makeRandomDividendList(PlayMatch playMatch) {
        List<Dividend> resultDividendList = getDiviendList(playMatch);
        if (resultDividendList.isEmpty()) {
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
        return resultDividendList;
    }

    public static boolean bettingMoney(Dividend dividend, long money) {
        User user = getUser();
        if (user.getMoney() < money) {
            return false;
        }
        Transaction transaction = session.beginTransaction();
        Betting betting = new Betting(user, dividend, money, cal.getTime());
        user.setMoney(user.getMoney() - money);
        session.update(user);
        session.save(betting);
        transaction.commit();
        return true;
    }

    public static PlayMatch getPlayMatch(long playMatchId) {
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("From PlayMatch playMatch Where playMatch.id = ?");
        query.setParameter(0, playMatchId);
        PlayMatch result = (PlayMatch) query.uniqueResult();

        transaction.commit();
        return result;
    }

    public static void loadLeagueTeamSQL(String sqlFilePath) {
        Transaction transaction = session.beginTransaction();

        try (BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(new FileInputStream(sqlFilePath),
                        "UTF-8"))) {
            String str;
            while ((str = bufferedReader.readLine()) != null) {
//                System.out.println(str);
                session.createSQLQuery(str).executeUpdate();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        transaction.commit();
    }

    public static List<Betting> getBettingList() {
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from Betting");
        List<Betting> resultList = query.list();
        transaction.commit();
        return resultList;
    }

    public static boolean chargeMoney(long money) {
        User user = getUser();
        if (user != null) {
            Transaction transaction = session.beginTransaction();
            user.setMoney(user.getMoney() + money);
            session.update(user);
            transaction.commit();
            return true;
        } else {
            return false;
        }
    }

    public static void deleteBettingAndEarnUserMoney() {
        User user = getUser();
        Transaction transaction = session.beginTransaction();
        Set<Betting> bettings = user.getBettings();
        bettings.forEach((betting) -> {
            if(cal.after(betting.getDividend().getPlayMatch().getEndGameDate())) {
                if(betting.getDividend().getPlayMatch().getState().equals(PlayMatchState.ENDGAME)) {
                    long earnMoney = (long) (betting.getBettingMoney() * betting.getDividend().getDividendRate());
                    user.setMoney(user.getMoney() + earnMoney);
                }
                if (cal.getTime().getTime() > betting.getBettingTime().getTime() + 172800000L) {
                    session.delete(betting);
                }
            }
        });
        transaction.commit();
    }
}
