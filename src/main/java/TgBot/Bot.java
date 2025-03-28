package TgBot;



import SpringConfigs.SpringDBCfg;
import SpringConfigs.SpringTGCfg;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
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
    private boolean is_time_message = false;

    public Bot()
    {
        context = new AnnotationConfigApplicationContext(SpringTGCfg.class, SpringDBCfg.class);
        buttons = context.getBean("buttons",Buttons.class);
        messagesHandler = context.getBean("messagesHandler", MessagesHandler.class);
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
                String messageText = update.getMessage().getText();
                long chatId = update.getMessage().getChatId();
                String firstName = update.getMessage().getFrom().getFirstName();



                if(is_callback)
                {
                    SendMessage returnCallbackMessage = messagesHandler.saveMessageInDB(chatId, messageText);
                    execute(returnCallbackMessage);
                    is_callback = false;
                    is_time_message = true;
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
                System.out.println("is_time_message: " + is_time_message);

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
