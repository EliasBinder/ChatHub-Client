package it.eliasandandrea.chathub.view;

import javafx.scene.text.Font;

import java.io.InputStream;

public class ResourceLoader {

    public static String loadStylesheet(String path){
        return ResourceLoader.class.getResource("/it/eliasandandrea/chathub/view/css/" + path).toExternalForm();
    }

    public static InputStream loadImage(String path){
        return ResourceLoader.class.getResourceAsStream("/it/eliasandandrea/chathub/view/images/" + path);
    }

    public static void loadFont(String path){
        Font.loadFont(ResourceLoader.class.getResource("/it/eliasandandrea/chathub/view/fonts/" + path).toExternalForm(), 10);
    }

}
