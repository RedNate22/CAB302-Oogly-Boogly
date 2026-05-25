package com.mathcat.mathcat.services;

import javafx.scene.image.Image;

/**
 * Utility for loading sprite images from classpath resources.
 */
public class SpriteService {
    private SpriteService() {}

    /**
     * Loads an image from the given classpath resource path.
     *
     * @param path the classpath-relative path to the image resource
     * @return the loaded {@link Image}
     */
    public static Image load(String path) {
        return new Image(SpriteService.class.getResourceAsStream(path));
    }
}
