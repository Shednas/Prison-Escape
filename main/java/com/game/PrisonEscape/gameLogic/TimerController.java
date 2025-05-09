package com.game.PrisonEscape.gameLogic;

public class TimerController {
    private long startTime;      // Stores the starting time in nanoseconds
    private long elapsedTime;    // Stores the total elapsed time in milliseconds

    // Start the timer
    public void start() {
        startTime = System.nanoTime(); // Record the start time
    }

    // Update the elapsed time
    public void update() {
        elapsedTime = (System.nanoTime() - startTime) / 1_000_000; // Convert nanoseconds to milliseconds
    }

    // Get the elapsed time in "MM:SS:MS" format
    public String getFormattedTime() {
        long minutes = (elapsedTime / 1000) / 60;             // Calculate minutes
        long seconds = (elapsedTime / 1000) % 60;             // Calculate seconds
        long milliseconds = elapsedTime % 1000;              // Calculate milliseconds
        return String.format("%02d:%02d:%03d", minutes, seconds, milliseconds);
    }
}