package DB_Operations;

import Entity.Message;
import Entity.User;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;


@Component
public class SavingMessages {


    public SavingMessages() {}

    public boolean saveMessage(Session session, String userMessage,String timeMessage, long chatId) {
        try {
            session.beginTransaction();

            User user =(User) session.createQuery("FROM User WHERE UserChatId = :chatId")
                    .setParameter("chatId", chatId)
                    .uniqueResult();

            if (user == null) {
                user = new User();
                user.setChatId(chatId);
                session.persist(user);
            }


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


}
