package TgBot;



import TgBot.MessageActions.MessagesHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

@Component
public class Bot extends TelegramLongPollingBot {

    @Autowired
    private Buttons buttons;

    @Autowired
    private MessagesHandler messagesHandler;

    final private String BOT_TOKEN = "7902651303:AAFPjXFnWT3YFFTpHspVt_BUqET_plsAnwU";
    final private String BOT_NAME = "Rreminderr1Bot";

    private boolean is_callback = false;

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

                if (buttons.startWords().contains(messageText))
                {
                    is_callback = false;
                    SendMessage sendMessage = messagesHandler.receiveMessage(messageText,
                            chatId,
                            buttons.setSimpleKeyboardMarkup(),
                            buttons.startWords(),
                            buttons.stopWords(),firstName );
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
