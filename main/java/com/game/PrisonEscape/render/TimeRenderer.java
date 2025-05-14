package com.game.PrisonEscape.render;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class TimeRenderer {
    public void render(GraphicsContext gc, String timeText) {
        Font font = Font.font("Verdana", 25);
        gc.setFont(font);

        Text text = new Text(timeText);
        text.setFont(font);
        double textWidth = text.getLayoutBounds().getWidth();
        double textHeight = text.getLayoutBounds().getHeight();

        double boxWidth = textWidth + 40;
        double boxHeight = textHeight + 24;
        double x = gc.getCanvas().getWidth() - boxWidth - 30;
        double y = 30;

        gc.setFill(Color.rgb(34, 139, 34, 0.85));
        gc.fillRoundRect(x, y, boxWidth, boxHeight, 20, 20);

        gc.setStroke(Color.LIME);
        gc.setLineWidth(2);
        gc.strokeRoundRect(x, y, boxWidth, boxHeight, 20, 20);

        gc.setFill(Color.WHITE);
        gc.setFont(font);
        gc.fillText(timeText, x + boxWidth / 2, y + boxHeight / 2 + textHeight / 4);
    }
}
