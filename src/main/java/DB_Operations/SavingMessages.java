package DB_Operations;

import Entity.Mess;
import Entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class SavingMessages {
    private SessionFactory sessionFactory;
    private Session session;

    @PostConstruct
    private void initialize() {
        sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Mess.class)
                .addAnnotatedClass(User.class)
                .buildSessionFactory();
        session = sessionFactory.getCurrentSession();
    }

    public SavingMessages() {}

    public boolean save(Mess mess) {
        try
        {
            session.beginTransaction();
            session.save(mess);
            session.getTransaction().commit();
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }


    @PreDestroy
    private void close() {
        if (session != null) session.close();
        if (sessionFactory != null) sessionFactory.close();
    }
}
