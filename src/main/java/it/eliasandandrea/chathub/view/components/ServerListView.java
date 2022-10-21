package it.eliasandandrea.chathub.view.components;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ServerListView extends VBox {

    public ServerListView() {
        super();
        super.getStyleClass().add("backgroundSidebar");
        super.setMinWidth(200);
        super.setMaxWidth(330);
        Label label = new Label("Server List");
        label.getStyleClass().add("description-lbl");
        label.setAlignment(Pos.CENTER);
        label.prefWidthProperty().bind(super.widthProperty());
        super.getChildren().add(label);
    }

}
