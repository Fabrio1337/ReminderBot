package DB_Operations;

import Entity.Message;
import Entity.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class GettingData {

    private final SetSession setSession;

    @Autowired
    public GettingData(SetSession setSession) {
        this.setSession = setSession;
    }

    @Transactional
    public List<Message> getMessage(String currentTime)
    {
        try {
            Session session = setSession.getSession();
            session.beginTransaction();

            Query query = session.createQuery(
                    "FROM Message m " +
                            "WHERE m.delayedMessageDate = :currentDate AND m.isDelayed = true");
            query.setParameter("currentDate", currentTime);

            List<Message> messages = query.list();

            session.getTransaction().commit();

            return messages;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Transactional
    public User getUserByChatId(long chatId) {
        Session session = setSession.getSession();
        session.beginTransaction();
        User user =(User) session.createQuery("FROM User WHERE UserChatId = :chatId")
                .setParameter("chatId", chatId)
                .uniqueResult();

        if (user == null) {
            user = new User();
            user.setChatId(chatId);
            session.persist(user);
        }
        session.getTransaction().commit();
        return user;
    }


}
