package com.ssanggland;

import com.ssanggland.algorithms.ScoreAlgorithm;
import com.ssanggland.models.*;
import com.ssanggland.models.enumtypes.BettingState;
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

    private static User user;

    public static User getUser() {
        if (user == null) {
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("from User u where u.id = ?");
            query.setParameter(0, LoginSession.getInstance().getSessionUserId());
            user = (User) query.uniqueResult();
            transaction.commit();
        }
        return user;
    }

    public static User getUser(String loginId) {
        if (user == null) {
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("from User u where u.loginId = ?");
            query.setParameter(0, loginId);
            user = (User) query.uniqueResult();
            transaction.commit();
        }
        return user;
    }

    private static long leagueCount = 0L;

    public static long getLeagueCount() {
        if (leagueCount == 0L) {
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
        long randomLeagueId = new Random().nextInt(leagueCount == 0L ? 6 : (int) leagueCount);
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
        for (int day = 0; day < 10; day++) {
            Random random = new Random();
            int matchCount = random.nextInt(15) + 5;
            for (int i = 0; i < matchCount; i++) {
                PlayMatch playMatch = makeRandomPlayMatch(cal);
                playMatch.setPlayMatchResult(new PlayMatchResult(playMatch, -1, -1));
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
            Dividend dividendWin = new Dividend(KindOfDividend.HOME, playMatch,
                    dividendList.get(0));
            Dividend dividendDraw = new Dividend(KindOfDividend.DRAW, playMatch,
                    dividendList.get(1));
            Dividend dividendLose = new Dividend(KindOfDividend.AWAY, playMatch,
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
        betting.setBettingResult(new BettingResult(betting, 0));
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
        User user = getUser();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from Betting betting" +
                " where betting.user.id = ? and betting.dividend.playMatch.state != ?");
        query.setParameter(0, user.getId());
        query.setParameter(1, PlayMatchState.ENDGAME);
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

    public static void updatePlayMatchState() {
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from PlayMatch playMatch" +
                " where playMatch.endGameDate <= ? and playMatch.state != ?");
        query.setTimestamp(0, cal.getTime());
        query.setParameter(1, PlayMatchState.ENDGAME);
        List<PlayMatch> playMatchList = query.list();
        for (PlayMatch playMatch : playMatchList) {
            if (new Random().nextInt(20) > 0) {
                playMatch.setState(PlayMatchState.ENDGAME);
            } else {
                playMatch.setState(PlayMatchState.EXTEND);
            }
            session.update(playMatch);
            if(playMatch.getId() % 3 == 0) {
                session.flush();
            }
        }
        transaction.commit();
    }

    public static boolean isAlreadyBet(long playMatchId) {
//        User user = getUser();
        Transaction transaction = session.beginTransaction();
//        Query query = session.createQuery("select * from Betting betting" +
//                " where betting.user = ?");
//        query.setParameter(0, user);
//        List<Betting> bettingList = query.list();
//        for(Betting betting : bettingList) {
//            if(betting.getDividend().getPlayMatch().getId() == playMatchId) {
//                return false;
//            }
//        }

        Query query = session.createQuery("select count(*) from Betting betting" +
                " where betting.dividend.playMatch.id = ?");
        query.setParameter(0, playMatchId);
        Long result = (Long)query.uniqueResult();
        transaction.commit();
        return result > 0L;
    }

    public static List<Betting> getResultBettingList() {
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from Betting betting" +
                " where betting.user.id = ? and betting.dividend.playMatch.state = ?");
        query.setParameter(0, LoginSession.getInstance().getSessionUserId());
        query.setParameter(1, PlayMatchState.ENDGAME);
        List<Betting> resultBettingList = query.list();
        transaction.commit();
        return resultBettingList;
    }

    public static void updateMatchResultScore(PlayMatch playMatch, int[] scores) {
        playMatch.getPlayMatchResult().setHomeScore(scores[0]);
        playMatch.getPlayMatchResult().setAwayScore(scores[1]);
        session.update(playMatch);
    }

    public static void updateBettingStateAndcreateBettingResult(Betting betting, long resultMoney) {
        betting.getBettingResult().setResultMoney(resultMoney);
        if(resultMoney == 0L) {
            betting.setState(BettingState.FAIL);
        } else {
            betting.setState(BettingState.GOOD);
        }
        session.update(betting);
    }

    public static void makeRandomPlayMatchResult() {
        List<Betting> bettingList = getResultBettingList();
        Transaction transaction = session.beginTransaction();
        for (Betting betting : bettingList) {
            Dividend dividend = betting.getDividend();
            PlayMatch playMatch = dividend.getPlayMatch();
            Team homeTeam = playMatch.getHomeTeam();
            Team awayTeam = playMatch.getAwayTeam();

            // 아직 배팅결과가 안나왔으면 결과를 만듬
            if (betting.getState().equals(BettingState.YET)) {
                // 경기 결과 점수 (0 - home, 1 - away)
                int[] scores = ScoreAlgorithm.calculate(
                        homeTeam.getOverall(), awayTeam.getOverall());

                updateMatchResultScore(playMatch, scores);

                KindOfDividend matchKindOfDividend = scores[0] == scores[1] ? KindOfDividend.DRAW :
                        scores[0] > scores[1] ? KindOfDividend.HOME : KindOfDividend.AWAY;
                long resultMoney = dividend.getKindOfDividend().equals(matchKindOfDividend) ?
                        (long) (betting.getBettingMoney() * dividend.getDividendRate()) : 0L;

                updateBettingStateAndcreateBettingResult(betting, resultMoney);
            }
        }
        transaction.commit();
    }

    public static List<Betting> getEarnMoneyBettingList() {
        User user = getUser();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from Betting betting" +
                " where betting.user.id = ? and betting.bettingResult.isPaid = ?" +
                " and betting.dividend.playMatch.state = ?");

        query.setParameter(0, user.getId());
        query.setParameter(1, false);
        query.setParameter(2, PlayMatchState.ENDGAME);

        List<Betting> bettingList = query.list();
        transaction.commit();
        return bettingList;
    }

    public static void updateBettingResultIsPaid(Betting betting, boolean isPaid) {
        Transaction transaction = session.beginTransaction();
        betting.getBettingResult().setPaid(true);
        session.update(betting);
        transaction.commit();
    }
}
