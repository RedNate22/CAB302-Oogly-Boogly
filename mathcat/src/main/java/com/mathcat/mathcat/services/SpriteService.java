package com.mathcat.mathcat.services;

import javafx.scene.image.Image;

public class SpriteService {
    private SpriteService() {}

    public static Image load(String path) {
        return new Image(SpriteService.class.getResourceAsStream(path));
    }
}
