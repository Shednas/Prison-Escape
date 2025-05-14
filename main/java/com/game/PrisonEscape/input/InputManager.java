package com.game.PrisonEscape.input;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import java.util.Set;

public class InputManager {
    private final Set<KeyCode> keysPressed;
    private final Runnable onSkip;
    private final java.util.function.Consumer<javafx.scene.input.KeyEvent> onBackstoryKey;

    public InputManager(Set<KeyCode> keysPressed, Runnable onSkip, java.util.function.Consumer<javafx.scene.input.KeyEvent> onBackstoryKey) {
        this.keysPressed = keysPressed;
        this.onSkip = onSkip;
        this.onBackstoryKey = onBackstoryKey;
    }

    public void attach(Scene scene, java.util.function.Supplier<Boolean> backstoryActiveSupplier,
                       java.util.function.DoubleSupplier skipBtnX, java.util.function.DoubleSupplier skipBtnY,
                       double skipBtnW, double skipBtnH) {
        scene.setOnKeyPressed(event -> {
            if (backstoryActiveSupplier.get()) {
                onBackstoryKey.accept(event);
            } else {
                keysPressed.add(event.getCode());
            }
        });
        scene.setOnKeyReleased(event -> keysPressed.remove(event.getCode()));
        scene.setOnMouseClicked(event -> {
            if (backstoryActiveSupplier.get()) {
                double mx = event.getX(), my = event.getY();
                if (mx >= skipBtnX.getAsDouble() && mx <= skipBtnX.getAsDouble() + skipBtnW &&
                    my >= skipBtnY.getAsDouble() && my <= skipBtnY.getAsDouble() + skipBtnH) {
                    onSkip.run();
                }
            }
        });
    }
}
