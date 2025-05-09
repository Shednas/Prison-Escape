package com.game.PrisonEscape.menu;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class LeaderboardController extends NavigationController {

    @FXML
    private Button backButton;

    @FXML
    private void initialize() {
        backButton.setOnAction(event -> {
            Stage stage = (Stage) backButton.getScene().getWindow();
            navigateTo(stage, "startMenu.fxml");
        });
    }
}