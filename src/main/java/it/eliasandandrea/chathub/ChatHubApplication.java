package it.eliasandandrea.chathub;

import it.eliasandandrea.chathub.view.ServerList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ChatHubApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(new StackPane(), 800, 450);
        scene.setRoot(new ServerList(scene));
        stage.setTitle("ChatHub");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}