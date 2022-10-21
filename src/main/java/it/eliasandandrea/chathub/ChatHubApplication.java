package it.eliasandandrea.chathub;

import it.eliasandandrea.chathub.view.ServerList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ChatHubApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(new ServerList(), 800, 600);
        stage.setTitle("ChatHub");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}