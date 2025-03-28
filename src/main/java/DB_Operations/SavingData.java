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
    private final GettingData gettingData;

    @Autowired
    public SavingData(SetSession setSession, GettingData gettingData) {
        this.setSession = setSession;
        this.gettingData = gettingData;
    }

    @Transactional
    public boolean saveMessage(String userMessage,String timeMessage, long chatId) {
        try {
            Session session = setSession.getSession();
            session.beginTransaction();

            User user = gettingData.getUserByChatId(chatId);

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
