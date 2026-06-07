package com.mathcat.mathcat.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mathcat.mathcat.dao.CatDAO;
import com.mathcat.mathcat.dao.ItemDAO;
import com.mathcat.mathcat.dao.UserDAO;
import com.mathcat.mathcat.models.Cat;
import com.mathcat.mathcat.models.Item;
import com.mathcat.mathcat.models.SpriteConstants;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Controller for the inventory modal screen. Handles item use and cat accessory selection.
 */
public class InventoryModalController {

    private static final Logger LOG = LoggerFactory.getLogger(InventoryModalController.class);

    private static final String ITEM_FOOD_TUNA   = "FOOD_TUNA";
    private static final String ITEM_FOOD_MILK   = "FOOD_MILK";
    private static final String ITEM_FOOD_KIBBLE = "FOOD_KIBBLE";
    private static final String ITEM_TOY_BALL    = "TOY_BALL";
    private static final String ITEM_TOY_LASER   = "TOY_LASER";
    private static final String ITEM_TOY_CATNIP  = "TOY_CATNIP";
    private static final String ITEM_ENERGY_NAP  = "ENERGY_NAP";

    /** Creates a new InventoryModalController. */
    public InventoryModalController() {}

    private Cat cat;
    private double catLevel;
    private ArrayList<Item> catItem;

    private Consumer<String> onItemSelectCallback;

    private Map<String, String> accessoryIdToPath;
    private Map<String, String> cssIdToItemId;
    private Map<String, Item> itemById;
    private Map<String, Label> itemAmountLabels;
    private Map<String, Pane> itemLockedPanes;

    @FXML private Label petNameLabel;
    @FXML private ImageView imageView;
    @FXML private ImageView viewCurrentAccessoryImage;

    private String selectedAccessorySpritePath = null;

    @FXML private Pane megaCowboyLocked;
    @FXML private Pane blueBowtieLocked;
    @FXML private Pane purpleBowtieLocked;
    @FXML private Pane greenBowtieLocked;

    @FXML private Pane tunaItemLocked;
    @FXML private Pane milkItemLocked;
    @FXML private Pane kibbleItemLocked;

    @FXML private Pane yarnItemLocked;
    @FXML private Pane laserItemLocked;
    @FXML private Pane catnipItemLocked;
    @FXML private Pane energyNapItemLocked;

    @FXML private Tooltip megaCowboyTooltip;
    @FXML private Tooltip blueBowtieTooltip;
    @FXML private Tooltip purpleBowtieTooltip;
    @FXML private Tooltip greenBowtieTooltip;

    @FXML private Tooltip tunaItemTooltip;
    @FXML private Tooltip milkItemTooltip;
    @FXML private Tooltip kibbleItemTooltip;

    @FXML private Tooltip yarnItemTooltip;
    @FXML private Tooltip laserItemTooltip;
    @FXML private Tooltip catnipItemTooltip;
    @FXML private Tooltip energyNapItemTooltip;

    @FXML private Label tunaItemAmount;
    @FXML private Label milkItemAmount;
    @FXML private Label kibbleItemAmount;

    @FXML private Label yarnItemAmount;
    @FXML private Label laserItemAmount;
    @FXML private Label catnipItemAmount;
    @FXML private Label energyNapItemAmount;

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
            catItem = new ArrayList<>();
            LOG.warn("inventory modal opened with no cat for user: {}",
                    UserDAO.currentUser.getUsername());
        }

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

        Tooltip.install(megaCowboyLocked,   megaCowboyTooltip);
        Tooltip.install(blueBowtieLocked,   blueBowtieTooltip);
        Tooltip.install(purpleBowtieLocked, purpleBowtieTooltip);
        Tooltip.install(greenBowtieLocked,  greenBowtieTooltip);
        Tooltip.install(tunaItemLocked,     tunaItemTooltip);
        Tooltip.install(milkItemLocked,     milkItemTooltip);
        Tooltip.install(kibbleItemLocked,   kibbleItemTooltip);
        Tooltip.install(yarnItemLocked,     yarnItemTooltip);
        Tooltip.install(laserItemLocked,    laserItemTooltip);
        Tooltip.install(catnipItemLocked,   catnipItemTooltip);
        Tooltip.install(energyNapItemLocked, energyNapItemTooltip);

        accessoryIdToPath = new HashMap<>();
        accessoryIdToPath.put("cowboyHat",      SpriteConstants.COWBOY_HAT);
        accessoryIdToPath.put("megaCowboyHat",  SpriteConstants.MEGA_COWBOY_HAT);
        accessoryIdToPath.put("redBowtie",      SpriteConstants.RED_BOWTIE_HAT);
        accessoryIdToPath.put("blueBowtie",     SpriteConstants.BLUE_BOWTIE_HAT);
        accessoryIdToPath.put("purpleBowtie",   SpriteConstants.PURPLE_BOWTIE_HAT);
        accessoryIdToPath.put("greenBowtie",    SpriteConstants.GREEN_BOWTIE_HAT);
        accessoryIdToPath.put("clearAccessory", SpriteConstants.NO_ACCESSORY_SELECTED);

        cssIdToItemId = new HashMap<>();
        cssIdToItemId.put("tunaItem",      ITEM_FOOD_TUNA);
        cssIdToItemId.put("milkItem",      ITEM_FOOD_MILK);
        cssIdToItemId.put("kibbleItem",    ITEM_FOOD_KIBBLE);
        cssIdToItemId.put("yarnItem",      ITEM_TOY_BALL);
        cssIdToItemId.put("laserItem",     ITEM_TOY_LASER);
        cssIdToItemId.put("catnipItem",    ITEM_TOY_CATNIP);
        cssIdToItemId.put("energyNapItem", ITEM_ENERGY_NAP);

        itemAmountLabels = new HashMap<>();
        itemAmountLabels.put(ITEM_FOOD_TUNA,   tunaItemAmount);
        itemAmountLabels.put(ITEM_FOOD_MILK,   milkItemAmount);
        itemAmountLabels.put(ITEM_FOOD_KIBBLE, kibbleItemAmount);
        itemAmountLabels.put(ITEM_TOY_BALL,    yarnItemAmount);
        itemAmountLabels.put(ITEM_TOY_LASER,   laserItemAmount);
        itemAmountLabels.put(ITEM_TOY_CATNIP,  catnipItemAmount);
        itemAmountLabels.put(ITEM_ENERGY_NAP,  energyNapItemAmount);

        itemLockedPanes = new HashMap<>();
        itemLockedPanes.put(ITEM_FOOD_TUNA,   tunaItemLocked);
        itemLockedPanes.put(ITEM_FOOD_MILK,   milkItemLocked);
        itemLockedPanes.put(ITEM_FOOD_KIBBLE, kibbleItemLocked);
        itemLockedPanes.put(ITEM_TOY_BALL,    yarnItemLocked);
        itemLockedPanes.put(ITEM_TOY_LASER,   laserItemLocked);
        itemLockedPanes.put(ITEM_TOY_CATNIP,  catnipItemLocked);
        itemLockedPanes.put(ITEM_ENERGY_NAP,  energyNapItemLocked);

        itemById = new HashMap<>();
        for (Item item : catItem) {
            itemById.put(item.getItemId(), item);
        }

        unlockAccessory();
        unlockItem();
    }

    /**
     * Registers a callback invoked when an accessory is selected or cleared.
     *
     * @param callback receives the sprite path of the selected accessory
     */
    public void setOnItemSelect(Consumer<String> callback) {
        this.onItemSelectCallback = callback;
    }

    /**
     * Returns the sprite path of the currently selected accessory, or null if none.
     *
     * @return the selected accessory sprite path
     */
    public String getCurrentSelectedPath() {
        return selectedAccessorySpritePath;
    }

    /**
     * Handles selection of any accessory, including clearing. The clicked node's
     * CSS id is used to look up the corresponding sprite path.
     *
     * @param event the mouse click event
     */
    @FXML
    private void onClickAccessory(MouseEvent event) {
        String cssId = ((Node) event.getSource()).getId();
        String spritePath = accessoryIdToPath.get(cssId);
        if (spritePath == null) {
            return;
        }
        selectedAccessorySpritePath = spritePath;
        LOG.debug("accessory selected: {}", spritePath);
        if (onItemSelectCallback != null) {
            onItemSelectCallback.accept(selectedAccessorySpritePath);
        }
    }

    /**
     * Handles use of any consumable item. The clicked node's CSS id is mapped to
     * an item ID, which is then looked up in O(1) from the item map.
     *
     * @param event the mouse click event
     */
    @FXML
    private void onClickItem(MouseEvent event) {
        String cssId = ((Node) event.getSource()).getId();
        String itemId = cssIdToItemId.get(cssId);
        if (itemId == null) {
            return;
        }
        Item item = itemById.get(itemId);
        if (item == null) {
            return;
        }
        boolean used = ItemDAO.useItem(cat.getCatId(), itemId);
        if (used) {
            item.applyItem(cat);
            int newQty = item.getQuantity() - 1;
            item.setQuantity(newQty);
            itemAmountLabels.get(itemId).setText("( " + newQty + " )");
            if (newQty <= 0) {
                itemLockedPanes.get(itemId).setVisible(true);
            }
            CatDAO.save(cat);
            LOG.debug("used {}, quantity remaining: {}", item.getItemName(), newQty);
        }
    }

    /**
     * Unlocks item slots for items the cat currently has in its inventory.
     */
    private void unlockItem() {
        for (Item item : catItem) {
            switch (item.getItemId()) {
                case ITEM_FOOD_TUNA:
                    tunaItemTooltip.setText("Gives 30 Fullness Points");
                    tunaItemLocked.setVisible(false);
                    tunaItemAmount.setText("( " + item.getQuantity() + " )");
                    break;
                case ITEM_FOOD_MILK:
                    milkItemTooltip.setText("Gives 15 Fullness Points");
                    milkItemLocked.setVisible(false);
                    milkItemAmount.setText("( " + item.getQuantity() + " )");
                    break;
                case ITEM_FOOD_KIBBLE:
                    kibbleItemTooltip.setText("Gives 10 Fullness Points");
                    kibbleItemLocked.setVisible(false);
                    kibbleItemAmount.setText("( " + item.getQuantity() + " )");
                    break;
                case ITEM_TOY_BALL:
                    yarnItemTooltip.setText("Gives 25 Happiness Points");
                    yarnItemLocked.setVisible(false);
                    yarnItemAmount.setText("( " + item.getQuantity() + " )");
                    break;
                case ITEM_TOY_LASER:
                    laserItemTooltip.setText("Gives 15 Happiness Points");
                    laserItemLocked.setVisible(false);
                    laserItemAmount.setText("( " + item.getQuantity() + " )");
                    break;
                case ITEM_TOY_CATNIP:
                    catnipItemTooltip.setText("Gives 35 Happiness Points");
                    catnipItemLocked.setVisible(false);
                    catnipItemAmount.setText("( " + item.getQuantity() + " )");
                    break;
                case ITEM_ENERGY_NAP:
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
     * Unlocks accessory slots for accessories unlocked by the cat's level.
     */
    private void unlockAccessory() {
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
