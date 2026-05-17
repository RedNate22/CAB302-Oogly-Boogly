package com.mathcat.mathcat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import com.mathcat.mathcat.database.DatabaseManager;
import com.mathcat.mathcat.util.DebugLogger;

import java.io.IOException;

/** JavaFX application entry point. Initialises the database and loads the initial screen. */
public class MathCatApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        DebugLogger.enable();

        // Initialise the SQLite database and create tables if they don't exist
        DatabaseManager.initialiseDatabase();

        Font.loadFont(getClass().getResourceAsStream(
                "/com/mathcat/mathcat/assets/font/PressStart2P-Regular.ttf"), 12);

        FXMLLoader fxmlLoader = new FXMLLoader(
                MathCatApp.class.getResource("/com/mathcat/mathcat/initial-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 500);
        stage.setTitle("MathCat");
        stage.setScene(scene);
        stage.show();
    }
}