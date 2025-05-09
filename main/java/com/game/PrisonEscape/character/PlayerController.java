package com.game.PrisonEscape.character;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.Set;

public class PlayerController {

    private double x;
    private double y;
    private final double playerSize = 30;
    private final double speed = 3;

    private double screenWidth;
    private double screenHeight;

    public PlayerController(double startX, double startY, double screenWidth, double screenHeight) {
        this.x = startX;
        this.y = startY;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public void move(Set<KeyCode> keysPressed) {
        // Update player position based on keys pressed
        if (keysPressed.contains(KeyCode.W)) y -= speed;
        if (keysPressed.contains(KeyCode.S)) y += speed;
        if (keysPressed.contains(KeyCode.A)) x -= speed;
        if (keysPressed.contains(KeyCode.D)) x += speed;

        // Keep player within bounds of the screen
        x = Math.max(0, Math.min(screenWidth - playerSize, x));
        y = Math.max(0, Math.min(screenHeight - playerSize, y));
    }

    public void render(GraphicsContext gc) {
        // Draw the player
        gc.setFill(Color.LIGHTBLUE);
        gc.fillRect(x, y, playerSize, playerSize);
    }

    public void updateScreenSize(double screenWidth, double screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }
}