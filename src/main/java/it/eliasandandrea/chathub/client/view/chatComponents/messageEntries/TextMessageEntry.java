package it.eliasandandrea.chathub.client.view.chatComponents.messageEntries;

import it.eliasandandrea.chathub.client.view.chatComponents.MessageEntry;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.css.PseudoClass;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;

public class TextMessageEntry extends MessageEntry {

    public TextMessageEntry(ReadOnlyDoubleProperty parentWidth, String message, String sender, boolean isOwn, boolean isGroup) {
        super(parentWidth, message, sender, isOwn, isGroup);
        Label messageLbl = new Label(message);
        messageLbl.getStyleClass().add("message-lbl");
        messageLbl.setWrapText(true);
        messageLbl.setTextAlignment(TextAlignment.JUSTIFY);
        panel.getChildren().add(messageLbl);
        if (isOwn) {
            messageLbl.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), true);
        }else{
            messageLbl.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), false);
        }
        super.getChildren().add(panel);
    }

}
