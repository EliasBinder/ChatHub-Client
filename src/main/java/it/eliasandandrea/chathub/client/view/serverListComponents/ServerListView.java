package it.eliasandandrea.chathub.client.view.serverListComponents;

import it.eliasandandrea.chathub.client.model.zeroconf.ServerFinder;
import it.eliasandandrea.chathub.client.model.Server;
import it.eliasandandrea.chathub.client.view.sharedComponents.ListEntrySelectCallback;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.LinkedList;

public class ServerListView extends VBox {

    private LinkedList<ServerListEntry> serverListEntries;

    public ServerListView(ServerSelectCallback serverSelectCallback) {
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
        super.getChildren().add(serverList);

        serverListEntries = new LinkedList<>();
        ListEntrySelectCallback listEntrySelectCallback = listEntry -> {
            for (ServerListEntry serverListEntry : serverListEntries) {
                if (serverListEntry != listEntry) {
                    serverListEntry.unselect();
                }
            }
            serverSelectCallback.onServerSelected(listEntry);
        };

        new ServerFinder(server -> {
            //Server Added
            ServerListEntry serverListEntry = new ServerListEntry(super.widthProperty(), server, listEntrySelectCallback);
            serverListEntries.add(serverListEntry);
            serverList.getChildren().add(serverListEntry);
        }, server -> {
            //Server Removed
            for (ServerListEntry serverListEntry : serverListEntries) {
                if (serverListEntry.getServer().equals(server)) {
                    serverList.getChildren().remove(serverListEntry);
                    serverListEntries.remove(serverListEntry);
                    break;
                }
            }
        });
    }

    public boolean selectServer(Server server) {
        for (ServerListEntry serverListEntry : serverListEntries) {
            if (serverListEntry.getServer().getAddress().equals(server.getAddress())
                && serverListEntry.getServer().getPort() == server.getPort()) {
                    serverListEntry.select();
                    return true;
            }
        }
        return false;
    }

    public void deselect() {
        for (ServerListEntry serverListEntry : serverListEntries) {
            serverListEntry.unselect();
        }
    }

}
