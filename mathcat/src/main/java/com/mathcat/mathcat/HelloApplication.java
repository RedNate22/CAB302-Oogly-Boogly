package com.mathcat.mathcat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import com.mathcat.mathcat.database.DatabaseManager;


import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Initialise the SQLite database and create tables if they don't exist
        DatabaseManager.initialiseDatabase();

        Font.loadFont(getClass().getResourceAsStream(
                "/com/mathcat/mathcat/assets/font/PressStart2P-Regular.ttf"), 12);

        FXMLLoader fxmlLoader = new FXMLLoader(
                HelloApplication.class.getResource("/com/mathcat/mathcat/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 500);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
}
