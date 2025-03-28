package TgBot.Services.Schedule;

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


    @Autowired
    private  Bot bot;

    @Autowired
    public ScheduleMessageService(Bot bot, SetSession setSession) {
        this.bot = bot;
        this.setSession = setSession;
        System.out.println("ScheduleMessageService инициализирован!");
    }

    public ScheduleMessageService() {}

    @Transactional
    @Scheduled(fixedRate = 10000)
    public void scheduleMessage() {
        try {
            Session session = setSession.getSession();
            System.out.println("в методе scheduleMessage");
            String currentTime = getCurrentDateAsString();

            session.beginTransaction();

            Query query = session.createQuery(
                    "FROM Message m " +
                            "WHERE m.delayedMessageDate = :currentDate AND m.isDelayed = true");
            query.setParameter("currentDate", currentTime);

            List<Message> messages = query.list();

            if (messages.isEmpty()) {
                System.out.println("Сообщений нет для отправки");
            }

            System.out.println("перед отправкой сообщений");

            for (Message message : messages) {
                try {
                    System.out.println("начало отправки для сообщения ID: " + message.getId());

                    SendMessage telegramMessage = new SendMessage();
                    telegramMessage.setChatId(String.valueOf(message.getUser().getChatId()));
                    telegramMessage.setText("Вы просили напомнить\uD83D\uDE0A:\n" + message.getMessage());


                    bot.execute(telegramMessage);


                    message.setDelayed(false);
                    session.update(message);

                    session.flush();

                    System.out.println("Сообщение отправлено: " + message.getId());
                } catch (TelegramApiException e) {
                    System.err.println("Ошибка отправки сообщения для ID: " + message.getId() + " - " + e.getMessage());
                }
            }

            session.getTransaction().commit();

        } catch (Exception e) {
            System.err.println("Ошибка в scheduleMessage: " + e.getMessage());
        }
    }

    private String getCurrentDateAsString() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
    }
}
