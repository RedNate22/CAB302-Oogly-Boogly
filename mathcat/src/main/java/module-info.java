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

    opens com.mathcat.mathcat to javafx.fxml;

    exports com.mathcat.mathcat;
}
