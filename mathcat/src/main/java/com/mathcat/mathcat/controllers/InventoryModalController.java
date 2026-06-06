package com.mathcat.mathcat.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mathcat.mathcat.dao.CatDAO;
import com.mathcat.mathcat.dao.ItemDAO;
import com.mathcat.mathcat.dao.UserDAO;
import com.mathcat.mathcat.models.Cat;
import com.mathcat.mathcat.models.Item;
import com.mathcat.mathcat.models.SpriteConstants;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * Controller for the inventory modal screen. Handles item selection and cat
 * appearance changes.
 */
public class InventoryModalController {

    private static final Logger LOG = LoggerFactory.getLogger(InventoryModalController.class);

    /** Creates a new InventoryModalController. */
    public InventoryModalController() {
    }

    private Cat cat; // needs to be scoped here to be accessible by onSubmit()
    private double catLevel;
    private ArrayList<Item> catItem;

    // Callback function to allow Home and Inventory controllers to talk to each
    // other
    // when updating the sprite accessories
    private Consumer<String> onItemSelectCallback;

    private Consumer<String> onReactCallback;

    @FXML
    private Label petNameLabel;

    @FXML
    private ImageView imageView;

    @FXML
    private ImageView viewCurrentPetImage;

    private String applySleepingCatSprite = null;

    @FXML
    private ImageView viewCurrentAccessoryImage;

    private String selectedAccessorySpritePath = null;

    @FXML
    private ImageView viewCurrentReactionImage;

    private String appliedReactionStringPath = null;

    private PauseTransition reactTimer;

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

    @FXML
    private Label tunaItemAmount;
    @FXML
    private Label milkItemAmount;
    @FXML
    private Label kibbleItemAmount;

    @FXML
    private Label yarnItemAmount;
    @FXML
    private Label laserItemAmount;
    @FXML
    private Label catnipItemAmount;
    @FXML
    private Label energyNapItemAmount;

    /**
     * Loads the current user's cat and its inventory on screen load.
     */
    @FXML
    public void initialize() {
        cat = CatDAO.load(UserDAO.currentUser.getId());
        if (cat != null) {
            catLevel = cat.getLevel();
            catItem = cat.getItems();
            LOG.debug("inventory: {} item(s)", catItem.size());
        } else {
            LOG.warn("inventory modal opened with no cat for user: {}",
                    UserDAO.currentUser.getUsername());
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
     * Handles the logic of callback to Home screen
     * Handles the change cat accessory button action.
     *
     * @param event the button click event
     * @throws IOException if the next screen cannot be loaded
     */
    public void setOnItemSelect(Consumer<String> callback) {
        this.onItemSelectCallback = callback;
    }

    public void setOnReact(Consumer<String> callback) {
        this.onReactCallback = callback;
    }

    /**
     * Handles the logic of getting the current selected accessory
     * to then save to DB in Home controller
     * 
     * @return the current selected accessory
     */
    public String getCurrentSelectedPath() {
        return selectedAccessorySpritePath;
    }

    @FXML
    private void onClickClearAccessory() {
        selectedAccessorySpritePath = SpriteConstants.NO_ACCESSORY_SELECTED;
        LOG.debug("accessory cleared");

        if (onItemSelectCallback != null) {
            onItemSelectCallback.accept(selectedAccessorySpritePath);

        }
    }

    @FXML
    private void onClickCowboyHat() {
        selectedAccessorySpritePath = SpriteConstants.COWBOY_HAT;
        LOG.debug("accessory selected: cowboy hat");

        if (onItemSelectCallback != null) {
            onItemSelectCallback.accept(selectedAccessorySpritePath);
        }
    }

    @FXML
    private void onClickMegaCowboyHat() {
        selectedAccessorySpritePath = SpriteConstants.MEGA_COWBOY_HAT;
        LOG.debug("accessory selected: mega cowboy hat");

        if (onItemSelectCallback != null) {
            onItemSelectCallback.accept(selectedAccessorySpritePath);
        }
    }

    @FXML
    private void onClickRedBowtieHat() {
        selectedAccessorySpritePath = SpriteConstants.RED_BOWTIE_HAT;
        LOG.debug("accessory selected: red bowtie hat");

        if (onItemSelectCallback != null) {
            onItemSelectCallback.accept(selectedAccessorySpritePath);
        }
    }

    @FXML
    private void onClickBlueBowtieHat() {
        selectedAccessorySpritePath = SpriteConstants.BLUE_BOWTIE_HAT;
        LOG.debug("accessory selected: blue bowtie hat");

        if (onItemSelectCallback != null) {
            onItemSelectCallback.accept(selectedAccessorySpritePath);
        }
    }

    @FXML
    private void onClickPurpleBowtieHat() {
        selectedAccessorySpritePath = SpriteConstants.PURPLE_BOWTIE_HAT;
        LOG.debug("accessory selected: purple bowtie hat");

        if (onItemSelectCallback != null) {
            onItemSelectCallback.accept(selectedAccessorySpritePath);
        }
    }

    @FXML
    private void onClickGreenBowtieHat() {
        selectedAccessorySpritePath = SpriteConstants.GREEN_BOWTIE_HAT;
        LOG.debug("accessory selected: green bowtie hat");

        if (onItemSelectCallback != null) {
            onItemSelectCallback.accept(selectedAccessorySpritePath);
        }
    }

    @FXML
    private void onClickTunaItem() {
        for (Item item : catItem) {
            if (item.getItemId().equals("FOOD_TUNA")) {
                boolean used = ItemDAO.useItem(cat.getCatId(), item.getItemId());
                if (used) {
                    item.applyItem(cat);
                    int newQty = item.getQuantity() - 1;
                    item.setQuantity(newQty);
                    tunaItemAmount.setText("( " + newQty + " )");
                    if (newQty <= 0) {
                        tunaItemLocked.setVisible(true);
                    }
                    CatDAO.save(cat);
                    LOG.debug("used {}, quantity remaining: {}", item.getItemName(), newQty);

                }
                break;
            }
        }
    }

    @FXML
    private void onClickMilkItem() {
        for (Item item : catItem) {
            if (item.getItemId().equals("FOOD_MILK")) {
                boolean used = ItemDAO.useItem(cat.getCatId(), item.getItemId());
                if (used) {
                    item.applyItem(cat);
                    int newQty = item.getQuantity() - 1;
                    item.setQuantity(newQty);
                    milkItemAmount.setText("( " + newQty + " )");
                    if (newQty <= 0) {
                        milkItemLocked.setVisible(true);
                    }
                    CatDAO.save(cat);
                    LOG.debug("used {}, quantity remaining: {}", item.getItemName(), newQty);
                }
                break;
            }
        }
    }

    @FXML
    private void onClickKibbleItem() {
        for (Item item : catItem) {
            if (item.getItemId().equals("FOOD_KIBBLE")) {
                boolean used = ItemDAO.useItem(cat.getCatId(), item.getItemId());
                if (used) {
                    item.applyItem(cat);
                    int newQty = item.getQuantity() - 1;
                    item.setQuantity(newQty);
                    kibbleItemAmount.setText("( " + newQty + " )");
                    if (newQty <= 0) {
                        kibbleItemLocked.setVisible(true);
                    }
                    CatDAO.save(cat);
                    LOG.debug("used {}, quantity remaining: {}", item.getItemName(), newQty);
                }
                break;
            }
        }
    }

    @FXML
    private void onClickYarnItem() {
        for (Item item : catItem) {
            if (item.getItemId().equals("TOY_BALL")) {
                boolean used = ItemDAO.useItem(cat.getCatId(), item.getItemId());
                if (used) {
                    item.applyItem(cat);
                    int newQty = item.getQuantity() - 1;
                    item.setQuantity(newQty);
                    yarnItemAmount.setText("( " + newQty + " )");
                    if (newQty <= 0) {
                        yarnItemLocked.setVisible(true);
                    }
                    CatDAO.save(cat);
                    LOG.debug("used {}, quantity remaining: {}", item.getItemName(), newQty);
                }
                break;
            }
        }
    }

    @FXML
    private void onClickLaserItem() {
        for (Item item : catItem) {
            if (item.getItemId().equals("TOY_LASER")) {
                boolean used = ItemDAO.useItem(cat.getCatId(), item.getItemId());
                if (used) {
                    item.applyItem(cat);
                    int newQty = item.getQuantity() - 1;
                    item.setQuantity(newQty);
                    laserItemAmount.setText("( " + newQty + " )");
                    if (newQty <= 0) {
                        laserItemLocked.setVisible(true);
                    }
                    CatDAO.save(cat);
                    LOG.debug("used {}, quantity remaining: {}", item.getItemName(), newQty);
                }
                break;
            }
        }
    }

    @FXML
    private void onClickCatnipItem() {
        for (Item item : catItem) {
            if (item.getItemId().equals("TOY_CATNIP")) {
                boolean used = ItemDAO.useItem(cat.getCatId(), item.getItemId());
                if (used) {
                    item.applyItem(cat);
                    int newQty = item.getQuantity() - 1;
                    item.setQuantity(newQty);
                    catnipItemAmount.setText("( " + newQty + " )");
                    if (newQty <= 0) {
                        catnipItemLocked.setVisible(true);
                    }
                    CatDAO.save(cat);
                    LOG.debug("used {}, quantity remaining: {}", item.getItemName(), newQty);
                }
                break;
            }
        }
    }

    @FXML
    private void onClickEnergyNapItem() {
        for (Item item : catItem) {
            if (item.getItemId().equals("ENERGY_NAP")) {
                boolean used = ItemDAO.useItem(cat.getCatId(), item.getItemId());
                if (used) {
                    item.applyItem(cat);
                    int newQty = item.getQuantity() - 1;
                    item.setQuantity(newQty);
                    energyNapItemAmount.setText("( " + newQty + " )");
                    if (newQty <= 0) {
                        energyNapItemLocked.setVisible(true);
                    }
                    CatDAO.save(cat);
                    LOG.debug("used {}, quantity remaining: {}", item.getItemName(), newQty);
                    applySleepyReaction();
                }
                break;
            }
        }
    }

    private void applyHeartReaction() {
        appliedReactionStringPath = SpriteConstants.HEART_REACTION;
        LOG.debug("reaction applied");
    }

    private void applySleepyReaction() {
        appliedReactionStringPath = SpriteConstants.SLEEPY_REACTION;
        LOG.debug("sleepy reaction applied");

        if (onReactCallback != null) {
            onReactCallback.accept(appliedReactionStringPath);
        }

    }

    /**
     * Handles logic of unlocking item if user
     * has at least one of the item
     */
    public void unlockItem() {
        for (Item item : catItem) {
            switch (item.getItemId()) {
                case "FOOD_TUNA":
                    tunaItemTooltip.setText("Gives 30 Fullness Points");
                    tunaItemLocked.setVisible(false);
                    tunaItemAmount.setText("( " + item.getQuantity() + " )");
                    break;
                case "FOOD_MILK":
                    milkItemTooltip.setText("Gives 15 Fullness Points");
                    milkItemLocked.setVisible(false);
                    milkItemAmount.setText("( " + item.getQuantity() + " )");
                    break;
                case "FOOD_KIBBLE":
                    kibbleItemTooltip.setText("Gives 10 Fullness Points");
                    kibbleItemLocked.setVisible(false);
                    kibbleItemAmount.setText("( " + item.getQuantity() + " )");
                    break;
                case "TOY_BALL":
                    yarnItemTooltip.setText("Gives 25 Happiness Points");
                    yarnItemLocked.setVisible(false);
                    yarnItemAmount.setText("( " + item.getQuantity() + " )");
                    break;
                case "TOY_LASER":
                    laserItemTooltip.setText("Gives 15 Happiness Points");
                    laserItemLocked.setVisible(false);
                    laserItemAmount.setText("( " + item.getQuantity() + " )");
                    break;
                case "TOY_CATNIP":
                    catnipItemTooltip.setText("Gives 35 Happiness Points");
                    catnipItemLocked.setVisible(false);
                    catnipItemAmount.setText("( " + item.getQuantity() + " )");
                    break;
                case "ENERGY_NAP":
                    energyNapItemTooltip.setText("Gives 40 Energy Points");
                    energyNapItemLocked.setVisible(false);
                    energyNapItemAmount.setText("( " + item.getQuantity() + " )");
                    break;
                default:
                    break;
            }
        }

    }

    /**
     * Handles logic of unlocking accessory
     * if user has reached specific level
     */
    public void unlockAccessory() {
        LOG.debug("unlocking accessories for level {}", (int) catLevel);
        if (catLevel >= 3) {
            blueBowtieTooltip.setText("");
            blueBowtieTooltip.hide();
            blueBowtieLocked.setVisible(false);
        }
        if (catLevel >= 5) {
            purpleBowtieTooltip.setText("");
            purpleBowtieTooltip.hide();
            Tooltip.uninstall(purpleBowtieLocked, purpleBowtieTooltip);
            purpleBowtieLocked.setVisible(false);
        }
        if (catLevel >= 8) {
            greenBowtieTooltip.setText("");
            greenBowtieTooltip.hide();
            Tooltip.uninstall(greenBowtieLocked, greenBowtieTooltip);
            greenBowtieLocked.setVisible(false);
        }
        if (catLevel >= 10) {
            megaCowboyTooltip.setText("");
            megaCowboyTooltip.hide();
            Tooltip.uninstall(megaCowboyLocked, megaCowboyTooltip);
            megaCowboyLocked.setVisible(false);
        }
    }
}
