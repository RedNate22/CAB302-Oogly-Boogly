package com.mathcat.mathcat.services;

import java.io.InputStream;
import javafx.scene.image.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility for loading sprite images from classpath resources.
 */
public final class SpriteService {
    private static final Logger LOG = LoggerFactory.getLogger(SpriteService.class);

    private SpriteService() {}

    /**
     * Loads an image from the given classpath resource path.
     * Returns null and logs a warning if the resource cannot be found.
     *
     * @param path the classpath-relative path to the image resource
     * @return the loaded {@link Image}, or null if the resource is not found
     */
    public static Image load(String path) {
        InputStream stream = SpriteService.class.getResourceAsStream(path);
        if (stream == null) {
            LOG.warn("sprite resource not found: {}", path);
            return null;
        }
        return new Image(stream);
    }
}
