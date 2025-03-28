package DB_Operations;

import Entity.Message;
import Entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;


@Component
public class SetSession {
    private SessionFactory sessionFactory;
    private Session session;

    @PostConstruct
    private void initialize() {
        sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Message.class)
                .addAnnotatedClass(User.class)
                .buildSessionFactory();

        session = sessionFactory.getCurrentSession();
    }



    public Session getSession() {

        if (session == null || !session.isOpen()) {

            session = sessionFactory.getCurrentSession();
        }
        return session;
    }


    @PreDestroy
    public void cleanup() {
        if (session != null && session.isOpen()) {
            session.close();
        }
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
