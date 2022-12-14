package it.eliasandandrea.chathub.client.view.serverListComponents;

import com.github.javafaker.Faker;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class ChangeUsernameDialog extends VBox {

    private final TextField username;

    public ChangeUsernameDialog(SimpleStringProperty usernameProperty) {
        super();
        super.setSpacing(10);
        super.setMaxWidth(240);
        super.setMaxHeight(80);
        super.getStyleClass().add("dialog");
        super.setAlignment(Pos.CENTER);
        super.setTranslateY(60);

        Label usernameLbl = new Label("Your Username");
        usernameLbl.getStyleClass().add("dialog-lbl");
        super.getChildren().add(usernameLbl);

        username = new TextField();
        username.getStyleClass().add("dialog-input");
        username.setMinHeight(20);
        username.setAlignment(Pos.CENTER);
        usernameProperty.bind(username.textProperty());

        Faker faker = new Faker();
        username.setText(faker.superhero().prefix()+faker.name().firstName()+faker.address().buildingNumber());

        super.getChildren().add(username);

        super.setVisible(false);
    }

    public void toggle(){
        if(super.isVisible()){
            super.setVisible(false);
        }else{
            super.setVisible(true);
            username.requestFocus();
        }
    }
}
