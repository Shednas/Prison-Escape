package com.game.PrisonEscape.render;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class ObjectiveRenderer {
    public void render(GraphicsContext gc, double x, double y, double width, double height, String text) {
        DropShadow ds = new DropShadow();
        ds.setOffsetY(4.0);
        ds.setColor(Color.rgb(30, 60, 30, 0.35));
        gc.applyEffect(ds);

        LinearGradient gradient = new LinearGradient(
                0, y, 0, y + height, false, CycleMethod.NO_CYCLE,
                new Stop(0, Color.rgb(30, 40, 30, 0.92)),
                new Stop(1, Color.rgb(40, 60, 40, 0.82))
        );
        gc.setFill(gradient);
        gc.fillRoundRect(x, y, width, height, 22, 22);

        gc.setStroke(Color.LIMEGREEN);
        gc.setLineWidth(3);
        gc.strokeRoundRect(x, y, width, height, 22, 22);

        String[] lines = text.split("\n");
        double lineHeight = 28;
        double startY = y + (height - lineHeight * lines.length) / 2 + lineHeight - 8;

        gc.setTextAlign(TextAlignment.CENTER);

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            if (line.trim().startsWith("Objective:")) {
                gc.setFont(Font.font("Consolas", FontWeight.BOLD, 24));
                gc.setFill(Color.GOLD);
            } else if (line.trim().startsWith("Current Objective:")) {
                gc.setFont(Font.font("Consolas", FontWeight.BOLD, 22));
                gc.setFill(Color.LIGHTSKYBLUE);
            } else {
                gc.setFont(Font.font("Consolas", 20));
                gc.setFill(Color.WHITE);
            }
            gc.fillText(line, x + width / 2, startY + i * lineHeight);
        }

        gc.applyEffect(null);
    }
}
