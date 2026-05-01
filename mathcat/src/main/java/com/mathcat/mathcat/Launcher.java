package com.mathcat.mathcat;

import javafx.application.Application;

/** Entry point that works around JavaFX module restrictions on direct Application launch. */
public class Launcher {
    public static void main(String[] args) {
        Application.launch(MathCatApp.class, args);
    }
}
