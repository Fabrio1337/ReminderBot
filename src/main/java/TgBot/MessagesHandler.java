package TgBot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Component
@Scope("prototype")
public class MessagesHandler {
    private SendMessages sendMessages;
    private ReceiveMessages receiveMessages;

    @Autowired
    MessagesHandler(SendMessages sendMessages, ReceiveMessages receiveMessages)
    {
        this.sendMessages = sendMessages;
        this.receiveMessages = receiveMessages;
    }

    public SendMessage receiveMessage(String messageText, long chatId, InlineKeyboardMarkup keyboardMarkup,  List<String> startWords, List<String> stopWords,  String firstName)
    {
        if(startWords.contains(messageText))
        {
            sendMessages.setFirstName(firstName);
            SendMessage message =  sendMessages.sendSimpleMessage(chatId);

            message.setReplyMarkup(keyboardMarkup);


            return message;
        }
        else
        {
            SendMessage message = sendMessages.sendErrorMessage(chatId);

            return message;
        }
    }

    public SendMessage callbackMessage(long chatId, String callbackData)
    {
        if(callbackData.equals("delayed"))
        {
            SendMessage message = sendMessages.sendDelayedMessage(chatId);
            return message;
        }
        if(callbackData.equals("list_delayed"))
        {
            return null;
        }
        else
        {
            return null;
        }
    }

    public SendMessage saveMessageInDB(long chatId,String message)
    {
        SendMessage sendMessage = sendMessages.sendTimeMessage(chatId, message);

        return sendMessage;
    }

    public SendMessage timeCallbackMessage(long chatId)
    {
        SendMessage message;

        return null;
    }


}
