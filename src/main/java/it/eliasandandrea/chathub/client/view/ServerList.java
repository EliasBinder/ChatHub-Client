package it.eliasandandrea.chathub.client.view;

import it.eliasandandrea.chathub.client.model.persistence.Persistence;
import it.eliasandandrea.chathub.client.model.Server;
import it.eliasandandrea.chathub.client.model.TCPClient;
import it.eliasandandrea.chathub.client.model.protocol.ServerEventCallbackRouter;
import it.eliasandandrea.chathub.shared.crypto.CryptManager;
import it.eliasandandrea.chathub.client.view.serverListComponents.*;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class ServerList extends StackPane {

    private ObjectProperty<CryptManager> cryptManagerObjectProperty = new SimpleObjectProperty<>();

    public ServerList(Scene scene) {
        super();
        super.getStylesheets().add(ResourceLoader.loadStylesheet("Shared.css"));
        super.getStylesheets().add(ResourceLoader.loadStylesheet("ServerList.css"));
        super.setAlignment(Pos.TOP_RIGHT);

        SimpleStringProperty usernameProperty = new SimpleStringProperty();
        ChangeUsernameDialog changeUsernameDialog = new ChangeUsernameDialog(usernameProperty);

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
        IntegerProperty serverPort = new SimpleIntegerProperty();

        ServerSelectCallback serverSelectCallback = entry -> {
            ServerListEntry serverListEntry = (ServerListEntry) entry;
            Server server = serverListEntry.getServer();
            serverAddress.set(server.getAddress());
            serverPort.set(server.getPort());
        };

        StackPane loadingPane = new StackPane();
        loadingPane.getStyleClass().add("overlay");
        loadingPane.minWidthProperty().bind(super.widthProperty());
        loadingPane.minHeightProperty().bind(super.heightProperty());
        loadingPane.setVisible(false);
        loadingPane.setAlignment(Pos.CENTER);
        ImageView loading = new ImageView(new Image(ResourceLoader.loadImage("loading.gif")));
        loading.setFitWidth(120);
        loading.setFitHeight(120);
        loadingPane.getChildren().add(loading);

        ConnectCallback connectCallback = new ConnectCallback() {
            @Override
            public void startConnect(Server server) {
                Platform.runLater(() -> {
                    loadingPane.setVisible(true);
                });
                Persistence.getInstance().username = usernameProperty.get();
                Persistence.getInstance().serverEventCallbackRouter = new ServerEventCallbackRouter(() -> { //onConnectionSuccess
                    Platform.runLater(() -> scene.setRoot(new Chat()));
                });
                Persistence.getInstance().client = new TCPClient(
                        server.getAddress(),
                        server.getPort(),
                        cryptManagerObjectProperty.get(),
                        () -> { //onConnectionFail
                            Platform.runLater(() -> loadingPane.setVisible(false));
                        }, () -> { //onConnectionInterrupted
                            Platform.runLater(() -> scene.setRoot(new ServerList(scene)));
                        },
                        Persistence.getInstance().serverEventCallbackRouter
                );
            }
        };

        SplitPane splitPane = new SplitPane();
        splitPane.getStyleClass().add("background");
        splitPane.setDividerPositions(0.3);
        splitPane.prefWidthProperty().bind(vBox.widthProperty());
        splitPane.prefHeightProperty().bind(vBox.heightProperty());
        ServerListView serverListView = new ServerListView(serverSelectCallback);
        splitPane.getItems().addAll(serverListView, new ServerConnector(serverAddress, serverPort, connectCallback));
        vBox.getChildren().add(splitPane);

        serverAddress.addListener((observable, oldValue, newValue) -> {
            if (!serverListView.selectServer(new Server("", newValue, serverPort.get()))){
                serverListView.deselect();
            }
        });
        serverPort.addListener((observable, oldValue, newValue) -> {
            if (!serverListView.selectServer(new Server("", serverAddress.get(), newValue.intValue()))){
                serverListView.deselect();
            }
        });

        KeystorePasswordPane keystorePasswordPane = new KeystorePasswordPane(cryptManagerObjectProperty, super.widthProperty(), super.heightProperty());

        super.getChildren().add(vBox);
        super.getChildren().add(changeUsernameDialog);
        super.getChildren().add(loadingPane);
        super.getChildren().add(keystorePasswordPane);
    }

}
