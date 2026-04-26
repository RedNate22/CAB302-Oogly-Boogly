package com.mathcat.mathcat.services;

import com.mathcat.mathcat.models.Cat;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;

/**
 * Drives the periodic stat decay loop for the cat using a JavaFX Timeline.
 */
public class CatScheduler {
    private Timeline timeline;

    /**
     * Starts the decay timeline, firing every minute.
     * 
     * @param cat the cat to apply decay to
     */
    public void start(Cat cat) {
        // Every 1 minute, fire onTick to apply stat decay and energy regeneration to the cat
        // Duration.minutes(1) sets the interval, change to Duration.seconds(x) for faster ticking
        // during testing
        timeline = new Timeline(new KeyFrame(Duration.minutes(1), e -> onTick(cat)));

        // INDEFINITE means the timeline repeats forever until stop() is called
        timeline.setCycleCount(Timeline.INDEFINITE);

        // Start timer
        timeline.play();
    }

    /**
     * Stops the decay timeline if it is running.
     */
    public void stop() {
        if (timeline != null) {
            timeline.stop();
        }
    }

    /**
     * Called on each timeline tick to apply stat decay and energy regeneration.
     * 
     * @param cat the cat to update
     */
    public void onTick(Cat cat) {
        CatService.decreaseHappiness(cat, CatService.HAPPINESS_DECAY_RATE);
        CatService.decreaseFullness(cat, CatService.FULLNESS_DECAY_RATE);
        CatService.applyHungerPenalty(cat);
        CatService.regenerateEnergy(cat);
    }

    /**
     * Restarts the decay timeline.
     * 
     * @param cat the cat to apply decay to
     */
    public void restart(Cat cat) {
        stop();
        start(cat);
    }
}
