package com.game.PrisonEscape.menu;

import com.game.PrisonEscape.PrisonEscapeGame;
import com.game.PrisonEscape.database.PlayerDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PlayerCreatorController extends NavigationController{

    @FXML
    private TextField nameField;

    @FXML
    private TextField ageField;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    @FXML
    public void initialize() {
        // Save button functionality
        saveButton.setOnAction(event -> savePlayer());

        // Cancel button functionality
        cancelButton.setOnAction(event -> cancelCreation());
    }

    private void savePlayer() {
        // Get the entered name and age
        String playerName = nameField.getText();
        String playerAgeText = ageField.getText();

        // Validate inputs (name and age must be filled and age must be valid)
        if (playerName.isEmpty()) {
            System.out.println("Name is required!");
            return;
        }

        int playerAge;
        try {
            playerAge = Integer.parseInt(playerAgeText);
            if (playerAge <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid age!");
            return;
        }

        // Use PlayerDAO to insert the player into the database
        PlayerDAO playerDAO = new PlayerDAO();
        try {
            playerDAO.addPlayer(playerName, playerAge); // Delegate the insert operation to PlayerDAO

            // If successful, redirect to the game
            Stage stage = (Stage) saveButton.getScene().getWindow();
            PrisonEscapeGame game = new PrisonEscapeGame();
            game.start(stage);
        } catch (Exception e) {
            System.out.println("Failed to save player: " + e.getMessage());
        }
    }


    private void cancelCreation() {
            navigateTo((Stage) cancelButton.getScene().getWindow(), "startMenu.fxml");
    }

}