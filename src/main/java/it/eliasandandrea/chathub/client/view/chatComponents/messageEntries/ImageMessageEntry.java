package it.eliasandandrea.chathub.client.view.chatComponents.messageEntries;

import it.eliasandandrea.chathub.client.view.chatComponents.MessageEntry;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.css.PseudoClass;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.TextAlignment;

import java.io.ByteArrayInputStream;

public class ImageMessageEntry extends MessageEntry {

    public ImageMessageEntry(ReadOnlyDoubleProperty parentWidth, byte[] message, String sender, boolean isOwn, boolean isGroup) {
        super(parentWidth, message, sender, isOwn, isGroup);
        ImageView imageView = new ImageView(new Image(new ByteArrayInputStream(message)));
        imageView.getStyleClass().add("message-img");
        imageView.setPreserveRatio(true);
        imageView.fitWidthProperty().bind(parentWidth.multiply(.7).subtract(40));
        panel.getChildren().add(imageView);
        if (isOwn) {
            imageView.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), true);
        }else{
            imageView.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), false);
        }
    }


}
