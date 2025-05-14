package com.game.PrisonEscape.render;

import com.game.PrisonEscape.gameLogic.BackStoryOverlay;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class BackStoryOverlayRenderer {
    public void render(GraphicsContext gc, BackStoryOverlay overlay, double width, double height, double skipBtnX, double skipBtnY, double skipBtnW, double skipBtnH) {
        double boxWidth = width * 0.7;
        double boxHeight = 200;
        double boxX = (width - boxWidth) / 2;
        double boxY = height / 2 - boxHeight / 2;

        gc.setFill(Color.rgb(30, 30, 30, 0.85));
        gc.fillRoundRect(boxX, boxY, boxWidth, boxHeight, 30, 30);

        gc.setStroke(Color.LIMEGREEN);
        gc.setLineWidth(3);
        gc.strokeRoundRect(boxX, boxY, boxWidth, boxHeight, 30, 30);

        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Verdana", 28));
        String[] lines = overlay.getCurrentLines();
        double y = boxY + 60;
        for (String line : lines) {
            gc.fillText(line, boxX + 40, y);
            y += 35;
        }
        gc.setFill(Color.LIGHTGRAY);
        gc.setFont(Font.font("Verdana", 18));
        gc.fillText("Press Enter for next", boxX + 40, boxY + boxHeight - 30);

        gc.setFill(Color.rgb(50, 205, 50));
        gc.fillRoundRect(skipBtnX, skipBtnY, skipBtnW, skipBtnH, 15, 15);
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(2);
        gc.strokeRoundRect(skipBtnX, skipBtnY, skipBtnW, skipBtnH, 15, 15);
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Verdana", 20));
        gc.fillText("Skip", skipBtnX + 25, skipBtnY + 27);
    }
}
