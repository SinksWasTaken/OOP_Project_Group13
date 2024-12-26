module com.example.group13 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires de.jensd.fx.glyphs.fontawesome;
    requires mysql.connector.j;
    requires java.sql;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens com.example.group13 to javafx.fxml;
    exports com.example.group13;
    exports com.example.group13.Controllers;
    exports com.example.group13.Controllers.Admin;
    exports com.example.group13.Controllers.Cashier;
    exports com.example.group13.Controllers.Manager;
    exports com.example.group13.Models;
    exports com.example.group13.Views;


}