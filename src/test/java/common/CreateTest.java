package common;

import com.ssanggland.util.HibernateUtil;
import junit.framework.TestCase;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;

public class CreateTest extends TestCase {

    public static Session session;

    @Before
    public void openSession() {
        session = HibernateUtil.getSessionFactory().openSession();
        assertTrue(session.isOpen());
    }

    @After
    public void closeSession() {
        session.close();
        HibernateUtil.getSessionFactory().close();
    }

}
