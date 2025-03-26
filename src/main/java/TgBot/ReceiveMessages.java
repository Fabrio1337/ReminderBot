package TgBot;

import DB_Operations.*;

import Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;

@Component
@Scope("prototype")
public class ReceiveMessages {
    private SavingMessages savingMessages;
    private SavingUser savingUser;
    private MessagesHandler messagesHandler;

    @Autowired
    public ReceiveMessages(SavingMessages savingMessages, SavingUser savingUser,  MessagesHandler messagesHandler) {
        this.savingMessages = savingMessages;
        this.savingUser = savingUser;
        this.messagesHandler = messagesHandler;
    }

    public SendMessage receiveMessage(String messageText, long chatId, List<String> startWords, List<String> stopWords, InlineKeyboardMarkup keyboardMarkup)
    {
        if(startWords.contains(messageText))
        {
            SendMessage message = new SendMessage();
            message.setText("Добро пожаловать! Выберите:");
            message.setChatId(String.valueOf(chatId));
            message.setReplyMarkup(keyboardMarkup);
            //execute(message); --будет заменен на соответствующий метод из класса MessagesHandler
            return message; //просто затычка которая будет заменена на результат метода из класса MessagesHandler
        }
        else
        {
            SendMessage message = new SendMessage();
            message.setText("Я вас не понимаю :( Список моих команд /command");
            return message; //просто затычка которая будет заменена на результат метода из класса MessagesHandler
        }
    }

    public boolean saveMessage(String message) {
        try
        {

            return  true;
        }
        catch(Exception e)
        {
            return false;
        }
    }

    public boolean saveUser(User user) {
        try
        {

            return  true;
        }
        catch(Exception e)
        {
            return false;
        }
    }




}
