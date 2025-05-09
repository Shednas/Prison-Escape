package com.game.PrisonEscape.menu;

import com.game.PrisonEscape.PrisonEscapeGame;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class EndGameController extends NavigationController {

    @FXML
    private Label messageLabel;

    @FXML
    private Button playAgainButton;

    @FXML
    private Button mainMenuButton;

    @FXML
    private Button exitButton;

    private boolean playerWon;

    public void setPlayerResult(boolean playerWon) {
        this.playerWon = playerWon;
        if (playerWon) {
            messageLabel.setText("You have escaped the prison!");
            messageLabel.getStyleClass().add("label-win");
        } else {
            messageLabel.setText("You have been caught!");
            messageLabel.getStyleClass().add("label-lose");
        }
    }

    @FXML
    private void initialize() {
        playAgainButton.setOnAction(event -> {
            try {
                // Start the game by creating a new instance of PrisonEscapeGame
                PrisonEscapeGame game = new PrisonEscapeGame();
                Stage stage = (Stage) playAgainButton.getScene().getWindow(); // Get current stage
                game.start(stage); // Start the game on the same stage
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        mainMenuButton.setOnAction(event -> {
            Stage stage = (Stage) mainMenuButton.getScene().getWindow();
            navigateTo(stage, "startMenu.fxml"); // Navigate to Start Menu
        });

        exitButton.setOnAction(event -> {
            System.exit(0); // Exit the application
        });
    }
}