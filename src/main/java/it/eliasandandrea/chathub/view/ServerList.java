package it.eliasandandrea.chathub.view;

import it.eliasandandrea.chathub.model.Server;
import it.eliasandandrea.chathub.view.serverListComponents.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class ServerList extends StackPane {

    public ServerList() {
        super();
        super.getStylesheets().add(ResourceLoader.loadStylesheet("Shared.css"));
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

        headerSpacer.prefWidthProperty().bind(header.widthProperty().subtract(logoContainer.widthProperty()).subtract(accountContainer.widthProperty()).subtract(90));

        vBox.getChildren().add(header);

        StringProperty serverAddress = new SimpleStringProperty();
        StringProperty serverPort = new SimpleStringProperty();
        ServerSelectCallback serverSelectCallback = entry -> {
            ServerListEntry serverListEntry = (ServerListEntry) entry;
            Server server = serverListEntry.getServer();
            serverAddress.set(server.getAddress());
            serverPort.set(server.getPort()+"");
        };

        SplitPane splitPane = new SplitPane();
        splitPane.getStyleClass().add("background");
        splitPane.setDividerPositions(0.3);
        splitPane.prefWidthProperty().bind(vBox.widthProperty());
        splitPane.prefHeightProperty().bind(vBox.heightProperty());
        splitPane.getItems().addAll(new ServerListView(serverSelectCallback), new ServerConnector(serverAddress, serverPort));
        vBox.getChildren().add(splitPane);

        super.getChildren().add(vBox);
        super.getChildren().add(changeUsernameDialog);
    }

}
