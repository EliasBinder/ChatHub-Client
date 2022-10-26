package it.eliasandandrea.chathub.view;

import it.eliasandandrea.chathub.view.serverListComponents.ChatView;
import it.eliasandandrea.chathub.view.serverListComponents.UserGroupListView;
import javafx.geometry.Pos;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class Chat extends StackPane {

    public Chat() {
        super();
        super.getStylesheets().add(ResourceLoader.loadStylesheet("Shared.css"));
        super.getStylesheets().add(ResourceLoader.loadStylesheet("Chat.css"));

        VBox vBox = new VBox();
        vBox.getStyleClass().add("background");

        HBox header = new HBox();
        header.setAlignment(Pos.CENTER);
        header.getStyleClass().add("header");
        header.getStyleClass().add("background");

        StackPane logoContainer = new StackPane();
        int logoScale = 22;
        logoContainer.setPrefWidth((((double)2560)/logoScale)+30);
        ImageView logo = new ImageView(new Image(ResourceLoader.loadImage("ChatHubLogo.png")));
        logo.getStyleClass().add("logo");
        logo.setFitWidth(((double)2560)/logoScale);
        logo.setFitHeight(((double)905)/logoScale);
        logoContainer.getChildren().add(logo);
        header.getChildren().add(logoContainer);

        vBox.getChildren().add(header);

        SplitPane splitPane = new SplitPane();
        splitPane.getStyleClass().add("background");
        splitPane.setDividerPositions(0.3);
        splitPane.prefWidthProperty().bind(vBox.widthProperty());
        splitPane.prefHeightProperty().bind(vBox.heightProperty());
        splitPane.getItems().addAll(new UserGroupListView(), new ChatView());
        vBox.getChildren().add(splitPane);

        super.getChildren().add(vBox);
    }

}
