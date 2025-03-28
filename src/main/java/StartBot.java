import SpringConfigs.SpringDBCfg;
import SpringConfigs.SpringServiceCfg;
import SpringConfigs.SpringTGCfg;
import TgBot.Bot;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class StartBot {
    public static void main(String args[])
    {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringTGCfg.class, SpringDBCfg.class, SpringServiceCfg.class);
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(context.getBean(Bot.class));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
