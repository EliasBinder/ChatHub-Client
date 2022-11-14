module it.eliasandandrea.chathub {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires javax.jmdns;
    requires java.sql;
    requires javafaker;
    requires ChatHub.Shared;
    requires java.rmi;

    opens it.eliasandandrea.chathub.client to javafx.fxml;
    exports it.eliasandandrea.chathub.client;
}