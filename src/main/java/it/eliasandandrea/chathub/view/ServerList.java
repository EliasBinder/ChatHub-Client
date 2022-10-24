package it.eliasandandrea.chathub.view;

import it.eliasandandrea.chathub.model.zeroconf.ServerFinder;
import it.eliasandandrea.chathub.view.components.ChangeUsernameDialog;
import it.eliasandandrea.chathub.view.components.ServerConnector;
import it.eliasandandrea.chathub.view.components.ServerListView;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class ServerList extends StackPane {

    public ServerList() {
        super();
        super.getStylesheets().add(ResourceLoader.loadStylesheet("ServerList.css"));
        super.setAlignment(Pos.TOP_RIGHT);

        ChangeUsernameDialog changeUsernameDialog = new ChangeUsernameDialog();

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

        Pane headerSpacer = new Pane();
        HBox.setHgrow(headerSpacer, Priority.ALWAYS);
        header.getChildren().add(headerSpacer);

        StackPane accountContainer = new StackPane();
        accountContainer.setOnMouseClicked(event -> changeUsernameDialog.toggle());
        int accountScale = 3;
        accountContainer.setMinHeight((((double)100)/accountScale)+15);
        accountContainer.setMinWidth((((double)100)/accountScale)+15);
        accountContainer.getStyleClass().add("account-container");
        ImageView account = new ImageView(new Image(ResourceLoader.loadImage("account.png")));
        account.getStyleClass().add("account");
        account.setFitWidth(((double)100)/accountScale);
        account.setFitHeight(((double)100)/accountScale);
        accountContainer.getChildren().add(account);
        header.getChildren().add(accountContainer);

        //TODO
        header.widthProperty().addListener((observable, oldValue, newValue) -> {
            headerSpacer.setPrefWidth(newValue.doubleValue() - logoContainer.getPrefWidth() - accountContainer.getPrefWidth() - 90);
        });

        vBox.getChildren().add(header);

        SplitPane splitPane = new SplitPane();
        splitPane.getStyleClass().add("background");
        splitPane.setDividerPositions(0.3);
        splitPane.prefWidthProperty().bind(vBox.widthProperty());
        splitPane.prefHeightProperty().bind(vBox.heightProperty());
        splitPane.getItems().addAll(new ServerListView(), new ServerConnector());
        vBox.getChildren().add(splitPane);

        super.getChildren().add(vBox);
        super.getChildren().add(changeUsernameDialog);
    }

}
