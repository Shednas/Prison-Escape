package com.game.PrisonEscape.menu;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class SettingController extends NavigationController {

    @FXML
    private Button toggleSoundButton;

    @FXML
    private Button backButton;

    // Variable to track the sound state
    private boolean isSoundOn = true;

    @FXML
    private void initialize() {
        // Initialize sound button text
        updateSoundButtonText();

        // Add event for sound toggle button
        toggleSoundButton.setOnAction(event -> {
            isSoundOn = !isSoundOn; // Toggle sound state
            System.out.println(isSoundOn ? "Sound enabled." : "Sound disabled."); // Debugging message
            updateSoundButtonText();
        });

        // Add event for back button
        backButton.setOnAction(event -> {
            // Navigate back to "startMenu.fxml"
            System.out.println("Back to Start Menu (Navigation triggered).");
            navigateToStartMenu();
        });
    }

    // Updates the sound button text to "ON" or "OFF" based on the current state
    private void updateSoundButtonText() {
        toggleSoundButton.setText(isSoundOn ? "ON" : "OFF");
    }

    // Navigation logic (replace with actual implementation)
    private void navigateToStartMenu() {
        navigateTo((Stage) backButton.getScene().getWindow(), "startMenu.fxml");
    }
}