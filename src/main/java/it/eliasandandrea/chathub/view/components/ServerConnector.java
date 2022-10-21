package it.eliasandandrea.chathub.view.components;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ServerConnector extends VBox {

    public ServerConnector() {
        super();
        super.getStyleClass().add("background");
        super.setMinWidth(200);
        Label label = new Label("Connect to a specific server");
        label.getStyleClass().add("description-lbl");
        label.setAlignment(Pos.CENTER);
        label.prefWidthProperty().bind(super.widthProperty());
        super.getChildren().add(label);
    }

}
