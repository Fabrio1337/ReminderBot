package TgBot.Services.Schedule;

import DB_Operations.DeletionData;
import DB_Operations.GettingData;
import Entity.Message;
import TgBot.Bot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ScheduleMessageService {

    private GettingData gettingData;

    private DeletionData deletionData;

    private  Bot bot;

    @Autowired
    public ScheduleMessageService(Bot bot, GettingData gettingData, DeletionData deletionData) {
        this.bot = bot;
        this.gettingData = gettingData;
        this.deletionData = deletionData;
    }

    public ScheduleMessageService() {}


    @Scheduled(fixedRate = 30000)
    public void scheduleMessage() {
        try {
            List<Message> messages = gettingData.getMessage(getCurrentDateAsString());



            if(messages != null && messages.size() > 0) {

                for (Message message : messages) {
                    try {
                        SendMessage telegramMessage = new SendMessage();
                        telegramMessage.setChatId(String.valueOf(message.getUser().getChatId()));
                        telegramMessage.setText("Вы просили напомнить\uD83D\uDE0A:\n" + message.getMessage());

                        deletionData.deleteMessage(message);

                        bot.execute(telegramMessage);


                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }

            }
        } catch (Exception e) {
           e.printStackTrace();
        }
    }

    private String getCurrentDateAsString() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
    }
}
