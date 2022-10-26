package it.eliasandandrea.chathub.view.serverListComponents;

import it.eliasandandrea.chathub.model.Server;
import it.eliasandandrea.chathub.view.ResourceLoader;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class ServerListEntry extends HBox implements ListEntry{

    private Label serverNameLbl;
    private ImageView iconImg;
    private Server server;
    private ListEntrySelectCallback callback;

    public ServerListEntry(ReadOnlyDoubleProperty widthProperty, Server server, ListEntrySelectCallback callback) {
        super();
        this.server = server;
        this.callback = callback;

        super.getStyleClass().add("backgroundSidebar");
        super.getStyleClass().add("server-entry");
        super.setPrefHeight(30);
        super.maxWidthProperty().bind(widthProperty.subtract(35));
        super.setAlignment(Pos.CENTER);
        super.setSpacing(10);
        super.setOnMouseClicked(e -> select());

        iconImg = new ImageView(new Image(ResourceLoader.loadImage("server_white.png")));
        iconImg.setFitWidth(18);
        iconImg.setFitHeight(18);
        super.getChildren().add(iconImg);

        serverNameLbl = new Label(server.getName());
        serverNameLbl.getStyleClass().add("server-entry-lbl");
        serverNameLbl.prefWidthProperty().bind(widthProperty.subtract(50));
        super.getChildren().add(serverNameLbl);

    }

    public void select(){
        callback.onSelected(this);
        super.getStyleClass().remove("server-entry");
        super.getStyleClass().add("server-entry-selected");
        iconImg.setImage(new Image(ResourceLoader.loadImage("server_black.png")));
        serverNameLbl.getStyleClass().remove("server-entry-lbl");
        serverNameLbl.getStyleClass().add("server-entry-lbl-selected");
    }

    public void unselect(){
        super.getStyleClass().remove("server-entry-selected");
        super.getStyleClass().add("server-entry");
        iconImg.setImage(new Image(ResourceLoader.loadImage("server_white.png")));
        serverNameLbl.getStyleClass().remove("server-entry-lbl-selected");
        serverNameLbl.getStyleClass().add("server-entry-lbl");
    }

    public Server getServer() {
        return server;
    }
}
