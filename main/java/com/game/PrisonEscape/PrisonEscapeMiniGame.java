package com.game.PrisonEscape;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PrisonEscapeMiniGame extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("PrisonEscapeMiniGame.fxml"));
        primaryStage.setTitle("Prison Escape Mini-Game");
        primaryStage.setScene(new Scene(root, 700, 500));
        primaryStage.show();
    }
}
