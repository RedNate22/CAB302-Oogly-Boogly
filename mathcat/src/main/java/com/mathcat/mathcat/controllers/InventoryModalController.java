package com.mathcat.mathcat.controllers;

import com.mathcat.mathcat.dao.CatDAO;
import com.mathcat.mathcat.dao.UserDAO;
import com.mathcat.mathcat.models.Cat;
import com.mathcat.mathcat.services.CatScheduler;
import com.mathcat.mathcat.services.CatService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Controller for the inventory modal screen. Handles item selection and cat appearance changes.
 */
public final class InventoryModalController {

    /** Creates a new InventoryModalController. */
    public InventoryModalController() {}

    private Cat cat; // needs to be scoped here to be accessible by onSubmit()

    @FXML
    private Label petNameLabel;

    @FXML
    private ImageView imageView;

    private InventoryModalController inventoryModalController;

    /**
     * Loads the current user's cat and its inventory on screen load.
     */
    @FXML
    public void initialize() {
        cat = CatDAO.load(UserDAO.currentUser.getId());
        if (cat != null) {
            // load amount of items user has
        }
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

    // private static Stage getRoot(MouseEvent event) {
    //     Node root = (Node) event.getSource();
    //     return (Stage) root.getScene().getWindow();
    // }

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
     * Handles the change cat accessory button action.
     *
     * @param event the button click event
     * @throws IOException if the next screen cannot be loaded
     */
    @FXML
    protected void changeCatAccessory(ActionEvent event) throws IOException {

    }

    /**
     * Handles the confirm and save button action. Saves changes and closes the modal.
     *
     * @param event the button click event
     * @throws IOException if closing the modal fails
     */
    @FXML
    private void onConfirmSaveDetails(ActionEvent event) throws IOException {
        // save details

        // close modal
        getRoot(event).close();
        getRoot(event).close();
    }

    /**
     * Sets the reference to the parent inventory modal controller.
     *
     * @param inventoryModalController the parent controller instance
     */
    public void setMainController(InventoryModalController inventoryModalController) {
        this.inventoryModalController = inventoryModalController;
    }

}
