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
public class SavingUser {

    public SavingUser() {}

    public boolean save(Session session, User user) {
        try
        {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }



}
