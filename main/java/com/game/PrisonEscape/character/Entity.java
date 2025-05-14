package com.game.PrisonEscape.character;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import java.util.Set;

public abstract class Entity {
    protected double x, y;
    protected double entitySize;
    protected double speed;
    protected double screenWidth, screenHeight;

    protected int animationFrame = 0;
    protected double animationCounter = 0;
    protected double animationSpeed = 8.0; // Slightly slower for smoother animation
    protected String direction = "down";
    protected boolean isMoving = false;

    protected Image[] upSprites, downSprites, leftSprites, rightSprites;
    protected Image upStationary, downStationary, leftStationary, rightStationary;

    protected int[][] map;
    protected double tileSize;

    public Entity(double startX, double startY, double entitySize, double speed, double screenWidth, double screenHeight) {
        this.x = startX;
        this.y = startY;
        this.entitySize = entitySize;
        this.speed = speed;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        loadSprites();
    }

    protected abstract void loadSprites();

    public void move(double dx, double dy) {
        isMoving = dx != 0 || dy != 0;
        x = x + dx * speed;
        y = y + dy * speed;

        if (isMoving) {
            animationCounter += Math.abs(dx) + Math.abs(dy); // Advance animation based on movement amount
            if (animationCounter >= animationSpeed) {
                animationFrame = (animationFrame + 1) % 2;
                animationCounter = 0;
            }
        } else {
            animationFrame = 0;
            animationCounter = 0;
        }
    }

    public abstract void tick(long now, Set<KeyCode> keysPressed);

    public void render(GraphicsContext gc) {
        gc.setImageSmoothing(false);
        Image sprite;
        switch (direction) {
            case "up":
                sprite = isMoving ? upSprites[animationFrame] : upStationary;
                break;
            case "down":
                sprite = isMoving ? downSprites[animationFrame] : downStationary;
                break;
            case "left":
                sprite = isMoving ? leftSprites[animationFrame] : leftStationary;
                break;
            case "right":
                sprite = isMoving ? rightSprites[animationFrame] : rightStationary;
                break;
            default:
                sprite = downStationary;
        }
        if (sprite != null) {
            gc.drawImage(sprite, Math.round(x), Math.round(y), entitySize, entitySize);
        }
    }

    public void updateScreenSize(double screenWidth, double screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public double getEntitySize() {
        return entitySize;
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public void setPosition(double x, double y) { this.x = x; this.y = y; }

    public void setMap(int[][] map) {
        this.map = map;
    }

    public int[][] getMap() {
        return map;
    }

    public void setTileSize(double tileSize) {
        this.tileSize = tileSize;
    }

    public double getTileSize() {
        return tileSize;
    }
}
