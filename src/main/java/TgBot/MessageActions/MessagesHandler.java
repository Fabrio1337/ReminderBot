package TgBot.MessageActions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
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
    private final DeleteMessages deleteMessages;
    private boolean is_callback = false;
    private boolean is_command = false;
    private long message_id;

    @Autowired
    MessagesHandler(SendMessages sendMessages, ReceiveMessages receiveMessages, DeleteMessages deleteMessages)
    {
        this.sendMessages = sendMessages;
        this.receiveMessages = receiveMessages;
        this.deleteMessages = deleteMessages;
    }

    public SendMessage receiveMessage(String messageText, long chatId, InlineKeyboardMarkup keyboardMarkup,  List<String> startWords, List<String> stopWords,  String firstName)
    {
        if(startWords.contains(messageText))
        {
            sendMessages.setFirstName(firstName);
            SendMessage message =  sendMessages.sendSimpleMessage(chatId);

            message.setReplyMarkup(keyboardMarkup);

            setIs_callback(false);
            setIs_command(false);

            return message;
        }
        else if(messageText.equals("/command") || messageText.equals("command"))
        {
            setIs_callback(false);
            setIs_command(false);
            return sendMessages.sendCommandMessage(chatId);
        }
        else if(messageText.equals("/add") || messageText.equals("add"))
        {
            setIs_callback(true);
            setIs_command(false);
            return  sendMessages.sendDelayedMessage(chatId);
        }
        else if(messageText.equals("/list") || messageText.equals("list"))
        {
            setIs_command(false);
            return sendMessages.sendListDelayedMessages(chatId);
        }
        else if(messageText.equals("/remove") || messageText.equals("remove"))
        {
            setIs_command(true);
            return sendMessages.sendRemoveMessage(chatId);
        }
        else
        {
            setIs_callback(false);
            setIs_command(false);
            return sendMessages.sendErrorMessage(chatId);
        }
    }

    public SendMessage callbackMessage(long chatId, String callbackData)
    {
        if(callbackData.equals("delayed"))
        {

            return sendMessages.sendDelayedMessage(chatId);
        }
        else
        {

            return sendMessages.sendListDelayedMessages(chatId);
        }
    }

    public SendMessage saveMessageInDB(long chatId,String message)
    {
        return messageSplit(message, chatId);
    }

    public SendMessage removeMessageInDB(String message, long chatId)
    {
        try
        {
            boolean is_deleted = deleteMessages.deleteMessage(Long.parseLong(message), chatId);
            if(is_deleted)
            {
                setIs_command(false);
                return sendMessages.sendCompleteDeletedMessage(chatId);
            }
            else
            {
                return sendMessages.sendErrorFormatMessage(chatId);
            }
        }
        catch (NumberFormatException e)
        {
            return sendMessages.sendErrorFormatMessage(chatId);
        }
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

    public boolean isCommand() {
        return is_command;
    }

    public void setIs_command(boolean is_command) {
        this.is_command = is_command;
    }

    public void setMessage_id(long message_id) {
        this.message_id = message_id;
    }
}
