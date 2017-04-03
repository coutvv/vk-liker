package ru.coutvv.vkliker.gui.desk;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.TelegramBotsApi;
import ru.coutvv.vkliker.Factory;
import ru.coutvv.vkliker.MainController;
import ru.coutvv.vkliker.api.FeedManager;
import ru.coutvv.vkliker.notify.Logger;
import ru.coutvv.vkliker.notify.Notifier;
import ru.coutvv.vkliker.notify.TelegramBot;
import ru.coutvv.vkliker.orm.LikePostRepository;
import ru.coutvv.vkliker.util.LagUtil;
import sun.applet.Main;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;

/**
 * @author coutvv
 */
public class VKLikerGuiController implements Observer{
    public TextArea log;
    public Button start;
    public Button stop;


    public TextArea statistic;

    static final LikePostRepository lpp = new LikePostRepository();
    private Notifier notifier = new GuiNotifier();

    MainController vklikerController;

    public VKLikerGuiController() {
        vklikerController = MainController.getInstance();
        Logger.init(notifier);
    }

    public void start() throws IOException {
        log.clear();
        vklikerController.start();
        start.setDisable(true);
        stop.setDisable(false);
    }

    public void stop() throws Exception {
        vklikerController.stop();
        stop.setDisable(true);
        start.setDisable(false);
    }



    public void day() {
        statistic.setText("Per day: " + lpp.getCountLikePerDay());
    }

    public void all() {
        statistic.setText("Per all time: " + lpp.getCountLike());
    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg == MainController.State.start) {
            notifier.print("vk-liker has started");
            start.setDisable(true);
            stop.setDisable(false);
        } else {
            notifier.print("vk-liker has started");
            stop.setDisable(true);
            start.setDisable(false);
        }
    }

    private class GuiNotifier implements Notifier {
        @Override
        public void print(String msg) {
            if(log.getText().length() > 2000) log.clear();
            log.setText(log.getText() + "\n" + msg);
        }
    }

}
