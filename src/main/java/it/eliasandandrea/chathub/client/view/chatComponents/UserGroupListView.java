package it.eliasandandrea.chathub.client.view.chatComponents;

import it.eliasandandrea.chathub.client.model.Persistence;
import it.eliasandandrea.chathub.shared.model.ChatEntity;
import it.eliasandandrea.chathub.shared.model.Group;
import it.eliasandandrea.chathub.shared.model.User;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;

public class UserGroupListView extends VBox {

    public UserGroupListView(){
        super.getStyleClass().add("backgroundSidebar");
        super.setMinWidth(200);
        super.setMaxWidth(330);
        super.setSpacing(22);

        Label label = new Label("Chats");
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

        HashMap<ChatEntity, UserGroupListEntry> domMap = new HashMap<>();

        final AtomicBoolean first = new AtomicBoolean(true);
        Persistence.getInstance().chats.forEach(chat -> {
            UserGroupListEntry userGroupListEntry = null;
            if (chat instanceof User usr){
                userGroupListEntry = new UserGroupListEntry(super.widthProperty(), usr.getUsername(), chat, true);
            }else if (chat instanceof Group grp){
                userGroupListEntry = new UserGroupListEntry(super.widthProperty(), grp.getName(), chat, false);
            }
            if (first.get())
                userGroupListEntry.select();
            usersList.getChildren().add(userGroupListEntry);
            domMap.put(chat, userGroupListEntry);
            first.set(false);
        });

        Persistence.getInstance().serverEventCallbackRouter.setOnChatEntityAddedCallback((chat) -> {
            try {
                UserGroupListEntry userGroupListEntry = null;
                if (chat instanceof User usr) {
                    System.out.println("User added with name: " + usr.username);
                    userGroupListEntry = new UserGroupListEntry(super.widthProperty(), usr.username, chat, true);
                } else if (chat instanceof Group grp) {
                    System.out.println("Group added with name: " + grp.name);
                    userGroupListEntry = new UserGroupListEntry(super.widthProperty(), grp.name, chat, false);
                }
                UserGroupListEntry finalUserGroupListEntry = userGroupListEntry;
                Platform.runLater(() -> usersList.getChildren().add(finalUserGroupListEntry));
                domMap.put(chat, userGroupListEntry);
            }catch (Exception e){
                e.printStackTrace();
            }
        });

        Persistence.getInstance().serverEventCallbackRouter.setOnChangeUsernameCallback((uuid, newUsername) -> {
            try {
                domMap.entrySet().forEach(entry -> {
                    if (entry.getKey().getUUID().equals(uuid)) {
                        Platform.runLater(() -> entry.getValue().setName(newUsername));
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        super.getChildren().add(usersList);
    }

}
