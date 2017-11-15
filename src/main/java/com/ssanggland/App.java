package com.ssanggland;

import com.ssanggland.models.User;
import com.ssanggland.util.HibernateUtil;
import com.ssanggland.views.Main;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Date;

public class App {
    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Main.main(args);
        session.close();
        HibernateUtil.getSessionFactory().close();
    }
}
