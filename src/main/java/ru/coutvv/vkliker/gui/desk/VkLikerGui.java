package ru.coutvv.vkliker.gui.desk;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.TelegramBotsApi;
import ru.coutvv.vkliker.Factory;
import ru.coutvv.vkliker.MainController;
import ru.coutvv.vkliker.notify.Logger;
import ru.coutvv.vkliker.notify.TelegramBot;

import java.io.IOException;

/**
 * @author coutvv
 */
public class VkLikerGui extends Application {

    private static final Log log = LogFactory.getLog(VkLikerGui.class);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("gui/vkliker.fxml"));
        primaryStage.setTitle("VkLiker");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> {
            try {
                MainController.getInstance().stop();
            } catch (Exception e1) {
                log.error("can't stop controller thread", e1);
                Logger.log("can't stop controller thread");
            }
            Platform.exit();
            System.exit(0);
        });
        initTelegramBot();
    }


    private void initTelegramBot() throws IOException {
        TelegramBot bot = new Factory().createStaticalNotifier();

        TelegramBotsApi api = new TelegramBotsApi();
        try {
            api.registerBot(bot);
        } catch (TelegramApiException e) {
            throw new IllegalArgumentException("telegram register bot failed");
        }
        Logger.init(bot);
    }



}
