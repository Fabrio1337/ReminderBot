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

    private boolean setUser(String chatId)
    {
        try
        {
            User user = new User();
            session.beginTransaction();
            user.setChatId(Long.parseLong(chatId));
            session.persist(user);
            session.getTransaction().commit();
            return true;

        } catch (Exception e)
        {
            return false;
        }
        finally
        {
            session.close();
        }


    }

    private boolean getUser(String chatId)
    {

        try {
            session = setSession().openSession();
            session.beginTransaction();
            String hql = "from User where UserChatId = :chatId";
            User user = (User) session.createQuery(hql)
                    .setParameter("chatId", Long.parseLong(chatId))
                    .uniqueResult();

            if (user == null) {

                session.getTransaction().commit();
                session.close();
                return setUser(chatId);
            }

            session.getTransaction().commit();
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        finally
        {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

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

                boolean test = getUser(chatId);
                //Создаем объект класса SendMessage - наш будущий ответ пользователю
                SendMessage outMess = new SendMessage();

                //Добавляем в наше сообщение id чата, а также наш ответ
                outMess.setChatId(chatId);
                outMess.setText(response + " " + chatId + " " + test);

                //Отправка в чат
                execute(outMess);
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
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
