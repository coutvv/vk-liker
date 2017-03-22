package ru.coutvv.vkliker.gui.desk;/**
 * @author coutvv
 */

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.coutvv.vkliker.Factory;
import ru.coutvv.vkliker.api.FeedManager;
import ru.coutvv.vkliker.notify.Logger;
import ru.coutvv.vkliker.notify.Notifier;

import java.io.IOException;

public class VkLikerMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("gui/vkliker.fxml"));
        primaryStage.setTitle("VkLiker");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> Platform.exit());
    }






}
