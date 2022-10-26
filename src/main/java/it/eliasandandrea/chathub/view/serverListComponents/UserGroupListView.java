package it.eliasandandrea.chathub.view.serverListComponents;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class UserGroupListView extends VBox {

    public UserGroupListView(){
        super.getStyleClass().add("backgroundSidebar");
        super.setMinWidth(200);
        super.setMaxWidth(330);
        super.setSpacing(22);

        Label label = new Label("Users | Groups");
        label.getStyleClass().add("description-lbl");
        label.setAlignment(Pos.CENTER);
        label.prefWidthProperty().bind(super.widthProperty());
        super.getChildren().add(label);

        VBox usersList = new VBox();
        usersList.getStyleClass().add("backgroundSidebar");
        usersList.setSpacing(5);
        usersList.prefWidthProperty().bind(super.widthProperty());
        usersList.setMinHeight(200);
        usersList.setAlignment(Pos.TOP_CENTER);
        VBox.setVgrow(usersList, Priority.ALWAYS);

        UserGroupListEntry firstUser = new UserGroupListEntry(super.widthProperty(), "Elias", true);
        firstUser.select();
        usersList.getChildren().add(firstUser);
        usersList.getChildren().add(new UserGroupListEntry(super.widthProperty(), "Andrea", true));
        usersList.getChildren().add(new UserGroupListEntry(super.widthProperty(), "Some Group", false));

        super.getChildren().add(usersList);
    }

}
