package com.game.PrisonEscape.gameLogic;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class TimerController {
    private long startTime;
    private long elapsedTime;
    private boolean started = false;

    public void start() {
        startTime = System.nanoTime();
        started = true;
    }

    public void update(long now) {
        if (started) {
            elapsedTime = (now - startTime) / 1_000_000;
        }
    }

    public String getFormattedTime() {
        if (!started) {
            return "00:00:000";
        }
        long minutes = (elapsedTime / 1000) / 60;
        long seconds = (elapsedTime / 1000) % 60;
        long milliseconds = elapsedTime % 1000;
        return String.format("%02d:%02d:%03d", minutes, seconds, milliseconds);
    }

    public void render(GraphicsContext gc) {
        String timeText = "⏱ " + getFormattedTime();
        Font font = Font.font("Verdana", 25);
        gc.setFont(font);
        Text text = new Text(timeText);
        text.setFont(font);
        double textWidth = text.getLayoutBounds().getWidth();
        double x = gc.getCanvas().getWidth() - textWidth - 30;
        double y = 65;
        gc.setFill(Color.rgb(34, 139, 34, 0.85));
        gc.fillRoundRect(x - 15, y - 30, textWidth + 30, 40, 20, 20);
        gc.setStroke(Color.LIME);
        gc.setLineWidth(2);
        gc.strokeRoundRect(x - 15, y - 30, textWidth + 30, 40, 20, 20);
        gc.setFill(Color.WHITE);
        gc.fillText(timeText, x, y);
    }
}
