package it.eliasandandrea.chathub.view.serverListComponents;

import it.eliasandandrea.chathub.model.Server;
import it.eliasandandrea.chathub.view.ResourceLoader;
import it.eliasandandrea.chathub.view.sharedComponents.ListEntry;
import it.eliasandandrea.chathub.view.sharedComponents.ListEntrySelectCallback;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.css.PseudoClass;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class ServerListEntry extends HBox implements ListEntry {

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
        super.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), true);
        iconImg.setImage(new Image(ResourceLoader.loadImage("server_black.png")));
        serverNameLbl.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), true);
    }

    public void unselect() {
        super.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), false);
        iconImg.setImage(new Image(ResourceLoader.loadImage("server_white.png")));
        serverNameLbl.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), false);
    }

    public Server getServer() {
        return server;
    }
}
