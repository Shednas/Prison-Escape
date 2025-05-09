package com.game.PrisonEscape.menu;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class NavigationController {

    protected void navigateTo(Stage stage, String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/game/PrisonEscape/" + fxmlFile));
            Scene newScene = new Scene(loader.load());
            double currentWidth = stage.getWidth();
            double currentHeight = stage.getHeight();
            stage.setScene(newScene);
            stage.setWidth(currentWidth);
            stage.setHeight(currentHeight);
            stage.centerOnScreen();
        } catch (NullPointerException e) {
            System.err.println("File not found: " + fxmlFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}