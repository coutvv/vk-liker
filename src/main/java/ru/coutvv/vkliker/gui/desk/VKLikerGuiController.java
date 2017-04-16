package ru.coutvv.vkliker.gui.desk;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.coutvv.vkliker.MainController;
import ru.coutvv.vkliker.notify.Logger;
import ru.coutvv.vkliker.notify.Notifier;
import ru.coutvv.vkliker.orm.LikePostRepository;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

/**
 * @author coutvv
 */
public class VKLikerGuiController implements Observer {

    private static final Log logger = LogFactory.getLog(VKLikerGuiController.class);

    public TextArea log;
    public Button start;
    public Button stop;


    public TextArea statistic;

    static final LikePostRepository lpp = new LikePostRepository();
    public TextField profileId;
    private Notifier notifier = new GuiNotifier();

    MainController vklikerController;

    public VKLikerGuiController() {
        vklikerController = MainController.getInstance();
        Logger.init(notifier);
        vklikerController.addObserver(this);
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
            notifier.print("vk-liker has stopped");
            stop.setDisable(true);
            start.setDisable(false);
        }
    }

    public void likeAllProfileNews() {
        try {
            vklikerController.likeAllProfileNews(profileId.getText());
        } catch (ClientException | ApiException e) {
            logger.warn("likeAllProfileNews", e);
        }
    }

    private class GuiNotifier implements Notifier {
        @Override
        public void print(String msg) {
            if(log.getText().length() > 2000) log.clear();
            log.setText(log.getText() + msg + "\n");
        }
    }

}
