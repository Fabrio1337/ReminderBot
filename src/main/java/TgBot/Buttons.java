package TgBot;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import javax.annotation.PostConstruct;

@Component
public class Buttons {

    private ReplyKeyboardMarkup replyKeyboardMarkup;

    public Buttons() {}

    @PostConstruct
    public void init() {
        replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true); //подгоняем размер
        replyKeyboardMarkup.setOneTimeKeyboard(false); //скрываем после использования

        //метод для создания кнопок
    }

}
