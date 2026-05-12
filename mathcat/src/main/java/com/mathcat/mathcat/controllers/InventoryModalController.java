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
*
*/
public class InventoryModalController {
    private Cat cat; // needs to be scoped here to be accessible by onSubmit()

    @FXML
    private Label petNameLabel;

    @FXML
    private ImageView imageView;

    private InventoryModalController inventoryModalController;

    /**
     * @param event
     */
    @FXML
    public void initialize() {
        cat = CatDAO.load(UserDAO.currentUser.getId());
        if (cat != null) {
            // load amount of items user has
        }
    }

    /**
     * @param event
     * @return Stage
     */
    private static Stage getRoot(ActionEvent event) {
        Node root = (Node) event.getSource();
        return (Stage) root.getScene().getWindow();
    }

    /**
     * @param event
     * @return Stage
     */
    private static Stage getRoot(MouseEvent event) {
        Node root = (Node) event.getSource();
        return (Stage) root.getScene().getWindow();
    }

    /**
     * @param event
     * @throws IOException
     */
    @FXML
    protected void changeCatColour(ActionEvent event) throws IOException {

    }

    /**
     * @param event
     * @throws IOException
     */
    @FXML
    protected void changeCatAccessory(ActionEvent event) throws IOException {

    }

    /**
     * @param event
     * @throws IOException
     */
    @FXML
    private void confirmSaveDetails(ActionEvent event) throws IOException {

    }

    /**
     * @param inventoryModalController
     */
    public void setMainController(InventoryModalController inventoryModalController) {
        this.inventoryModalController = inventoryModalController;
    }

}
