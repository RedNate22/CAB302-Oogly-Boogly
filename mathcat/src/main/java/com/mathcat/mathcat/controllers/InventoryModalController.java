package com.mathcat.mathcat.controllers;

import com.mathcat.mathcat.dao.CatDAO;
import com.mathcat.mathcat.dao.UserDAO;
import com.mathcat.mathcat.models.Cat;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.function.Consumer;

/** 
*
*/
public class InventoryModalController {
    private Cat cat; // needs to be scoped here to be accessible by onSubmit()

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

    @FXML
    public void initialize() {
        cat = CatDAO.load(UserDAO.currentUser.getId());
        if (cat != null) {
            // load amount of items user has
        }

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


    }

    /**
     * Handles the logic of callback to Home screen
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
     * Handles logic of unlocking item or accessory if user
     * has either at least one of the item 
     * or leveled up for the accessory
     */
    public void unlockItem() {
        
    }
}
