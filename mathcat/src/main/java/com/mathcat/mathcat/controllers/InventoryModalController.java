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
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

public class InventoryModalController {
    private Cat cat; // needs to be scoped here to be accessible by onSubmit()

    @FXML
    private Label petNameLabel;

    @FXML
    public void initialize() {
        cat = CatDAO.load(UserDAO.currentUser.getId());
        if (cat != null) {
            // load amount of items user has
            }
        }

    

}
