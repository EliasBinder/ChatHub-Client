package it.eliasandandrea.chathub.client.view.chatComponents;

import it.eliasandandrea.chathub.client.model.persistence.ChatHistory;
import it.eliasandandrea.chathub.client.model.persistence.Persistence;
import it.eliasandandrea.chathub.client.view.ResourceLoader;
import it.eliasandandrea.chathub.client.view.chatComponents.messageEntries.ImageMessageEntry;
import it.eliasandandrea.chathub.client.view.chatComponents.messageEntries.TextMessageEntry;
import it.eliasandandrea.chathub.shared.crypto.CryptManager;
import it.eliasandandrea.chathub.shared.model.ChatEntity;
import it.eliasandandrea.chathub.shared.model.Group;
import it.eliasandandrea.chathub.shared.model.User;
import it.eliasandandrea.chathub.shared.protocol.Message;
import it.eliasandandrea.chathub.shared.protocol.messageTypes.ImageMessage;
import it.eliasandandrea.chathub.shared.protocol.messageTypes.TextMessage;
import it.eliasandandrea.chathub.shared.protocol.sharedEvents.MessageEvent;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.PrivateKey;

public class ChatView extends VBox {

    private String currentUUID;
    private VBox chatHistory;
    private ScrollPane chatHistorySP;

    public ChatView(CryptManager cryptManager){
        super.getStyleClass().add("background");
        super.setMinWidth(400);
        super.setSpacing(20);
        super.setAlignment(Pos.BOTTOM_CENTER);

        chatHistory = new VBox();
        chatHistory.setAlignment(Pos.BOTTOM_CENTER);
        chatHistory.getStyleClass().add("background");

        chatHistorySP = new ScrollPane();
        chatHistorySP.getStyleClass().add("background");
        chatHistorySP.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        chatHistorySP.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        chatHistorySP.minWidthProperty().bind(super.widthProperty());
        chatHistorySP.setFitToWidth(true);
        chatHistorySP.setContent(chatHistory);

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
        attachmentContainer.setOnMouseClicked(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select an image to send");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
                    new FileChooser.ExtensionFilter("All Files", "*.*")
            );
            File selectedFile = fileChooser.showOpenDialog(null);
            if (selectedFile != null && selectedFile.exists()){
                try {
                    FileInputStream fis = new FileInputStream(selectedFile);
                    byte[] fileContent = new byte[(int) selectedFile.length()];
                    fis.read(fileContent);
                    ImageMessage imageMessage = new ImageMessage();
                    imageMessage.image = fileContent;
                    ChatEntity receiver = Persistence.getInstance().chats.stream().filter(c -> c.getUUID().equals(currentUUID)).findFirst().get();
                    MessageEvent event = new MessageEvent(
                            Persistence.getInstance().myUUID,
                            receiver,
                            imageMessage
                    );
                    Persistence.getInstance().client.sendEvent(event);
                    ImageMessageEntry messageEntry = new ImageMessageEntry(super.widthProperty(), fileContent, "", true, Group.class.equals(receiver.getClass()));
                    ChatHistory.getInstance().addMessage(currentUUID, messageEntry);
                    chatHistory.getChildren().add(messageEntry);
                    chatHistorySP.setVvalue(1.0);
                } catch (Exception ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error while sending the image");
                    alert.setContentText("An error occurred while sending the image. Please try again.");
                    alert.showAndWait();
                    ex.printStackTrace();
                }
            }
        });

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
        sendContainer.setOnMouseClicked(e -> {
            TextMessage msg = new TextMessage();
            msg.message = input.getText();
            try {
                ChatEntity receiver = Persistence.getInstance().chats.stream().filter(c -> c.getUUID().equals(currentUUID)).findFirst().get();
                MessageEvent event = new MessageEvent(
                        Persistence.getInstance().myUUID,
                        receiver,
                        msg
                );
                Persistence.getInstance().client.sendEvent(event);
                TextMessageEntry messageEntry = new TextMessageEntry(super.widthProperty(), input.getText(), "", true, Group.class.equals(receiver.getClass()));
                ChatHistory.getInstance().addMessage(currentUUID, messageEntry);
                chatHistory.getChildren().add(messageEntry);
                input.setText("");
                chatHistorySP.setVvalue(1.0);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        Persistence.getInstance().serverEventCallbackRouter.setOnMessageCallback((e) -> {
            String senderUUID = e.senderUUID;
            User sender = Persistence.getInstance().chats.stream().filter(chatEntity -> chatEntity.getUUID().equals(senderUUID)).map(chatEntity -> (User)chatEntity).findFirst().orElse(null);
            PrivateKey privateKey;
            boolean isGroup = false;
            if (e.receiverUUID.equals(Persistence.getInstance().myUUID)) {
                privateKey = cryptManager.privateKey;
            } else {
                isGroup = true;
                privateKey = Persistence.getInstance().chats.stream().filter(chatEntity -> chatEntity.getUUID().equals(e.receiverUUID)).map(s -> (Group) s).findFirst().get().getPrivateKey();
            }
            Message message = e.getMessage(privateKey);
            MessageEntry entry = null;
            if (TextMessage.class.equals(message.getClass())) {
                TextMessage textMessage = (TextMessage) message;
                entry = new TextMessageEntry(super.widthProperty(), textMessage.message, sender.getUsername(), false, isGroup);
            } else if (ImageMessage.class.equals(message.getClass())) {
                ImageMessage imageMessage = (ImageMessage) message;
                entry = new ImageMessageEntry(super.widthProperty(), imageMessage.image, sender.getUsername(), false, isGroup);
            }
            ChatHistory.getInstance().addMessage(isGroup? e.receiverUUID : e.senderUUID, entry);
            if ((isGroup? e.receiverUUID : e.senderUUID).equals(currentUUID)){
                MessageEntry finalEntry = entry;
                Platform.runLater(() -> chatHistory.getChildren().add(finalEntry));
            }
        });

        super.getChildren().addAll(chatHistorySP, chatInputContainer);
    }

    public void setCurrentUUID(String uuid){
        if (uuid.equals(currentUUID))
            return;
        this.currentUUID = uuid;
        chatHistory.getChildren().clear();
        chatHistory.getChildren().addAll(ChatHistory.getInstance().getMessages(uuid));
        chatHistorySP.setVvalue(1.0);
    }

}
