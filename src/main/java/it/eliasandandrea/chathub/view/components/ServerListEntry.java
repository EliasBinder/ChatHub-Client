package it.eliasandandrea.chathub.view.components;

import it.eliasandandrea.chathub.view.ResourceLoader;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class ServerListEntry extends HBox {

    private Label serverNameLbl;
    private ImageView iconImg;

    public ServerListEntry(ReadOnlyDoubleProperty widthProperty, String name) {
        super();
        super.getStyleClass().add("backgroundSidebar");
        super.getStyleClass().add("server-entry");
        super.setPrefHeight(30);
        super.maxWidthProperty().bind(widthProperty.subtract(35));
        super.setAlignment(Pos.CENTER);
        super.setSpacing(10);

        iconImg = new ImageView(new Image(ResourceLoader.loadImage("server_white.png")));
        iconImg.setFitWidth(18);
        iconImg.setFitHeight(18);
        super.getChildren().add(iconImg);

        serverNameLbl = new Label(name);
        serverNameLbl.getStyleClass().add("server-entry-lbl");
        serverNameLbl.prefWidthProperty().bind(widthProperty.subtract(50));
        super.getChildren().add(serverNameLbl);

    }

    public void select(){
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

}
