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
public class CatScheduler {
    private static CatScheduler instance;
    private Timeline timeline;

    private static final Logger log = LoggerFactory.getLogger(CatScheduler.class);

    private CatScheduler() {}

    /**
     * @return the single shared CatScheduler instance
     */
    public static CatScheduler getInstance() {
        if (instance == null) {
            instance = new CatScheduler();
        }
        return instance;
    }

    /**
     * Starts the decay timeline, firing every minute. Stops any existing timeline first so
     * calling {@link #start(Cat)} again (e.g. on re-entering the home screen) never stacks decay ticks.
     *
     * @param cat the cat to apply decay to
     */
    public void start(Cat cat) {
        stop(); // ensure no existing timeline is still running before creating a new one

        // Duration.minutes(1) sets the interval; change to Duration.seconds(x) for faster ticking during testing
        timeline = new Timeline(new KeyFrame(Duration.minutes(1), e -> onTick(cat)));

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
     * 
     * @param cat the cat to update
     */
    public void onTick(Cat cat) {
        log.debug("Tick fired - happiness: {}, fullness: {}, energy: {}", cat.getHappiness(), cat.getFullness(), cat.getEnergy());
        CatService.decreaseHappiness(cat, CatService.HAPPINESS_DECAY_RATE);
        CatService.decreaseFullness(cat, CatService.FULLNESS_DECAY_RATE);
        CatService.applyHungerPenalty(cat);
        CatService.regenerateEnergy(cat);
        log.debug("After tick - happiness: {}, fullness: {}, energy: {}", cat.getHappiness(), cat.getFullness(), cat.getEnergy());
    }

    /**
     * Restarts the decay timeline. Equivalent to calling {@link #stop()} then {@link #start(Cat)}.
     *
     * @param cat the cat to apply decay to
     */
    public void restart(Cat cat) {
        stop();
        start(cat);
    }
}
