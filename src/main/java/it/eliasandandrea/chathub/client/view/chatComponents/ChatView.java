package it.eliasandandrea.chathub.client.view.chatComponents;

import it.eliasandandrea.chathub.client.model.persistence.ChatHistory;
import it.eliasandandrea.chathub.client.model.persistence.Persistence;
import it.eliasandandrea.chathub.client.view.ResourceLoader;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.LinkedList;

public class ChatView extends VBox {

    private String currentUUID;
    private VBox chatHistory;

    public ChatView(){
        super.getStyleClass().add("background");
        super.setMinWidth(400);
        super.setSpacing(20);
        super.setAlignment(Pos.BOTTOM_CENTER);

        chatHistory = new VBox();
        chatHistory.setAlignment(Pos.BOTTOM_CENTER);
        chatHistory.getStyleClass().add("background");

        //TODO edit
        MessageEntry testMsg = new MessageEntry(super.widthProperty(), "Test from other user", "Andrea", false, true);
        MessageEntry testMsg2 = new MessageEntry(super.widthProperty(), "Test from myself", "", true, true);
        //test with very long message
        MessageEntry testMsg3 = new MessageEntry(super.widthProperty(), "Test from other user with a very long message that should be wrapped", "Someone", false, true);
        chatHistory.getChildren().addAll(testMsg, testMsg2, testMsg3);

        ScrollPane chatHistorySP = new ScrollPane();
        chatHistorySP.getStyleClass().add("background");
        chatHistorySP.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        chatHistorySP.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        chatHistorySP.minWidthProperty().bind(super.widthProperty());
        chatHistorySP.setFitToHeight(true);
        chatHistorySP.setFitToWidth(true);
        chatHistorySP.setContent(chatHistory);
        VBox.setVgrow(chatHistorySP, Priority.ALWAYS);

        HBox chatInputContainer = new HBox();
        chatInputContainer.getStyleClass().add("chat-input-container");
        chatInputContainer.setAlignment(Pos.CENTER_LEFT);
        chatInputContainer.setSpacing(10);
        TextField input = new TextField();
        input.setPromptText("Type your message here...");
        input.getStyleClass().add("chat-input");
        input.prefWidthProperty().bind(super.widthProperty().subtract(85));
        chatInputContainer.getChildren().add(input);

        StackPane attachmentContainer = new StackPane();
        attachmentContainer.getStyleClass().add("chat-input-icon-container");
        attachmentContainer.setPrefWidth(30);
        attachmentContainer.setPrefHeight(30);
        attachmentContainer.setAlignment(Pos.CENTER);
        ImageView attachment = new ImageView(new Image(ResourceLoader.loadImage("attach.png")));
        attachment.getStyleClass().add("chat-input-icon");
        attachment.setFitWidth(23);
        attachment.setFitHeight(23);
        attachmentContainer.getChildren().add(attachment);
        chatInputContainer.getChildren().add(attachmentContainer);

        StackPane sendContainer = new StackPane();
        sendContainer.getStyleClass().add("chat-input-icon-container");
        sendContainer.setPrefWidth(35);
        sendContainer.setPrefHeight(35);
        sendContainer.setAlignment(Pos.CENTER);
        ImageView send = new ImageView(new Image(ResourceLoader.loadImage("send.png")));
        send.getStyleClass().add("chat-input-icon");
        send.setFitWidth(26);
        send.setFitHeight(26);
        sendContainer.getChildren().add(send);
        chatInputContainer.getChildren().add(sendContainer);

        super.getChildren().addAll(chatHistorySP, chatInputContainer);
    }

    public void setCurrentUUID(String uuid){
        if (uuid == currentUUID)
            return;
        this.currentUUID = uuid;
        chatHistory.getChildren().clear();
        chatHistory.getChildren().addAll(ChatHistory.getInstance().getMessages(uuid));
    }

}
