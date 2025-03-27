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
public class SavingMessages {


    public SavingMessages() {}

    public boolean save(Session session, Message message) {
        try
        {
            session.beginTransaction();
            session.save(message);
            session.getTransaction().commit();
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }


}
