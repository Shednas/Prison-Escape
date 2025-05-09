package com.game.PrisonEscape.menu;

import com.game.PrisonEscape.PrisonEscapeGame;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class StartMenuController extends NavigationController {

    @FXML
    private Button startButton;

    @FXML
    private Button settingsButton;

    @FXML
    private Button leaderboardButton;

    @FXML
    private Button exitButton;

    @FXML
    private void handleStartButton() {
        Stage stage = (Stage) startButton.getScene().getWindow();
        navigateTo(stage, "playerCreator.fxml");
    }

    @FXML
    private void handleSettingsButton() {
        // Navigate to "setting.fxml"
        Stage stage = (Stage) settingsButton.getScene().getWindow();
        navigateTo(stage, "setting.fxml");
    }

    @FXML
    private void handleLeaderboardButton() {
        // Navigate to "leaderboard.fxml"
        Stage stage = (Stage) leaderboardButton.getScene().getWindow();
        navigateTo(stage, "leaderboard.fxml");
    }

    @FXML
    private void handleExitButton() {
        // Close the application
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
}