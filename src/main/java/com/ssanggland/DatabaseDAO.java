package com.ssanggland;

import com.ssanggland.models.User;
import com.ssanggland.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
        User user = (User) query.uniqueResult();
        transaction.commit();
        return user != null;
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
        Query query = session.createQuery("from User u where u.loginId = ?");
        query.setParameter(0, loginId);
        User user = (User) query.uniqueResult();
        transaction.commit();
        if (user == null || !user.getPassword().equals(userPassword)) {
            return false;
        }
        return true;
    }

    public static User getUser(String loginId) {
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from User u where u.loginId = ?");
        query.setParameter(0, loginId);
        User user = (User) query.uniqueResult();
        transaction.commit();
        return user;
    }
}
