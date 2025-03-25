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
public class GettingUser {
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

    public GettingUser() {}

    public Object get(String chatId) {
        try
        {
            session.beginTransaction();
            String hql = "from User where UserChatId = :chatId";
            User user = (User) session.createQuery(hql)
                    .setParameter("chatId", Long.parseLong(chatId))
                    .uniqueResult();

            return user;
        }
        catch (Exception e)
        {
            return null;
        }
    }


    @PreDestroy
    private void close() {
        if (session != null) session.close();
        if (sessionFactory != null) sessionFactory.close();
    }
}
