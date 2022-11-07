package it.eliasandandrea.chathub.client.view.chatComponents;

import it.eliasandandrea.chathub.client.model.persistence.Persistence;
import it.eliasandandrea.chathub.client.view.serverListComponents.ServerListEntry;
import it.eliasandandrea.chathub.client.view.sharedComponents.ListEntrySelectCallback;
import it.eliasandandrea.chathub.shared.model.ChatEntity;
import it.eliasandandrea.chathub.shared.model.Group;
import it.eliasandandrea.chathub.shared.model.User;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class UserGroupListView extends VBox {

    public UserGroupListView(ChatChangeCallback callback){
        super.getStyleClass().add("backgroundSidebar");
        super.setMinWidth(200);
        super.setMaxWidth(330);
        super.setSpacing(22);

        Label label = new Label("Chats");
        label.getStyleClass().add("description-lbl");
        label.setAlignment(Pos.CENTER);
        label.prefWidthProperty().bind(super.widthProperty());
        super.getChildren().add(label);

        ScrollPane usersListSP = new ScrollPane();
        usersListSP.getStyleClass().add("backgroundSidebar");
        usersListSP.prefWidthProperty().bind(super.widthProperty());
        usersListSP.setFitToWidth(true);
        usersListSP.getStyleClass().add("scroll-bar-sidebar");
        usersListSP.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        usersListSP.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        super.getChildren().add(usersListSP);

        VBox usersList = new VBox();
        usersList.getStyleClass().add("backgroundSidebar");
        usersList.setSpacing(5);
        usersList.prefWidthProperty().bind(usersListSP.widthProperty());
        usersList.setAlignment(Pos.TOP_CENTER);
        usersList.minHeightProperty().bind(usersListSP.minHeightProperty().subtract(2));

        HashMap<ChatEntity, UserGroupListEntry> domMap = new HashMap<>();

        ListEntrySelectCallback listEntrySelectCallback = listEntry -> {
            for (UserGroupListEntry serverListEntry : domMap.values()) {
                if (serverListEntry != listEntry) {
                    serverListEntry.unselect();
                }
            }
            callback.onChatChange(((UserGroupListEntry)listEntry).ref.getUUID());
        };

        final AtomicBoolean first = new AtomicBoolean(true);
        Persistence.getInstance().chats.forEach(chat -> {
            UserGroupListEntry userGroupListEntry = null;
            if (chat instanceof User usr){
                userGroupListEntry = new UserGroupListEntry(super.widthProperty(), usr.getUsername(), chat, true, listEntrySelectCallback);
            }else if (chat instanceof Group grp){
                userGroupListEntry = new UserGroupListEntry(super.widthProperty(), grp.getName(), chat, false, listEntrySelectCallback);
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
                    userGroupListEntry = new UserGroupListEntry(super.widthProperty(), usr.username, chat, true, listEntrySelectCallback);
                } else if (chat instanceof Group grp) {
                    userGroupListEntry = new UserGroupListEntry(super.widthProperty(), grp.name, chat, false, listEntrySelectCallback);
                }
                UserGroupListEntry finalUserGroupListEntry = userGroupListEntry;
                Platform.runLater(() -> usersList.getChildren().add(finalUserGroupListEntry));
                domMap.put(chat, userGroupListEntry);
            }catch (Exception e){
                e.printStackTrace();
            }
        });

        Persistence.getInstance().serverEventCallbackRouter.setOnChatEntityRemovedCallback((uuid) -> {
            try {
                for (Map.Entry<ChatEntity, UserGroupListEntry> entry : domMap.entrySet()) {
                    if (entry.getKey().getUUID().equals(uuid)) {
                        Platform.runLater(() -> usersList.getChildren().remove(entry.getValue()));
                        domMap.remove(entry.getKey());
                        break;
                    }
                }
            }catch (Exception ex){
                ex.printStackTrace();
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

        usersListSP.setContent(usersList);
    }

}
