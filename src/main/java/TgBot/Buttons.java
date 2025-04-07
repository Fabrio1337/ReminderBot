package TgBot;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class Buttons {

    public Buttons() {}

    public List<String> stopWords()
    {
        List<String> stopWords = List.of("стоп", "/стоп", "stop", "/stop");
        return stopWords;
    }

    public List<String> startWords()
    {
        List<String> startWords = List.of("старт", "/старт", "start", "/start");
        return startWords;
    }

    public String getListCommand()
    {
        return "/command";
    }

    public List<String> getCommands()
    {
        List<String> commands = List.of("/add","/remove","/list","add","remove","list");
        return commands;
    }

    public InlineKeyboardMarkup setSimpleKeyboardMarkup() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        InlineKeyboardButton first = new InlineKeyboardButton();
        first.setText("отложить сообщение");
        first.setCallbackData("delayed");

        InlineKeyboardButton second = new InlineKeyboardButton();
        second.setText("Список отложенных");
        second.setCallbackData("list_delayed");

        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(first);
        row.add(second);
        rows.add(row);


        inlineKeyboardMarkup.setKeyboard(rows);

        return inlineKeyboardMarkup;
    }

}
