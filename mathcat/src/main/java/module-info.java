module com.mathcat.mathcat {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires com.almasb.fxgl.all;
    requires java.sql;

    opens com.mathcat.mathcat to javafx.fxml;
    opens com.mathcat.mathcat.controllers to javafx.fxml;

    exports com.mathcat.mathcat;
    exports com.mathcat.mathcat.controllers;
    exports com.mathcat.mathcat.models;
    exports com.mathcat.mathcat.session;
    exports com.mathcat.mathcat.dao;
    exports com.mathcat.mathcat.database;
}
