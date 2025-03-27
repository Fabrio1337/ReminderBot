package TgBot;

import DB_Operations.GettingMessages;
import DB_Operations.GettingUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
@Scope("prototype")
public class SendMessages
{
    private GettingMessages gettingMessages;
    private GettingUser gettingUser;
    private String firstName;

    @Autowired
    public SendMessages(GettingMessages gettingMessages, GettingUser gettingUser)
    {
        this.gettingMessages = gettingMessages;
        this.gettingUser = gettingUser;
    }

    public SendMessage sendSimpleMessage(long chatId)
    {
        SendMessage sendMessage = new SendMessage();

        sendMessage.setText(firstName + ", Добро пожаловать!\n" +
                "Я - бот для напоминаний \uD83D\uDE0A \n" +
                "Все мои команды '/command'\n" +
                "Если хотите  сразу отложить сообщение, то выберите: ");
        sendMessage.setChatId(String.valueOf(chatId));

        return sendMessage;

    }

    public SendMessage sendErrorMessage(long chatId)
    {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Я вас не понимаю \uD83D\uDE1E \nСписок моих команд /command");
        sendMessage.setChatId(String.valueOf(chatId));
        return sendMessage;
    }

    public SendMessage sendDelayedMessage(long chatId)
    {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("\uD83D\uDCCB Что напомнить? Напишите сообщение:");
        sendMessage.setChatId(String.valueOf(chatId));
        return sendMessage;
    }

    public SendMessage sendTimeMessage(long chatId, String message)
    {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Сохранил ваше сообщение для напоминаний\uD83D\uDC4C \n" +
                "напишите теперь дату когда нужно напомнить\uD83D\uDCCB \n" +
                "P.S. пишите в таком формате DD.MM.YYYY HH.MM\n" +
                "P.S.S расшифровка: день.месяц.год час.минута");
        sendMessage.setChatId(String.valueOf(chatId));

        return  sendMessage;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
