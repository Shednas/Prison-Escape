package com.game.PrisonEscape;

import com.game.PrisonEscape.character.PlayerController;
import com.game.PrisonEscape.gameLogic.MapController;
import com.game.PrisonEscape.gameLogic.TimerController;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.HashSet;
import java.util.Set;

public class PrisonEscapeGame extends Application {

    private static final double WINDOW_WIDTH = 800;
    private static final double WINDOW_HEIGHT = 600;

    private final Set<KeyCode> keysPressed = new HashSet<>();
    private PlayerController playerController; // Controller for the player
    private MapController mapController; // Controller for the map
    private TimerController timerController; // Controller for the timer

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        // Create a Canvas for rendering
        Canvas canvas = new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Initialize the PlayerController with starting position and canvas size
        playerController = new PlayerController(100, 100, WINDOW_WIDTH, WINDOW_HEIGHT);

        // Initialize the MapController to load and render the map
        mapController = new MapController(10, 10, 64); // 10x10 grid, cell size 64x64
        mapController.loadMap(); // Load the map into the game

        // Initialize the TimerController
        timerController = new TimerController();
        timerController.start(); // Start the timer when the game starts

        // Set up the main layout and scene
        StackPane root = new StackPane(canvas);
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

        // Track key presses for player movement
        scene.setOnKeyPressed(e -> keysPressed.add(e.getCode()));
        scene.setOnKeyReleased(e -> keysPressed.remove(e.getCode()));

        // Game loop
        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Update
                update();

                // Render
                render(gc);
            }
        };
        gameLoop.start();

        // Configure the stage
        primaryStage.setTitle("Prison Escape Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void update() {
        // Update player position based on controls
        playerController.move(keysPressed);

        // Update the timer
        timerController.update();
    }

    private void render(GraphicsContext gc) {
        // Clear the canvas
        gc.clearRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

        // Render the map
        mapController.render(gc);

        // Render the player
        playerController.render(gc);

        // Render the timer at the top-right corner
        gc.setFill(Color.BLACK);
        gc.fillText(timerController.getFormattedTime(), WINDOW_WIDTH - 120, 20); // Top-right corner
    }
}