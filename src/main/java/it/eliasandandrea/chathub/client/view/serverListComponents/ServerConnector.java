package it.eliasandandrea.chathub.client.view.serverListComponents;

import it.eliasandandrea.chathub.client.model.Server;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.NumberStringConverter;

import java.util.Objects;

public class ServerConnector extends VBox {

    public ServerConnector(StringProperty serverAddressProperty, IntegerProperty serverPortProperty, IntegerProperty serverTypeProperty, ConnectCallback callback){
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

        HBox connectionType = new HBox();
        connectionType.getStyleClass().add("background");
        connectionType.setAlignment(Pos.TOP_CENTER);
        connectionType.setSpacing(50);

        ToggleGroup connectionTypeGroup = new ToggleGroup();
        RadioButton tcp = new RadioButton("TCP");
        tcp.getStyleClass().add("radio-btn");
        tcp.getProperties().put("type", 1);
        tcp.setSelected(true);
        RadioButton rmi = new RadioButton("RMI");
        rmi.getStyleClass().add("radio-btn");
        rmi.getProperties().put("type", 2);
        connectionType.getChildren().addAll(tcp, rmi);
        connectionTypeGroup.getToggles().addAll(tcp, rmi);
        connectionTypeGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                serverTypeProperty.setValue((int) newValue.getProperties().get("type"));
            }
        });
        serverTypeProperty.addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (newValue.equals(1)) {
                    connectionTypeGroup.selectToggle(tcp);
                } else {
                    connectionTypeGroup.selectToggle(rmi);
                }
            }
        });


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
        ip.textProperty().bindBidirectional(serverAddressProperty);
        ip.setMaxWidth(350);
        addInput(descriptionContainer, inputContainer, "Server Address", ip);

        TextField port = new TextField();
        port.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                port.setText(newValue.replaceAll("[^\\d]", ""));
            }
            serverPortProperty.setValue(Integer.parseInt(port.getText()));
        });
        serverPortProperty.addListener((observable, oldValue, newValue) -> {
            port.setText(newValue.toString());
        });
        port.setMaxWidth(80);
        addInput(descriptionContainer, inputContainer, "Port", port);

        connectionDetails.getChildren().add(connectionType);
        connectionDetails.getChildren().add(connectionSettings);

        Button connect = new Button("Connect");
        connect.getStyleClass().add("btn");
        connect.setOnAction(e -> callback.startConnect(new Server("", serverAddressProperty.get(), serverPortProperty.get(), serverTypeProperty.get())));
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
