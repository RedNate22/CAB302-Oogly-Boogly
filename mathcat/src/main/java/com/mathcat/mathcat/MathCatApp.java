package com.mathcat.mathcat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import com.mathcat.mathcat.database.DatabaseManager;

public class MathCatApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        DatabaseManager.initialiseDatabase(); // setup db, create tables

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
