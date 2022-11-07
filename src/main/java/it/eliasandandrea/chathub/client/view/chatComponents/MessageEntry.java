package it.eliasandandrea.chathub.client.view.chatComponents;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.css.PseudoClass;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import org.kordamp.bootstrapfx.scene.layout.Panel;

public abstract class MessageEntry extends HBox {

    protected VBox panel;

    public MessageEntry(ReadOnlyDoubleProperty parentWidth, Object message, String sender, boolean isOwn, boolean isGroup) {
        super.prefWidthProperty().bind(parentWidth);
        super.getStyleClass().add("message-container");

        panel = new VBox();
        panel.getStyleClass().add("message");
        panel.maxWidthProperty().bind(parentWidth.multiply(.7));
        panel.setMinHeight(50);

        if (!isOwn && isGroup){
            Label senderLbl = new Label(sender);
            senderLbl.getStyleClass().add("message-sender");
            panel.getChildren().add(senderLbl);
            panel.setSpacing(7);
        }

        if (isOwn) {
            super.setAlignment(Pos.CENTER_RIGHT);
            panel.setAlignment(Pos.CENTER_RIGHT);
            panel.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), true);
        }else{
            super.setAlignment(Pos.CENTER_LEFT);
            panel.setAlignment(Pos.CENTER_LEFT);
            panel.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), false);
        }

    }

}
