package it.eliasandandrea.chathub.client.view.serverListComponents;

import it.eliasandandrea.chathub.shared.crypto.CryptManager;
import it.eliasandandrea.chathub.shared.util.LocalPaths;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.css.PseudoClass;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.nio.file.Path;

public class KeystorePasswordPane extends StackPane {

    private boolean modeLogin = true;

    public KeystorePasswordPane(ObjectProperty<CryptManager> rsaCipherObjectProperty, ReadOnlyDoubleProperty widthProperty, ReadOnlyDoubleProperty heightProperty) {
        super();
        super.getStyleClass().add("overlay");
        super.minWidthProperty().bind(widthProperty);
        super.minHeightProperty().bind(heightProperty);
        super.setVisible(true);
        super.setAlignment(Pos.CENTER);

        VBox content = new VBox();
        content.getStyleClass().add("content");
        content.setAlignment(Pos.CENTER);
        content.setSpacing(20);
        content.maxWidthProperty().bind(widthProperty.multiply(0.4).add(20));
        content.setMaxHeight(120);

        PasswordField keyPassField = new PasswordField();
        keyPassField.setMinWidth(250);
        keyPassField.setAlignment(Pos.CENTER);
        keyPassField.maxWidthProperty().bind(widthProperty.multiply(0.4));
        keyPassField.getStyleClass().add("dialog-input");
        content.getChildren().add(keyPassField);

        Button okButton = new Button("OK");
        okButton.getStyleClass().add("btn");
        content.getChildren().add(okButton);

        Path dataDir = LocalPaths.getData();
        Path publicKeyPath = dataDir.resolve("public.key");
        Path privateKeyPath = dataDir.resolve("private.key");
        if (!publicKeyPath.toFile().exists() || !privateKeyPath.toFile().exists()) {
            modeLogin = false;
            keyPassField.setPromptText("Insert a new password for the keystore");
        }else {
            keyPassField.setPromptText("Insert the password for the keystore");
        }

        okButton.setOnAction(event -> {
            if (modeLogin) {
                try{
                    CryptManager rsaCipher = new CryptManager(publicKeyPath, privateKeyPath, keyPassField.getText());
                    rsaCipherObjectProperty.set(rsaCipher);
                    super.setVisible(false);
                }catch (Exception ex){
                    keyPassField.setText("");
                    keyPassField.setPromptText("Wrong password");
                    keyPassField.pseudoClassStateChanged(PseudoClass.getPseudoClass("error"), true);
                }
            } else {
                try {
                    CryptManager.init(publicKeyPath, privateKeyPath, keyPassField.textProperty().get());
                    rsaCipherObjectProperty.set(new CryptManager(publicKeyPath, privateKeyPath, keyPassField.getText()));
                    super.setVisible(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        super.getChildren().add(content);
    }

}
