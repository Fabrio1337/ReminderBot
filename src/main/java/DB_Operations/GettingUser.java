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
public class GettingUser {

    public GettingUser() {}

    public Object get(Session session, String chatId) {
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



}
