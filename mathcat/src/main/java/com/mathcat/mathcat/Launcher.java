package com.mathcat.mathcat;

import javafx.application.Application;

/** Entry point that works around JavaFX module restrictions on direct Application launch. */
public class Launcher {
    private Launcher() {}

    /**
     * Delegates to {@link Application#launch(Class, String...)} to start the JavaFX application.
     *
     * @param args command-line arguments passed through to the application
     */
    public static void main(String[] args) {
        Application.launch(MathCatApp.class, args);
    }
}
