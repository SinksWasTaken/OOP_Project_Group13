module com.group13 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires mysql.connector.j;
    requires java.sql;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.desktop;
    opens com.group13.Controllers to javafx.fxml;
    opens com.group13.Controllers.Admin to javafx.fxml;
<<<<<<< Updated upstream
    opens com.group13.Controllers.Manager to javafx.fxml;
=======
    opens com.group13.Controllers.Admin.MenuOperations to javafx.fxml;
    opens com.group13.Controllers.Manager to javafx.fxml;
    opens com.group13.Controllers.Manager.MenuOperations to javafx.fxml;
>>>>>>> Stashed changes
    exports com.group13;
    exports com.group13.Controllers;
    exports com.group13.Controllers.Cashier;
    exports com.group13.Controllers.Admin;
<<<<<<< Updated upstream
=======
    exports com.group13.Controllers.Admin.MenuOperations;
>>>>>>> Stashed changes
    exports com.group13.Controllers.Manager;
    exports com.group13.Controllers.Manager.MenuOperations;
    exports com.group13.Models;
    exports com.group13.Views;
}