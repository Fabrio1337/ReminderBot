package DB_Operations;

import Entity.Message;
import Entity.User;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DeletionData {

    private final SetSession setSession;

    @Autowired
    public DeletionData(SetSession setSession) {
        this.setSession = setSession;
    }

    @Transactional
    public void deleteMessage(Message message)
    {
        Session session = setSession.getSession();

        session.beginTransaction();

        session.delete(message);

        session.getTransaction().commit();

    }

    @Transactional
    public void deleteUser(User user)
    {
        Session session = setSession.getSession();
        session.beginTransaction();

        session.delete(user);

        session.getTransaction().commit();
    }
}
