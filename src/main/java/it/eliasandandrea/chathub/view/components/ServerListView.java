package it.eliasandandrea.chathub.view.components;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class ServerListView extends VBox {

    public ServerListView() {
        super();
        super.getStyleClass().add("backgroundSidebar");
        super.setMinWidth(200);
        super.setMaxWidth(330);
        super.setSpacing(22);

        Label label = new Label("Local Servers");
        label.getStyleClass().add("description-lbl");
        label.setAlignment(Pos.CENTER);
        label.prefWidthProperty().bind(super.widthProperty());
        super.getChildren().add(label);

        VBox serverList = new VBox();
        serverList.getStyleClass().add("backgroundSidebar");
        serverList.setSpacing(5);
        serverList.prefWidthProperty().bind(super.widthProperty());
        serverList.setMinHeight(200);
        serverList.setAlignment(Pos.TOP_CENTER);
        VBox.setVgrow(serverList, Priority.ALWAYS);
        ServerListEntry server1 = new ServerListEntry(super.widthProperty(), "Server 1");
        server1.select();
        serverList.getChildren().add(server1);
        serverList.getChildren().add(new ServerListEntry(super.widthProperty(), "Server 2"));
        serverList.getChildren().add(new ServerListEntry(super.widthProperty(), "Server 3"));
        super.getChildren().add(serverList);
    }

}
