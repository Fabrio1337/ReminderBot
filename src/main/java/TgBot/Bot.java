package TgBot;



import TgBot.MessageActions.MessagesHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;


@Component
public class Bot extends TelegramLongPollingBot {

    @Autowired
    private Buttons buttons;

    @Autowired
    private MessagesHandler messagesHandler;

    final private String BOT_TOKEN = "7600893575:AAFc6McadnqNN1uyZK7-waK4y17FMlli0dA";
    final private String BOT_NAME = "Rreminderr1Bot";

    private boolean is_callback = false;
    private boolean is_command = false;

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
            if(update.hasMessage() && update.getMessage().hasText()) {
                String messageText = update.getMessage().getText();
                long chatId = update.getMessage().getChatId();
                String firstName = update.getMessage().getFrom().getFirstName();

                if (buttons.startWords().contains(messageText) || buttons.getCommands().contains(messageText))
                {
                    SendMessage sendMessage = messagesHandler.receiveMessage(messageText,
                            chatId,
                            buttons.setSimpleKeyboardMarkup(),
                            buttons.startWords(),
                            buttons.stopWords(),firstName );
                    is_callback = messagesHandler.isCallback();
                    is_command = messagesHandler.isCommand();
                    execute(sendMessage);
                }
                else if(is_command)
                {
                    SendMessage sendMessage = messagesHandler.removeMessageInDB(messageText, chatId);
                    is_command = messagesHandler.isCommand();
                    execute(sendMessage);
                }
                else if(is_callback)
                {
                    SendMessage returnCallbackMessage = messagesHandler.saveMessageInDB(chatId, messageText);
                    is_callback = messagesHandler.isCallback();
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

            }
            if(update.hasCallbackQuery())
            {

                long callbackChatId = update.getCallbackQuery().getMessage().getChatId();
                String callbackData = update.getCallbackQuery().getData();

                SendMessage sendMessage = messagesHandler.callbackMessage(callbackChatId, callbackData);

                execute(sendMessage);


                is_callback = true;

            }
        } catch (TelegramApiException e) {
            System.out.println(e.getMessage());
        }

    }


}
