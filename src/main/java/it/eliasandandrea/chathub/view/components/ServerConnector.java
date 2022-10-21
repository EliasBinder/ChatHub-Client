package it.eliasandandrea.chathub.view.components;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class ServerConnector extends VBox {

    public ServerConnector() {
        super();
        super.getStyleClass().add("background");
        super.setMinWidth(200);
        super.setSpacing(50);
        Label label = new Label("Connect to a Remote Server");
        label.getStyleClass().add("description-lbl");
        label.setAlignment(Pos.CENTER);
        label.prefWidthProperty().bind(super.widthProperty());
        super.getChildren().add(label);

        VBox connectionDetails = new VBox();
        connectionDetails.getStyleClass().add("background");
        connectionDetails.prefWidthProperty().bind(super.widthProperty());
        connectionDetails.prefHeightProperty().bind(super.heightProperty());
        connectionDetails.setAlignment(Pos.TOP_CENTER);
        connectionDetails.setSpacing(40);

        HBox connectionSettings = new HBox();
        connectionSettings.getStyleClass().add("background");
        connectionSettings.setAlignment(Pos.TOP_CENTER);
        connectionSettings.setSpacing(20);

        VBox descriptionContainer = new VBox();
        descriptionContainer.getStyleClass().add("background");
        descriptionContainer.setSpacing(10);
        descriptionContainer.setAlignment(Pos.TOP_CENTER);
        connectionSettings.getChildren().add(descriptionContainer);

        VBox inputContainer = new VBox();
        inputContainer.getStyleClass().add("background");
        inputContainer.setSpacing(10);
        inputContainer.setAlignment(Pos.TOP_CENTER);
        connectionSettings.getChildren().add(inputContainer);

        TextField ip = new TextField();
        ip.setMaxWidth(350);
        addInput(descriptionContainer, inputContainer, "Server Address", ip);

        TextField port = new TextField();
        port.setMaxWidth(80);
        addInput(descriptionContainer, inputContainer, "Port", port);

        connectionDetails.getChildren().add(connectionSettings);

        Button connect = new Button("Connect");
        connect.getStyleClass().add("btn");
        connectionDetails.getChildren().add(connect);

        super.getChildren().add(connectionDetails);
    }

    private void addInput(VBox descriptionContainer, VBox inputContainer, String description, TextField input){
        StackPane descLblContainerPane = new StackPane();
        descLblContainerPane.setPrefHeight(30);
        descLblContainerPane.setAlignment(Pos.CENTER_RIGHT);

        Label descLbl = new Label(description);
        descLbl.getStyleClass().add("connection-setup-lbl");
        descLbl.setAlignment(Pos.CENTER_LEFT);

        descLblContainerPane.getChildren().add(descLbl);
        descriptionContainer.getChildren().add(descLblContainerPane);

        StackPane inputContainerPane = new StackPane();
        inputContainerPane.setPrefHeight(30);
        inputContainerPane.setAlignment(Pos.CENTER_LEFT);

        input.getStyleClass().add("connection-setup-textfield");
        input.setMinHeight(20);

        inputContainerPane.getChildren().add(input);
        inputContainer.getChildren().add(inputContainerPane);
    }

}
