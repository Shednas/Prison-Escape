package com.game.PrisonEscape;

import com.game.PrisonEscape.database.DatabaseSetup;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Launcher extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Launcher.class.getResource("startMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setTitle("Prison Escape");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        DatabaseSetup.createTables();
        launch();
    }
}
