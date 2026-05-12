package com.mathcat.mathcat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import com.mathcat.mathcat.dao.CatDAO;
import com.mathcat.mathcat.dao.UserDAO;
import com.mathcat.mathcat.database.DatabaseManager;
import com.mathcat.mathcat.models.Cat;
import com.mathcat.mathcat.services.CatScheduler;
import java.io.IOException;
import java.time.LocalDateTime;

/** JavaFX application entry point. Initialises the database and loads the initial screen. */
public class MathCatApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Initialise the SQLite database and create tables if they don't exist
        DatabaseManager.initialiseDatabase();

        Font.loadFont(getClass().getResourceAsStream(
                "/com/mathcat/mathcat/assets/font/PressStart2P-Regular.ttf"), 12);

        FXMLLoader fxmlLoader = new FXMLLoader(
                MathCatApp.class.getResource("/com/mathcat/mathcat/initial-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 500);
        stage.setTitle("MathCat");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /** Exit safely by stopping the scheduler and saving the cat's latest progress. */
    @Override
    public void stop() throws IOException {
        CatScheduler.getInstance().stop();
        if (UserDAO.currentUser != null) {
            Cat cat = CatDAO.load(UserDAO.currentUser.getId());
            if (cat != null) {
                cat.setLastSaved(LocalDateTime.now());
                CatDAO.save(cat);
            }
        }
    }
}
