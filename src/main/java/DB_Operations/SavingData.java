package DB_Operations;

import Entity.Message;
import Entity.User;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class SavingData {

    private final SetSession setSession;

    @Autowired
    public SavingData(SetSession setSession) {
        this.setSession = setSession;
    }

    @Transactional
    public boolean saveMessage(String userMessage,String timeMessage, long chatId) {
        try {
            Session session = setSession.getSession();
            session.beginTransaction();

            User user = getUserByChatId(chatId);

            Message message = new Message(userMessage, timeMessage, true);

            message.setUser(user);



            session.persist(message);
            session.getTransaction().commit();

            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Transactional
    public User getUserByChatId(long chatId) {
        Session session = setSession.getSession();
        User user =(User) session.createQuery("FROM User WHERE UserChatId = :chatId")
                .setParameter("chatId", chatId)
                .uniqueResult();

        if (user == null) {
            user = new User();
            user.setChatId(chatId);
            session.persist(user);
        }
        return user;
    }
}
