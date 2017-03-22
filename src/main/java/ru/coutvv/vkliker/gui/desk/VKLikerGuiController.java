package ru.coutvv.vkliker.gui.desk;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import ru.coutvv.vkliker.Factory;
import ru.coutvv.vkliker.api.FeedManager;
import ru.coutvv.vkliker.notify.Logger;
import ru.coutvv.vkliker.notify.Notifier;
import ru.coutvv.vkliker.orm.LikePostRepository;
import ru.coutvv.vkliker.util.LagUtil;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

/**
 * @author coutvv
 */
public class VKLikerGuiController {
    public TextArea log;
    public Button start;
    public Button stop;

    public Notifier notifier = new GuiNotifier();
    public TextArea statistic;

    public VKLikerGuiController() {

    }

    public void start(ActionEvent actionEvent) {
        log.clear();
        startWithLogSystem(notifier);
        start.setDisable(true);
        stop.setDisable(false);
    }

    public void stop(ActionEvent actionEvent) throws Exception {

        if(likerThread != null) {
            likerThread.shutdown();
            LagUtil.lag(500);
            if(!likerThread.isShutdown()) {
                String error = "Can't stop thread";
                notifier.print(error);
                throw new Exception(error);
            }
        }
        stop.setDisable(true);
        start.setDisable(false);
    }

    private ExecutorService likerThread;

    final static String FILENAME = "app.properties";
    public void startWithLogSystem(Notifier notifier) {
        FeedManager fm;
        Factory fac;
        Logger.init(notifier);
        try {
            fac = new Factory(FILENAME);
            fm = fac.createFeedManager();
            likerThread = fm.scheduleLike(15);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void day(ActionEvent actionEvent) {
        statistic.setText("Per day: " + lpp.getCountLikePerDay());
    }

    public void all(ActionEvent actionEvent) {
        statistic.setText("Per all time: " + lpp.getCountLike());
    }

    private class GuiNotifier implements Notifier {
        @Override
        public void print(String msg) {
            if(log.getText().length() > 2000) log.clear();
            log.setText(log.getText() + "\n" + msg);
        }
    }


    static final LikePostRepository lpp = new LikePostRepository();
}
