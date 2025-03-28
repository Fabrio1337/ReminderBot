package TgBot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Scope("prototype")
public class MessagesHandler {
    private final SendMessages sendMessages;
    private final ReceiveMessages receiveMessages;
    private boolean is_callback = false;

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
            return sendMessages.sendErrorMessage(chatId);
        }
    }

    public SendMessage callbackMessage(long chatId, String callbackData)
    {
        if(callbackData.equals("delayed"))
        {

            return sendMessages.sendDelayedMessage(chatId);
        }
        else if(callbackData.equals("list_delayed"))
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
        return messageSplit(message, chatId);
    }

    private SendMessage messageSplit(String message, long chatId)
    {
        try
        {
            String [] splitted = message.split("\n");

            String dateTime = splitted[splitted.length - 1].trim();
            String messageText = message.substring(0, message.lastIndexOf("\n")).trim();


            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");


            LocalDateTime localDateTime = LocalDateTime.now();

            LocalDateTime delayedDateTime = LocalDateTime.parse(dateTime, formatter);

            if(delayedDateTime.isAfter(localDateTime))
            {
                receiveMessages.saveMessage(messageText, dateTime, chatId);
                setIs_callback(false);
                return sendMessages.sendCompleteMessage(chatId);

            }
            else
            {
                setIs_callback(true);
                return sendMessages.sendErrorTimeMessage(chatId);
            }
        }
        catch(DateTimeException e)
        {
            setIs_callback(true);
            return sendMessages.sendErrorTimeMessage(chatId);
        }

    }

    private void setIs_callback(boolean is_callback)
    {
        this.is_callback = is_callback;
    }

    public boolean isCallback()
    {
        return is_callback;
    }




}
