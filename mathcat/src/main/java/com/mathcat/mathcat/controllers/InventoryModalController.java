package com.mathcat.mathcat.controllers;

import com.mathcat.mathcat.dao.CatDAO;
import com.mathcat.mathcat.dao.UserDAO;
import com.mathcat.mathcat.models.Cat;
import com.mathcat.mathcat.models.Item;
import com.mathcat.mathcat.models.SpriteConstants;
import com.mathcat.mathcat.services.CatScheduler;
import com.mathcat.mathcat.services.CatService;
import com.mathcat.mathcat.services.SpriteService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * Controller for the inventory modal screen. Handles item selection and cat appearance changes.
 */
public class InventoryModalController {

    /** Creates a new InventoryModalController. */
    public InventoryModalController() {}

    private Cat cat; // needs to be scoped here to be accessible by onSubmit()
    private double catLevel;
    private ArrayList<Item> catItem;

    // Callback function to allow Home and Inventory controllers to talk to each other
    // when updating the sprite accessories
    private Consumer<String> onItemSelectCallback;

    @FXML
    private Label petNameLabel;

    @FXML
    private ImageView imageView;

    @FXML
    private ImageView viewCurrentAccessoryImage;

    private String selectedAccessorySpritePath = null;

    @FXML
    private Pane megaCowboyLocked;
    @FXML
    private Pane blueBowtieLocked;
    @FXML
    private Pane purpleBowtieLocked;
    @FXML
    private Pane greenBowtieLocked;

    @FXML
    private Pane tunaItemLocked;
    @FXML
    private Pane milkItemLocked;
    @FXML
    private Pane kibbleItemLocked;

    @FXML
    private Pane yarnItemLocked;
    @FXML
    private Pane laserItemLocked;
    @FXML
    private Pane catnipItemLocked;
    @FXML
    private Pane energyNapItemLocked;

    @FXML
    private Tooltip megaCowboyTooltip;
    @FXML
    private Tooltip blueBowtieTooltip;
    @FXML
    private Tooltip purpleBowtieTooltip;
    @FXML
    private Tooltip greenBowtieTooltip;

    @FXML
    private Tooltip tunaItemTooltip;
    @FXML
    private Tooltip milkItemTooltip;
    @FXML
    private Tooltip kibbleItemTooltip;

    @FXML
    private Tooltip yarnItemTooltip;
    @FXML
    private Tooltip laserItemTooltip;
    @FXML
    private Tooltip catnipItemTooltip;
    @FXML
    private Tooltip energyNapItemTooltip;

    /**
     * Loads the current user's cat and its inventory on screen load.
     */
    @FXML
    public void initialize() {
        cat = CatDAO.load(UserDAO.currentUser.getId());
        if (cat != null) {
            catLevel = cat.getLevel();
            catItem = cat.getItems();
        }

        // Make the tooltips show fast
        megaCowboyTooltip.setShowDelay(Duration.millis(30));
        blueBowtieTooltip.setShowDelay(Duration.millis(30));
        purpleBowtieTooltip.setShowDelay(Duration.millis(30));
        greenBowtieTooltip.setShowDelay(Duration.millis(30));
        tunaItemTooltip.setShowDelay(Duration.millis(30));
        milkItemTooltip.setShowDelay(Duration.millis(30));
        kibbleItemTooltip.setShowDelay(Duration.millis(30));
        yarnItemTooltip.setShowDelay(Duration.millis(30));
        laserItemTooltip.setShowDelay(Duration.millis(30));
        catnipItemTooltip.setShowDelay(Duration.millis(30));
        energyNapItemTooltip.setShowDelay(Duration.millis(30));

        Tooltip.install(megaCowboyLocked, megaCowboyTooltip);
        Tooltip.install(blueBowtieLocked, blueBowtieTooltip);
        Tooltip.install(purpleBowtieLocked, purpleBowtieTooltip);
        Tooltip.install(greenBowtieLocked, greenBowtieTooltip);
        Tooltip.install(tunaItemLocked, tunaItemTooltip);
        Tooltip.install(milkItemLocked, milkItemTooltip);
        Tooltip.install(kibbleItemLocked, kibbleItemTooltip);
        Tooltip.install(yarnItemLocked, yarnItemTooltip);
        Tooltip.install(laserItemLocked, laserItemTooltip);
        Tooltip.install(catnipItemLocked, catnipItemTooltip);
        Tooltip.install(energyNapItemLocked, energyNapItemTooltip);

        unlockAccessory();
        unlockItem();
    }

    /**
     * Returns the root stage resolved from the given action event.
     *
     * @param event the action event used to resolve the current stage
     * @return the root Stage of the scene
     */
    private static Stage getRoot(ActionEvent event) {
        Node root = (Node) event.getSource();
        return (Stage) root.getScene().getWindow();
    }

    /**
     * Handles the change cat colour button action.
     *
     * @param event the button click event
     * @throws IOException if the next screen cannot be loaded
     */
    @FXML
    protected void changeCatColour(ActionEvent event) throws IOException {

    }

    /**
     * Handles the logic of callback to Home screen
     * Handles the change cat accessory button action.
     *
     * @param event the button click event
     * @throws IOException if the next screen cannot be loaded
     */
    public void setOnItemSelect(Consumer<String> callback) {
        this.onItemSelectCallback = callback;
    }

    /**
     * Handles the logic of getting the current selected accessory
     * to then save to DB in Home controller
     * @return the current selected accessory
     */
    public String getCurrentSelectedPath() {
        return selectedAccessorySpritePath;
    }

    /**
     * Handles logic of clearing accessory image of current cat appearance
     */
    public void onClickClearAccessory() {
        selectedAccessorySpritePath = SpriteConstants.NO_ACCESSORY_SELECTED;

        if (onItemSelectCallback != null) {
            onItemSelectCallback.accept(selectedAccessorySpritePath);

            
        }
    }

    /**
     * Handles logic of selecting the basic cowboy hat accessory
     */
    public void onClickCowboyHat() {
        selectedAccessorySpritePath = SpriteConstants.COWBOY_HAT;

        if (onItemSelectCallback != null) {
            onItemSelectCallback.accept(selectedAccessorySpritePath);
        }
    }

    /**
     * Handles logic of selecting the mega sized cowboy hat accessory
     */
    public void onClickMegaCowboyHat() {
        selectedAccessorySpritePath = SpriteConstants.MEGA_COWBOY_HAT;

        if (onItemSelectCallback != null) {
            onItemSelectCallback.accept(selectedAccessorySpritePath);
        }
    }

    /**
     * Handles logic of selecting the red bowtie hat accessory
     */
    public void onClickRedBowtieHat() {
        selectedAccessorySpritePath = SpriteConstants.RED_BOWTIE_HAT;

        if (onItemSelectCallback != null) {
            onItemSelectCallback.accept(selectedAccessorySpritePath);
        }
    }

    /**
     * Handles logic of selecting the blue bowtie hat accessory
     */
    public void onClickBlueBowtieHat() {
        selectedAccessorySpritePath = SpriteConstants.BLUE_BOWTIE_HAT;

        if (onItemSelectCallback != null) {
            onItemSelectCallback.accept(selectedAccessorySpritePath);
        }
    }

    /**
     * Handles logic of selecting the purple bowtie hat accessory
     */
    public void onClickPurpleBowtieHat() {
        selectedAccessorySpritePath = SpriteConstants.PURPLE_BOWTIE_HAT;

        if (onItemSelectCallback != null) {
            onItemSelectCallback.accept(selectedAccessorySpritePath);
        }
    }

    /**
     * Handles logic of selecting the green bowtie hat accessory
     */
    public void onClickGreenBowtieHat() {
        selectedAccessorySpritePath = SpriteConstants.GREEN_BOWTIE_HAT;

        if (onItemSelectCallback != null) {
            onItemSelectCallback.accept(selectedAccessorySpritePath);
        }
    }

    /**
     * Handles logic of selecting the tuna item
     */
    public void onClickTunaItem() {}

    /**
     * Handles logic of selecting the milk item
     */
    public void onClickMilkItem() {}

    /**
     * Handles logic of selecting the kibble item
     */
    public void onClickKibbleItem() {}

    /**
     * Handles logic of selecting the yarn ball item
     */
    public void onClickYarnItem() {}

    /**
     * Handles logic of selecting the laser item
     */
    public void onClickLaserItem() {}

    /**
     * Handles logic of selecting the catnip item
     */
    public void onClickCatnipItem() {}

    /**
     * Handles logic of selecting the energy nap item
     */
    public void onClickEnergyNapItem() {}

    /**
     * Handles logic of unlocking item if user
     * has at least one of the item
     */
    public void unlockItem() {
        for (Item item : catItem) {
            // Food Items
            if (item.getItemId().equals("FOOD_TUNA")) {
                tunaItemTooltip.setText("Gives 30 Fullness Points");
                tunaItemLocked.setVisible(false);
                break;
            }
            if (item.getItemId().equals("FOOD_MILK")) {
                milkItemTooltip.setText("Gives 15 Fullness Points");
                milkItemLocked.setVisible(false);
                break;
            }
            if (item.getItemId().equals("FOOD_KIBBLE")) {
                kibbleItemTooltip.setText("Gives 10 Fullness Points");
                kibbleItemLocked.setVisible(false);
                break;
            }

            // Toy Items
            if (item.getItemId().equals("TOY_BALL")) {
                yarnItemTooltip.setText("Gives 25 Happiness Points");
                yarnItemLocked.setVisible(false);
                break;
            }
            if (item.getItemId().equals("TOY_LASER")) {
                laserItemTooltip.setText("Gives 15 Happiness Points");
                laserItemLocked.setVisible(false);
                break;
            }
            if (item.getItemId().equals("TOY_CATNIP")) {
                catnipItemTooltip.setText("Gives 35 Happiness Points");
                catnipItemLocked.setVisible(false);
                break;
            }

            // Energy Item
            if (item.getItemId().equals("ENERGY_NAP")) {
                energyNapItemTooltip.setText("Gives 40 Energy Points");
                energyNapItemLocked.setVisible(false);
                break;
            }
        }
        // update tooltip to show stats
    }

    /**
     * Handles logic of unlocking accessory
     * if user has reached specific level
     */
    public void unlockAccessory() {
    
        if (catLevel >= 3) {
            Tooltip.uninstall(blueBowtieLocked, blueBowtieTooltip);
            blueBowtieLocked.setVisible(false);
        }
        if (catLevel >= 5) {
            Tooltip.uninstall(purpleBowtieLocked, purpleBowtieTooltip);
            purpleBowtieLocked.setVisible(false);
        }
        if (catLevel >= 8) {
            Tooltip.uninstall(greenBowtieLocked, greenBowtieTooltip);
            greenBowtieLocked.setVisible(false);
        }
        if (catLevel >= 10) {
            Tooltip.uninstall(megaCowboyLocked, megaCowboyTooltip);
            megaCowboyLocked.setVisible(false);
        }
    }
}
