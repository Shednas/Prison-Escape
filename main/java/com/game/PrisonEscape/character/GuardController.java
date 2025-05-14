package com.game.PrisonEscape.character;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import java.util.Set;

public class GuardController extends Entity {

    public GuardController(double startX, double startY, double screenWidth, double screenHeight) {
        super(startX, startY, 48, 2.0, screenWidth, screenHeight);
    }

    @Override
    protected void loadSprites() {
        upSprites = new Image[3];
        downSprites = new Image[3];
        leftSprites = new Image[3];
        rightSprites = new Image[3];
        upSprites[0] = new Image(getClass().getResourceAsStream("/sprites/guard/up1.png"));
        upSprites[1] = new Image(getClass().getResourceAsStream("/sprites/guard/up2.png"));
        try {
            upSprites[2] = new Image(getClass().getResourceAsStream("/sprites/guard/up3.png"));
        } catch (Exception e) {
            upSprites[2] = upSprites[0];
        }
        upStationary = new Image(getClass().getResourceAsStream("/sprites/guard/ups.png"));
        downSprites[0] = new Image(getClass().getResourceAsStream("/sprites/guard/down1.png"));
        downSprites[1] = new Image(getClass().getResourceAsStream("/sprites/guard/down2.png"));
        try {
            downSprites[2] = new Image(getClass().getResourceAsStream("/sprites/guard/down3.png"));
        } catch (Exception e) {
            downSprites[2] = downSprites[0];
        }
        downStationary = new Image(getClass().getResourceAsStream("/sprites/guard/down1.png"));
        leftSprites[0] = new Image(getClass().getResourceAsStream("/sprites/guard/left1.png"));
        leftSprites[1] = new Image(getClass().getResourceAsStream("/sprites/guard/left2.png"));
        try {
            leftSprites[2] = new Image(getClass().getResourceAsStream("/sprites/guard/left3.png"));
        } catch (Exception e) {
            leftSprites[2] = leftSprites[0];
        }
        leftStationary = new Image(getClass().getResourceAsStream("/sprites/guard/lefts.png"));
        rightSprites[0] = new Image(getClass().getResourceAsStream("/sprites/guard/right1.png"));
        rightSprites[1] = new Image(getClass().getResourceAsStream("/sprites/guard/right2.png"));
        try {
            rightSprites[2] = new Image(getClass().getResourceAsStream("/sprites/guard/right3.png"));
        } catch (Exception e) {
            rightSprites[2] = rightSprites[0];
        }
        rightStationary = new Image(getClass().getResourceAsStream("/sprites/guard/rights.png"));
    }

    public void moveAutomatically(double dx, double dy) {
        if (dx < 0) direction = "left";
        else if (dx > 0) direction = "right";
        else if (dy < 0) direction = "up";
        else if (dy > 0) direction = "down";
        move(dx, dy);
    }

    public void tick(long now, Set<KeyCode> keysPressed){
    }
}
