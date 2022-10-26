package it.eliasandandrea.chathub.view.chatComponents;

import it.eliasandandrea.chathub.view.ResourceLoader;
import it.eliasandandrea.chathub.view.sharedComponents.ListEntry;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class UserGroupListEntry extends HBox implements ListEntry {

    ImageView iconImg;
    Label nameLbl;
    boolean isUser;

    public UserGroupListEntry(ReadOnlyDoubleProperty widthProperty, String name, boolean isUser) {
        super();
        this.isUser = isUser;

        super.getStyleClass().add("backgroundSidebar");
        super.getStyleClass().add("user-entry");
        super.setPrefHeight(30);
        super.maxWidthProperty().bind(widthProperty.subtract(35));
        super.setAlignment(Pos.CENTER);
        super.setSpacing(10);

        iconImg = new ImageView(new Image(ResourceLoader.loadImage(isUser ? "user_white.png" : "group_white.png")));
        iconImg.setFitWidth(18);
        iconImg.setFitHeight(18);
        super.getChildren().add(iconImg);

        nameLbl = new Label(name);
        nameLbl.getStyleClass().add("user-entry-lbl");
        nameLbl.prefWidthProperty().bind(widthProperty.subtract(50));
        super.getChildren().add(nameLbl);
    }

    public void select(){
        super.getStyleClass().remove("user-entry");
        super.getStyleClass().add("user-entry-selected");
        iconImg.setImage(new Image(ResourceLoader.loadImage(isUser ? "user_black.png" : "group_black.png")));
        nameLbl.getStyleClass().remove("user-entry-lbl");
        nameLbl.getStyleClass().add("user-entry-lbl-selected");
    }

    public void unselect(){
        super.getStyleClass().remove("user-entry-selected");
        super.getStyleClass().add("user-entry");
        iconImg.setImage(new Image(ResourceLoader.loadImage(isUser ? "user_white.png" : "group_white.png")));
        nameLbl.getStyleClass().remove("user-entry-lbl-selected");
        nameLbl.getStyleClass().add("user-entry-lbl");
    }

}
