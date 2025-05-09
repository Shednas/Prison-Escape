package com.game.PrisonEscape.menu;

import com.game.PrisonEscape.PrisonEscapeGame;
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

        try {
            // Get the current Stage
            Stage stage = (Stage) saveButton.getScene().getWindow();
            // Start the PrisonEscapeGame using the existing stage
            PrisonEscapeGame game = new PrisonEscapeGame();
            game.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cancelCreation() {
            navigateTo((Stage) cancelButton.getScene().getWindow(), "startMenu.fxml");
    }

}