package it.eliasandandrea.chathub.view.chatComponents;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.css.PseudoClass;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextAlignment;
import org.kordamp.bootstrapfx.scene.layout.Panel;

public class MessageEntry extends HBox {

    public MessageEntry(ReadOnlyDoubleProperty parentWidth, String message, boolean isOwn){
        super.prefWidthProperty().bind(parentWidth);
        super.getStyleClass().add("message-container");

        StackPane panel = new StackPane();
        panel.getStyleClass().add("message");
        panel.maxWidthProperty().bind(parentWidth.multiply(.7));
        panel.setMinHeight(50);

        Label messageLbl = new Label(message);
        messageLbl.getStyleClass().add("message-lbl");
        messageLbl.setWrapText(true);
        messageLbl.setTextAlignment(TextAlignment.JUSTIFY);

        panel.getChildren().add(messageLbl);

        super.getChildren().add(panel);

        if (isOwn) {
            super.setAlignment(Pos.CENTER_RIGHT);
            panel.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), true);
            messageLbl.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), true);
        }else{
            super.setAlignment(Pos.CENTER_LEFT);
            panel.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), false);
            messageLbl.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), false);
        }

    }

}
