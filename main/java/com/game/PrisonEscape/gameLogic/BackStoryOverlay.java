package com.game.PrisonEscape.gameLogic;

import javafx.scene.input.KeyCode;

public class BackStoryOverlay {
    private final String[] lines;
    private int lineIndex = 0;
    private final Runnable onContinue;

    public BackStoryOverlay(String story, Runnable onContinue) {
        this.lines = story.split("\n");
        this.onContinue = onContinue;
    }

    public void handleKeyEvent(javafx.scene.input.KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            lineIndex++;
            if (lineIndex >= lines.length) {
                onContinue.run();
            }
        }
    }

    public boolean isActive() {
        return lineIndex < lines.length;
    }

    public String[] getCurrentLines() {
        if (lineIndex < lines.length) {
            return new String[]{lines[lineIndex]};
        } else if (lines.length > 0) {
            return new String[]{lines[lines.length - 1]};
        } else {
            return new String[]{""};
        }
    }
}
