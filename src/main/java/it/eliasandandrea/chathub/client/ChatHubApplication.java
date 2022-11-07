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
        ResourceLoader.loadFont("Poppins-Regular.ttf");
        Scene scene = new Scene(new StackPane(), 800, 450);
        scene.setRoot(new ServerList(scene));
        stage.setTitle("ChatHub");
        stage.setOnCloseRequest(e -> System.exit(0));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}