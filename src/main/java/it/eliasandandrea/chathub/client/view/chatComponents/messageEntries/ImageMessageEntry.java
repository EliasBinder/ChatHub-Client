package it.eliasandandrea.chathub.client.view.chatComponents.messageEntries;

import it.eliasandandrea.chathub.client.view.chatComponents.MessageEntry;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.ByteArrayInputStream;

public class ImageMessageEntry extends MessageEntry {

    public ImageMessageEntry(ReadOnlyDoubleProperty parentWidth, byte[] message, String sender, boolean isOwn, boolean isGroup) {
        super(parentWidth, message, sender, isOwn, isGroup);
        Pane imageContainer = new Pane();
        Image image = new Image(new ByteArrayInputStream(message));
        ImageView imageView = new ImageView(image);
        imageView.getStyleClass().add("message-img");
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.fitWidthProperty().bind(parentWidth.multiply(.7).subtract(40));
        imageContainer.minHeightProperty().bind(imageView.fitWidthProperty().multiply(image.getHeight() / image.getWidth()));
        imageContainer.getChildren().add(imageView);
        panel.getChildren().add(imageContainer);
        super.getChildren().add(panel);
    }


}
