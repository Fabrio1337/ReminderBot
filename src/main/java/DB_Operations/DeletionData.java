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
    public boolean deleteMessage(long id, long userId)
    {
        try {
            Session session = setSession.getSession();

            session.beginTransaction();

            int affectedRows = session.createQuery("delete from Message where id = :id and user.id = :userId")
                    .setParameter("id",(int) id)
                    .setParameter("userId",(int) userId)
                    .executeUpdate();


            session.getTransaction().commit();

            if(affectedRows > 0)
            {
                return true;
            }
            else {
                return false;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

}
