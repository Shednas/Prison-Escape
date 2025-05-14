package com.game.PrisonEscape.gameLogic;

import javafx.animation.ScaleTransition;
import javafx.scene.Node;
import javafx.util.Duration;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.geometry.Insets;
import javafx.scene.paint.*;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class Effect {
    /**
     * Plays a zoom-out effect on the given node, then runs the after callback.
     * The screen starts at 1.05x zoom, then zooms out to normal.
     */
    public static void play(Node node, Runnable after) {
        node.setScaleX(1.05);
        node.setScaleY(1.05);

        ScaleTransition zoom = new ScaleTransition(Duration.millis(700), node);
        zoom.setFromX(1.05);
        zoom.setFromY(1.05);
        zoom.setToX(1.0);
        zoom.setToY(1.0);

        zoom.setOnFinished(e -> {
            if (after != null) after.run();
        });
        zoom.play();
    }

    /**
     * Creates a Pane with the 2D prison vibe background (gradient, grid, noise, bars).
     */
    public static Pane createPrisonBackground(double width, double height) {
        Pane prisonBg = new Pane();
        prisonBg.setPrefSize(width, height);
        // Gradient background
        prisonBg.setBackground(new Background(new BackgroundFill(
                new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                        new Stop(0, Color.web("#4a5670")),
                        new Stop(0.5, Color.web("#23272e")),
                        new Stop(1, Color.web("#181b20"))
                ), CornerRadii.EMPTY, Insets.EMPTY)));

        // Subtle "noise" overlay for texture
        Rectangle noise = new Rectangle(width, height);
        noise.setFill(new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.rgb(80, 90, 110, 0.07)),
                new Stop(1, Color.rgb(30, 30, 40, 0.10))
        ));
        prisonBg.getChildren().add(noise);

        // Draw grid lines for a "tiled" effect
        for (int i = 0; i < width; i += 48) {
            Line vLine = new Line(i, 0, i, height);
            vLine.setStroke(Color.rgb(60, 70, 90, 0.18));
            vLine.setStrokeWidth(2);
            prisonBg.getChildren().add(vLine);
        }
        for (int i = 0; i < height; i += 48) {
            Line hLine = new Line(0, i, width, i);
            hLine.setStroke(Color.rgb(60, 70, 90, 0.18));
            hLine.setStrokeWidth(2);
            prisonBg.getChildren().add(hLine);
        }

        // Add some "bars" shadow at the top for prison feel
        for (int i = 0; i < width; i += 120) {
            Rectangle barShadow = new Rectangle(i + 8, 0, 16, 80);
            barShadow.setFill(new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE,
                    new Stop(0, Color.rgb(30, 30, 40, 0.55)),
                    new Stop(1, Color.TRANSPARENT)));
            prisonBg.getChildren().add(barShadow);
        }
        return prisonBg;
    }
}
