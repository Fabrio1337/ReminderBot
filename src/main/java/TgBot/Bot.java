package TgBot;



import SpringConfigs.SpringTGCfg;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

public class Bot extends TelegramLongPollingBot {

    AnnotationConfigApplicationContext context;
    private Buttons buttons;

    final private String BOT_TOKEN = "7902651303:AAFPjXFnWT3YFFTpHspVt_BUqET_plsAnwU";
    final private String BOT_NAME = "Rreminderr1Bot";


    public Bot()
    {
        context = new AnnotationConfigApplicationContext(SpringTGCfg.class);
        buttons = context.getBean("buttons",Buttons.class);
        SendMessage message = new SendMessage();
        message.setText("Добро пожаловать! Выберите:");
        buttons.setSimpleKeyboardMarkup();
    }

/*
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
*/

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
                String messageText = update.getMessage().getText();
                long chatId = update.getMessage().getChatId();

                List<String> stopWords = buttons.stopWords(); //скоро будет доделано

                List<String> startWords = buttons.startWords();

                if(startWords.contains(messageText))
                {
                    SendMessage message = new SendMessage();
                    message.setText("Добро пожаловать! Выберите:");
                    message.setChatId(String.valueOf(chatId));
                    message.setReplyMarkup(buttons.setSimpleKeyboardMarkup());
                    execute(message);
                }

                SendMessage message = new SendMessage();



                String test = "test";
                message.setText(test);
                message.setChatId(String.valueOf(chatId));
                message.setReplyMarkup(buttons.setKeyboardMarkup());
                execute(message);

                System.out.println("сообщение отправлено");

            }
            if(update.hasCallbackQuery())
            {
                System.out.println("в условии");
                String callbackQuery = update.getCallbackQuery().getData();
                long chatId = update.getCallbackQuery().getMessage().getChatId();

                System.out.println("Полученный callbackData: " + callbackQuery);

                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(String.valueOf(chatId));

                sendMessage.setText(callbackQuery + " ваше слово");
                execute(sendMessage);

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
