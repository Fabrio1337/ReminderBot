package TgBot.MessageActions;

import DB_Operations.GettingData;
import Entity.Message;
import Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

@Service
@Scope("prototype")
public class SendMessages
{
    private String firstName;

    private GettingData gettingData;

    @Autowired
    public SendMessages(GettingData gettingData)
    {
        this.gettingData = gettingData;
    }

    public SendMessage sendSimpleMessage(long chatId)
    {
        SendMessage sendMessage = new SendMessage();

        sendMessage.setText(firstName + ", Добро пожаловать!\n" +
                "Я - бот для напоминаний \uD83D\uDE0A \n" +
                "чтобы посмотреть все мои команды, напишите /command\n" +
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

    public SendMessage sendCommandMessage(long chatId)
    {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Мои команды:\n" +
                "/add - добавить напоминание \n" +
                "/list - посмотреть все напоминания\n" +
                "/remove - удалить напоминание");
        sendMessage.setChatId(String.valueOf(chatId));
        return sendMessage;
    }

    public SendMessage sendCompleteDeletedMessage(long chatId){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText("Ваше сообщение удалено, и не будет отправлено позже*☺\uFE0F*\n\n");
        sendMessage.setParseMode("Markdown");

        return sendMessage;
    }

    public SendMessage sendErrorFormatMessage(long chatId)
    {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText("*❌ Вы ввели неправильный ID.*\n\n" +
                "Пожалуйста введите корректный ID для ваших сообщений.");
        sendMessage.setParseMode("Markdown");

        return sendMessage;
    }

    public SendMessage sendDelayedMessage(long chatId)
    {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setParseMode("Markdown");
        sendMessage.setText("📋 *Что напомнить?*\n" +
                "Напишите сообщение, а в последней строке укажите дату и время для напоминания.\n\n" +
                "📌 *Формат:* `DD.MM.YYYY HH:mm` \n" +
                "*Например:* '`01.01.1999 12:00`' ");
        sendMessage.setChatId(String.valueOf(chatId));
        return sendMessage;
    }

    public SendMessage sendCompleteMessage(long chatId)
    {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Хорошо☺\uFE0F\n" +
                "Я запомнил ваше сообщение и время\uD83D\uDCCE");
        sendMessage.setChatId(String.valueOf(chatId));

        return sendMessage;
    }

    public SendMessage sendErrorTimeMessage(long chatId)
    {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText("*❌ Вы ввели неправильную дату и время.*\n\n" +
                "⏳ Пожалуйста, введите дату и время, которые _не раньше текущего момента_.");
        sendMessage.setParseMode("Markdown");

        return sendMessage;
    }

    public SendMessage sendRemoveMessage(long chatId)
    {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setParseMode("Markdown");
        sendMessage.setText("*❌Введите id сообщения которое хотите удалить.*\n\n");
        return sendMessage;
    }

    public SendMessage sendListDelayedMessages(long chatId)
    {
        SendMessage sendMessage = new SendMessage();
        User user = gettingData.getUserByChatId(chatId);
        List<Message> messages = user.getMessages();
        StringBuilder builder = new StringBuilder();
        if(!messages.isEmpty())
        {
            builder.append("Ваши отложенные сообщения:\n");
            for (Message message : messages)
            {
                builder.append("⚪\uFE0F " + message.toString() + " \n\n");
            }
        }
        else
        {
            builder.append("У вас нет отложенных сообщений!\uD83D\uDE22");
        }

        sendMessage.setText(builder.toString());
        sendMessage.setChatId(String.valueOf(chatId));

        return sendMessage;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
