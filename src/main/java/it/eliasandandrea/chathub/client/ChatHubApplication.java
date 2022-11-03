package it.eliasandandrea.chathub.client;

import it.eliasandandrea.chathub.client.view.ResourceLoader;
import it.eliasandandrea.chathub.client.view.ServerList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class ChatHubApplication extends Application {
    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new StackPane(), 800, 450);
        ResourceLoader.loadFont("Poppins-Regular.ttf");
        scene.setRoot(new ServerList(scene));
        stage.setTitle("ChatHub");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}