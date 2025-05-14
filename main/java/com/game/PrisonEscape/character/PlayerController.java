package com.game.PrisonEscape.character;
import com.game.PrisonEscape.gameLogic.physics.Collision;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

import java.util.Set;

public class PlayerController extends Entity {

    public PlayerController(double startX, double startY, double screenWidth, double screenHeight) {
        super(startX, startY, 40, 10, screenWidth, screenHeight); // Increased speed from 4 to 10 for faster movement
    }

    @Override
    protected void loadSprites() {
        // Load player sprites using getResourceAsStream
        upSprites = new Image[]{
                new Image(getClass().getResourceAsStream("/sprites/player/up1.png")),
                new Image(getClass().getResourceAsStream("/sprites/player/up2.png"))
        };
        downSprites = new Image[]{
                new Image(getClass().getResourceAsStream("/sprites/player/down1.png")),
                new Image(getClass().getResourceAsStream("/sprites/player/down2.png"))
        };
        leftSprites = new Image[]{
                new Image(getClass().getResourceAsStream("/sprites/player/left1.png")),
                new Image(getClass().getResourceAsStream("/sprites/player/left2.png"))
        };
        rightSprites = new Image[]{
                new Image(getClass().getResourceAsStream("/sprites/player/right1.png")),
                new Image(getClass().getResourceAsStream("/sprites/player/right2.png"))
        };

        // Load stationary (idle) frames
        upStationary = new Image(getClass().getResourceAsStream("/sprites/player/ups.png"));
        downStationary = new Image(getClass().getResourceAsStream("/sprites/player/downs.png"));
        leftStationary = new Image(getClass().getResourceAsStream("/sprites/player/lefts.png"));
        rightStationary = new Image(getClass().getResourceAsStream("/sprites/player/rights.png"));
    }

    @Override
    public void tick(long now, Set<KeyCode> keysPressed) {
        double dx = 0, dy = 0;

        boolean up = keysPressed.contains(KeyCode.W);
        boolean down = keysPressed.contains(KeyCode.S);
        boolean left = keysPressed.contains(KeyCode.A);
        boolean right = keysPressed.contains(KeyCode.D);

        if (up) dy -= 1;
        if (down) dy += 1;
        if (left) dx -= 1;
        if (right) dx += 1;

        // Normalize diagonal movement
        if (dx != 0 && dy != 0) {
            double norm = Math.sqrt(dx * dx + dy * dy);
            dx /= norm;
            dy /= norm;
        }

        // Set direction for animation (prioritize last pressed)
        if (up) direction = "up";
        else if (down) direction = "down";
        else if (left) direction = "left";
        else if (right) direction = "right";

        // Only move if not colliding (check all corners)
        if ((dx != 0 || dy != 0) && map != null) {
            double newX = x + dx * speed;
            double newY = y + dy * speed;
            double size = entitySize;

            boolean canMove =
                Collision.canMoveTo(map, (int)(newY / tileSize), (int)(newX / tileSize)) &&
                Collision.canMoveTo(map, (int)(newY / tileSize), (int)((newX + size - 1) / tileSize)) &&
                Collision.canMoveTo(map, (int)((newY + size - 1) / tileSize), (int)(newX / tileSize)) &&
                Collision.canMoveTo(map, (int)((newY + size - 1) / tileSize), (int)((newX + size - 1) / tileSize));

            if (canMove) {
                move(dx, dy); // Use move() to update position and animation
            } else {
                isMoving = false;
                animationFrame = 0;
                animationCounter = 0;
            }
        } else {
            isMoving = false;
            animationFrame = 0;
            animationCounter = 0;
        }
    }

    public void render(GraphicsContext gc) {
        // Render the entity using its current sprite (inherited from Entity)
        super.render(gc);
    }
}
