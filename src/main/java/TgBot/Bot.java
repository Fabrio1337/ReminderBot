package TgBot;



import SpringConfigs.SpringDBCfg;
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
    private MessagesHandler messagesHandler;

    final private String BOT_TOKEN = "7902651303:AAFPjXFnWT3YFFTpHspVt_BUqET_plsAnwU";
    final private String BOT_NAME = "Rreminderr1Bot";

    private boolean is_callback = false;

    public Bot()
    {
        context = new AnnotationConfigApplicationContext(SpringTGCfg.class, SpringDBCfg.class);
        buttons = context.getBean("buttons",Buttons.class);
        messagesHandler = context.getBean("messagesHandler", MessagesHandler.class);
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
                String firstName = update.getMessage().getFrom().getFirstName();



                if(is_callback)
                {
                    SendMessage returnCallbackMessage = messagesHandler.saveMessageInDB(chatId, messageText);
                    execute(returnCallbackMessage);
                }
                else
                {
                    SendMessage sendMessage = messagesHandler.receiveMessage(messageText,
                            chatId,
                            buttons.setSimpleKeyboardMarkup(),
                            buttons.startWords(),
                            buttons.stopWords(),firstName );

                    execute(sendMessage);
                }

                System.out.println("сообщение отправлено");

            }
            if(update.hasCallbackQuery())
            {

                long callbackChatId = update.getCallbackQuery().getMessage().getChatId();
                String callbackData = update.getCallbackQuery().getData();

                System.out.println("Полученный callbackData: " + callbackData);

                SendMessage sendMessage = messagesHandler.callbackMessage(callbackChatId, callbackData);

                execute(sendMessage);

                is_callback = true;
                System.out.println(is_callback);

            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

}
