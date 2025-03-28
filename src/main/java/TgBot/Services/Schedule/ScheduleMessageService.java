package TgBot.Services.Schedule;

import DB_Operations.GettingData;
import DB_Operations.SetSession;
import Entity.Message;
import Entity.User;
import TgBot.Bot;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ScheduleMessageService {

    private SetSession setSession;

    private GettingData gettingData;

    private  Bot bot;

    @Autowired
    public ScheduleMessageService(Bot bot, SetSession setSession, GettingData gettingData) {
        this.bot = bot;
        this.setSession = setSession;
        this.gettingData = gettingData;
    }

    public ScheduleMessageService() {}

    @Transactional
    @Scheduled(fixedRate = 30000)
    public void scheduleMessage() {
        try {
            List<Message> messages = gettingData.getMessage(getCurrentDateAsString());

            Session session = setSession.getSession();

            session.beginTransaction();

            for (Message message : messages) {
                try {
                    SendMessage telegramMessage = new SendMessage();
                    telegramMessage.setChatId(String.valueOf(message.getUser().getChatId()));
                    telegramMessage.setText("Вы просили напомнить\uD83D\uDE0A:\n" + message.getMessage());

                    bot.execute(telegramMessage);

                    session.delete(message);

                    session.flush();

                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }

            session.getTransaction().commit();

        } catch (Exception e) {
           e.printStackTrace();
        }
    }

    private String getCurrentDateAsString() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
    }
}
