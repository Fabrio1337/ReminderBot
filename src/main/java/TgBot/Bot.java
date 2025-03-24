package TgBot;

import Entity.User;
import Entity.Mess;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class Bot extends TelegramLongPollingBot {

    final private String BOT_TOKEN = "7902651303:AAFPjXFnWT3YFFTpHspVt_BUqET_plsAnwU";
    final private String BOT_NAME = "Rreminderr1Bot";
    private SessionFactory sessionFactory;
    private Session session;


    public Bot()
    {

    }

    private SessionFactory setSession()
    {
        sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Mess.class)
                .addAnnotatedClass(User.class)
                .buildSessionFactory();
        return sessionFactory;
    }

    private boolean setUser(Session locSession, User user)
    {
        try
        {
            locSession.beginTransaction();


        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        finally
        {
            session.close();
        }

        return false;
    }

    private boolean getUser(Session locSession, User user, String chatId)
    {
        String hql = String.format("from User where UserChatId = %d", Integer.parseInt(chatId));
        user = (User) session.createQuery(hql).uniqueResult();

        if (user == null) {
            user = new User(Integer.parseInt(chatId));
            session.persist(user);
            return false;
        }

        return true;
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        try{
            if(update.hasMessage() && update.getMessage().hasText())
            {

                //Извлекаем из объекта сообщение пользователя
                Message inMess = update.getMessage();
                //Достаем из inMess id чата пользователя
                String chatId = inMess.getChatId().toString();
                //Получаем текст сообщения пользователя, отправляем в написанный нами обработчик
                String response = parseMessage(inMess.getText());

                session = setSession().getCurrentSession();

                String hql = String.format("from User where UserChatId = %d", Integer.parseInt(chatId));

                User user = (User) session.createQuery(hql).uniqueResult();

                if (user == null) {
                    user = new User(Integer.parseInt(chatId));
                    session.persist(user);
                }

                //Создаем объект класса SendMessage - наш будущий ответ пользователю
                SendMessage outMess = new SendMessage();

                //Добавляем в наше сообщение id чата, а также наш ответ
                outMess.setChatId(chatId);
                outMess.setText(response + " " + chatId);

                session.getTransaction().commit();

                //Отправка в чат
                execute(outMess);
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        finally {
            if (session != null && session.isOpen()) {
                session.close(); // Закрываем только сессию
            }
        }
    }
    public String parseMessage(String textMsg) {
        String response;

        //Сравниваем текст пользователя с нашими командами, на основе этого формируем ответ
        if(textMsg.equals("/start"))
            response = "Приветствую. Жми /get";
        else if(textMsg.equals("/get"))
            response = "тест";
        else
            response = "Сообщение не распознано";

        return response;
    }
}
