/** Module descriptor for the MathCat application. */
module com.mathcat.mathcat {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires com.almasb.fxgl.all;

    requires java.net.http;
    requires com.google.gson;
    requires io.github.cdimascio.dotenv.java;
    requires transitive java.sql;
    requires annotations;
    requires org.slf4j;
    requires javafx.base;
    requires jbcrypt;
    requires org.controlsfx.controls;

    opens com.mathcat.mathcat to javafx.fxml;
    opens com.mathcat.mathcat.controllers to javafx.fxml;
    opens com.mathcat.mathcat.models;
    opens com.mathcat.mathcat.services;
    opens com.mathcat.mathcat.dao;

    exports com.mathcat.mathcat;
    exports com.mathcat.mathcat.controllers;
    exports com.mathcat.mathcat.models;
    exports com.mathcat.mathcat.dao;
    exports com.mathcat.mathcat.database;
    exports com.mathcat.mathcat.services;
}