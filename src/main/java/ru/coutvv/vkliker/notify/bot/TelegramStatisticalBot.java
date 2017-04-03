package ru.coutvv.vkliker.notify.bot;

import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.coutvv.vkliker.MainController;
import ru.coutvv.vkliker.notify.TelegramBot;
import ru.coutvv.vkliker.orm.LikePostRepository;
import ru.coutvv.vkliker.orm.entity.LikePost;

import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Created by lomovtsevrs on 03.03.2017.
 */
public class TelegramStatisticalBot extends TelegramBot implements Observer{

    static ReplyKeyboardMarkup keyboard;
    static ReplyKeyboardMarkup keyboardStop;
    static ReplyKeyboardMarkup keyboardStart;

    static final Map<String, Consumer<TelegramBot>> keys = new HashMap<>();
    static final LikePostRepository lpp = new LikePostRepository();
    static final MainController controller = MainController.getInstance();

    static {

        keys.put("/all", (bot) -> {
            long count = lpp.getCountLike();
            bot.sending("All : " + count, keyboard);
        });

        keys.put("/day", (bot) -> {
            long perDay = lpp.getCountLikePerDay();
            bot.sending("per day : " + perDay, keyboard);
        });

        keys.put("/start", (bot) -> {
            if(controller.getCurrentState() == MainController.State.start) {
                bot.sending("can't start cause is started!");
            }
            else
                try {
                    keyboard = keyboardStart;
                    controller.start();
                } catch (IOException e) {
                    bot.sending("can't start becouse error: " + e.getMessage());
                }
        });

        keys.put("/stop", (bot) -> {
            if(controller.getCurrentState() == MainController.State.stop) {
                bot.sending("vk-liker already stopped!");
            } else
                try {
                    keyboard = keyboardStop;
                    controller.stop();
                } catch (Exception e) {
                    bot.sending("can't stop becouse error: " + e.getMessage());
                }
        });

        keyboardStop = createKeyboardWithout("/stop");
        keyboardStart = createKeyboardWithout("/start");

        keyboard = keyboardStop;
    }

    private static ReplyKeyboardMarkup createKeyboardWithout(String k) {
        ReplyKeyboardMarkup result = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        for(String key : keys.keySet()) {
            if(!k.equals(key)) {
                if(row.size() > 2) {
                    keyboardRowList.add(row);
                    row = new KeyboardRow();
                }
                row.add(key);
            }
        }
        if(row.size()>0) keyboardRowList.add(row);
        result.setKeyboard(keyboardRowList);
        return result;
    }


    public TelegramStatisticalBot(String token, String chatId) throws TelegramApiException {
        super(token, chatId, (update, bot) -> {
            String cmd = update.getMessage().getText();
            if(keys.containsKey(cmd)) {
                keys.get(cmd).accept(bot);
            }
        });
        controller.addObserver(this);
    }

    @Override
    public void print(String msg) {

    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg.equals(MainController.State.start)) {
            keyboard = keyboardStart;
        } else {
            keyboard = keyboardStop;
        }
        this.sending("State has changed: " + arg.toString(), keyboard);
    }
}
