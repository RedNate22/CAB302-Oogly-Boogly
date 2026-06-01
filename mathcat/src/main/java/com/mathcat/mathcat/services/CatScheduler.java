package com.mathcat.mathcat.services;

import com.mathcat.mathcat.models.Cat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;

/**
 * Drives the periodic stat decay loop for the cat using a JavaFX Timeline.
 * Singleton — use {@link #getInstance()} to ensure only one timeline runs at a time.
 */
public final class CatScheduler {
    private static CatScheduler instance;
    private Timeline timeline;
    private Cat cat;
    private Runnable onTickCallback;

    private static final Logger LOG = LoggerFactory.getLogger(CatScheduler.class);

    private CatScheduler() {}

    /**
     * Returns the single shared CatScheduler instance, creating it if it does not yet exist.
     *
     * @return the single shared CatScheduler instance
     */
    public static CatScheduler getInstance() {
        if (instance == null) {
            instance = new CatScheduler();
        }
        return instance;
    }

    /**
     * Returns the cat currently being ticked, or null if the scheduler has not been started.
     *
     * @return the cat currently being ticked, or null if the scheduler has not been started
     */
    public Cat getCat() {
        return cat;
    }

    /**
     * Registers a callback to run on the JavaFX Application Thread after each tick.
     * Replaces any previously registered callback.
     *
     * @param callback the UI refresh logic to run after each tick
     */
    public void setOnTick(Runnable callback) {
        this.onTickCallback = callback;
    }

    /**
     * Starts the decay timeline, firing every minute. Stops any existing timeline first so
     * calling {@link #start(Cat)} again (e.g. on re-entering the home screen) never stacks decay ticks.
     *
     * @param cat the cat to apply decay to
     */
    public void start(Cat cat) {
        stop();
        this.cat = cat;

        // Duration.minutes(1) sets the interval; change to Duration.seconds(x) for faster ticking during testing
        timeline = new Timeline(new KeyFrame(Duration.minutes(1), e -> onTick()));

        // INDEFINITE means the timeline repeats forever until stop() is called
        timeline.setCycleCount(Timeline.INDEFINITE);
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
     * Called on each timeline tick to apply stat decay and energy regeneration via {@link CatService}.
     */
    private void onTick() {
        LOG.debug("Tick fired - happiness: {}, fullness: {}, energy: {}",
                        String.format("%.2f", cat.getHappiness()), String.format("%.2f", cat.getFullness()),
                String.format("%.2f", cat.getEnergy()));
        CatService.decreaseHappiness(cat, CatService.HAPPINESS_DECAY_RATE);
        CatService.decreaseFullness(cat, CatService.FULLNESS_DECAY_RATE);
        CatService.applyHungerPenalty(cat);
        CatService.regenerateEnergy(cat);
        LOG.debug("After tick - happiness: {}, fullness: {}, energy: {}",
                        String.format("%.2f", cat.getHappiness()), String.format("%.2f", cat.getFullness()),
                String.format("%.2f", cat.getEnergy()));
        if (onTickCallback != null) {
            onTickCallback.run();
        }
    }
}
