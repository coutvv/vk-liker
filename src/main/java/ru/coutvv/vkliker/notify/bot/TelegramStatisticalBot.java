package ru.coutvv.vkliker.notify.bot;

import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.coutvv.vkliker.notify.TelegramBot;
import ru.coutvv.vkliker.orm.LikePostRepository;
import ru.coutvv.vkliker.orm.entity.LikePost;

import java.util.Arrays;

/**
 * Created by lomovtsevrs on 03.03.2017.
 */
public class TelegramStatisticalBot extends TelegramBot {

    static ReplyKeyboard keyboard;
    static {
        ReplyKeyboardMarkup keyboard1 = new ReplyKeyboardMarkup();
        KeyboardRow row = new KeyboardRow();
        row.add("/all");
        row.add("/day");
        keyboard1.setKeyboard(Arrays.asList(row));
        keyboard = keyboard1;
    }

    public TelegramStatisticalBot(String token, String chatId) throws TelegramApiException {
        super(token, chatId, (update, bot) -> {
            String cmd = update.getMessage().getText();
            switch(cmd) {
                case "/all" :
                    long count = lpp.getCountLike();
                    bot.sending("All : " + count, keyboard);
                    break;
                case "/day" :
                    long perDay = lpp.getCountLikePerDay();
                    bot.sending("per day : " + perDay, keyboard);
                    break;
                case "/start" :
                    bot.sending("Hello! ", keyboard);
                    break;
                default:
                    break;
            }
        });
    }
    static final LikePostRepository lpp = new LikePostRepository();

    @Override
    public void print(String msg) {

    }
}
