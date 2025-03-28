package TgBot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

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
        SendMessage sendMessage = sendMessages.sendCompleteMessage(chatId);
        if(sendMessage != null)
        {
            messageSplit(message, chatId);
        }

        return sendMessage;
    }

    private void messageSplit(String message, long chatId)
    {
        String [] splitted = message.split("\n");

        String dateTime = splitted[splitted.length - 1].trim();
        String messageText = message.substring(0, message.lastIndexOf("\n")).trim();

        receiveMessages.saveMessage(messageText, dateTime, chatId);

    }



}
