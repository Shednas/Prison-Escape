package com.game.PrisonEscape.gameLogic;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PrisonEscapeMiniGameController {

    @FXML private Text gameVisualText;
    @FXML private Text ramenPointsText;
    @FXML private Text roundIndicatorText;
    @FXML private TextField betInputField;
    @FXML private Button placeBetButton;
    @FXML private Button startCardGameButton;
    @FXML private Button exitGameButton;
    @FXML private Button resetGameButton;
    @FXML private Button chooseRedButton, chooseBlackButton;
    @FXML private Button chooseHigherButton, chooseLowerButton;
    @FXML private Button chooseInBetweenButton, chooseOutOfBetweenButton;
    @FXML private Text currentWinningsText;
    @FXML private ProgressBar gameProgressBar;

    private Player player = new Player(10);
    private Deck deck;
    private Card previousCard;
    private Card previousCardRound2;
    private int betAmount;
    private boolean gameInProgress;
    private int round;
    private boolean betPlaced;
    private int currentWinnings = 0;
    private int progress = 0; // Track progress for progress bar

    @FXML
    public void initialize() {
        resetGame();
        ramenPointsText.setText("Ramen Points: " + player.getRamenPoints());
        updateCurrentWinningsText();
        updateRoundIndicator();
        updateGameMessage("Welcome to the Card Game! Click Start to begin.");
        toggleGameButtons();
    }

    @FXML
    public void startCardGame() {
        if (!gameInProgress) {
            resetGame();
            deck = new Deck();
            deck.shuffleDeck();
            gameInProgress = true;
            round = 1;
            betPlaced = false;
            currentWinnings = 0;
            progress = 0;
            updateCurrentWinningsText();
            updateRoundIndicator();
            updateGameMessage("Round 1: Place your bet");
            startCardGameButton.setText("Next Round");
            startCardGameButton.setDisable(true);
            updateProgressBar();
            toggleGameButtons();
        }
    }

    @FXML
    public void placeBet() {
        if (!gameInProgress || round != 1) {
            updateGameMessage("You can only place a bet during Round 1!");
            return;
        }
        try {
            int bet = Integer.parseInt(betInputField.getText());
            if (bet <= 0) {
                updateGameMessage("Bet must be positive.");
                return;
            }
            if (!player.hasEnoughPoints(bet)) {
                updateGameMessage("You do not have enough points to place this bet.");
                return;
            }
            betAmount = bet;
            player.updatePoints(-betAmount);
            currentWinnings = betAmount;
            ramenPointsText.setText("Ramen Points: " + player.getRamenPoints());
            updateCurrentWinningsText();
            updateGameMessage("Bet placed: " + betAmount + " points! Now pick Red or Black.");
            betInputField.clear();
            betPlaced = true;
            betInputField.setDisable(true);
            placeBetButton.setDisable(true);
            toggleGameButtons();
        } catch (NumberFormatException e) {
            updateGameMessage("Please enter a valid number for your bet.");
        }
    }

    @FXML
    public void chooseRedCard() {
        if (round == 1 && gameInProgress && betPlaced) handleColorChoice("Red");
    }

    @FXML
    public void chooseBlackCard() {
        if (round == 1 && gameInProgress && betPlaced) handleColorChoice("Black");
    }

    private void handleColorChoice(String chosenColor) {
        Card drawnCard = deck.drawCard();
        String actualColor = drawnCard.getColor();

        if (chosenColor.equals(actualColor)) {
            currentWinnings *= 2;
            previousCard = drawnCard;
            updateCurrentWinningsText();
            updateGameMessage("Correct! The card is " + drawnCard + ". Round 2: Higher or Lower?");
            round = 2;
            progress = 1;
            updateRoundIndicator();
            updateProgressBar();
            toggleGameButtons();
        } else {
            updateGameMessage("You lost, place your bet. The card was " + drawnCard + " (" + actualColor + ").");
            betAmount = 0;
            currentWinnings = 0;
            updateCurrentWinningsText();
            resetGameForNewBet();
        }
    }

    @FXML
    public void chooseHigherCard() {
        if (round == 2 && gameInProgress) handleHigherLowerChoice("Higher");
    }

    @FXML
    public void chooseLowerCard() {
        if (round == 2 && gameInProgress) handleHigherLowerChoice("Lower");
    }

    private void handleHigherLowerChoice(String choice) {
        Card drawnCard = deck.drawCard();
        boolean isHigher = drawnCard.getValue() > previousCard.getValue();

        if ((isHigher && choice.equals("Higher")) || (!isHigher && choice.equals("Lower"))) {
            currentWinnings *= 2;
            previousCardRound2 = drawnCard;
            updateCurrentWinningsText();
            updateGameMessage("Correct! The card is " + drawnCard + ". Round 3: In Between or Outside?");
            round = 3;
            progress = 2;
            updateRoundIndicator();
            updateProgressBar();
            toggleGameButtons();
        } else {
            String reason = "The card was " + drawnCard + ". It was " +
                    (isHigher ? "Higher" : "Lower") + " than " + previousCard + ".";
            updateGameMessage("You lost, place your bet. " + reason);
            betAmount = 0;
            currentWinnings = 0;
            updateCurrentWinningsText();
            resetGameForNewBet();
        }
    }

    @FXML
    public void chooseInBetween() {
        if (round == 3 && gameInProgress) handleBetweenChoice(true);
    }

    @FXML
    public void chooseOutOfBetween() {
        if (round == 3 && gameInProgress) handleBetweenChoice(false);
    }

    private void handleBetweenChoice(boolean inBetween) {
        Card newCard = deck.drawCard();
        int low = Math.min(previousCard.getValue(), previousCardRound2.getValue());
        int high = Math.max(previousCard.getValue(), previousCardRound2.getValue());
        int newVal = newCard.getValue();

        boolean isInBetween = newVal > low && newVal < high;

        if (inBetween == isInBetween) {
            currentWinnings *= 2;
            player.updatePoints(currentWinnings);
            ramenPointsText.setText("Ramen Points: " + player.getRamenPoints());
            updateCurrentWinningsText();
            updateGameMessage("You have won " + currentWinnings + " points. Congratulations! Play again?");
            progress = 3;
            updateProgressBar();
            Platform.runLater(() -> {
                new Thread(() -> {
                    try { Thread.sleep(1200); } catch (InterruptedException ignored) {}
                    Platform.runLater(this::resetGame);
                }).start();
            });
        } else {
            String range = "between " + previousCard + " and " + previousCardRound2;
            String reason = "The card was " + newCard + ", which is " +
                    (isInBetween ? "in between" : "outside") + " " + range + ".";
            updateGameMessage("You lost, place your bet. " + reason);
            betAmount = 0;
            currentWinnings = 0;
            updateCurrentWinningsText();
            resetGameForNewBet();
        }
    }

    private void resetGame() {
        gameInProgress = false;
        round = 0;
        betAmount = 0;
        betPlaced = false;
        currentWinnings = 0;
        progress = 0;
        ramenPointsText.setText("Ramen Points: " + player.getRamenPoints());
        updateCurrentWinningsText();
        updateGameMessage("Game reset. Play Again?");
        updateRoundIndicator();
        startCardGameButton.setText("▶ START");
        startCardGameButton.setDisable(false);
        betInputField.setDisable(false);
        placeBetButton.setDisable(false);
        updateProgressBar();
        toggleGameButtons();
    }

    private void resetGameForNewBet() {
        gameInProgress = false;
        round = 1;
        betAmount = 0;
        betPlaced = false;
        currentWinnings = 0;
        ramenPointsText.setText("Ramen Points: " + player.getRamenPoints());
        updateCurrentWinningsText();
        updateRoundIndicator();
        startCardGameButton.setText("▶ Start");
        startCardGameButton.setDisable(false);
        betInputField.setDisable(false);
        placeBetButton.setDisable(false);
        toggleGameButtons();
        // Do not reset progress bar here
    }

    @FXML
    private void resetToBet() {
        // Reset to round 1, require new bet, keep ramenPoints, and reset progress bar
        gameInProgress = false;
        round = 1;
        betAmount = 0;
        betPlaced = false;
        currentWinnings = 0;
        progress = 0; // Reset progress bar
        ramenPointsText.setText("Ramen Points: " + player.getRamenPoints());
        updateCurrentWinningsText();
        updateRoundIndicator();
        updateGameMessage("Game reset. Place your bet to start again!");
        startCardGameButton.setText("▶ START");
        startCardGameButton.setDisable(false);
        betInputField.setDisable(false);
        placeBetButton.setDisable(false);
        updateProgressBar();
        toggleGameButtons();
    }

    private void toggleGameButtons() {
        placeBetButton.setDisable(!(gameInProgress && round == 1 && !betPlaced));
        chooseRedButton.setDisable(!(gameInProgress && round == 1 && betPlaced));
        chooseBlackButton.setDisable(!(gameInProgress && round == 1 && betPlaced));
        chooseHigherButton.setDisable(!(gameInProgress && round == 2));
        chooseLowerButton.setDisable(!(gameInProgress && round == 2));
        chooseInBetweenButton.setDisable(!(gameInProgress && round == 3));
        chooseOutOfBetweenButton.setDisable(!(gameInProgress && round == 3));
        betInputField.setDisable(!(gameInProgress && round == 1 && !betPlaced));
    }

    private void updateGameMessage(String message) {
        gameVisualText.setText(message);
        gameVisualText.getStyleClass().removeAll("label-win", "label-lose");
        if (message.contains("Congratulations") || message.contains("You have won")) {
            gameVisualText.getStyleClass().add("label-win");
        } else if (message.contains("You lost")) {
            gameVisualText.getStyleClass().add("label-lose");
        }
    }

    private void updateProgressBar() {
        // Only increase progress when a round is won, and only reset on start game
        double barProgress = Math.max(0.0, Math.min(progress / 3.0, 1.0));
        gameProgressBar.setProgress(barProgress);
    }

    private void updateRoundIndicator() {
        roundIndicatorText.setText(round == 0 ? "Round: N/A" : "🔥 Round " + round);
        updateProgressBar();
    }

    private void updateCurrentWinningsText() {
        if (currentWinningsText != null) {
            currentWinningsText.setText("Current Winnings: " + currentWinnings);
        }
    }

    // ...existing Card, Deck, Player classes and exitGame()...
    static class Card {
        private final String suit;
        private final int value;

        public Card(String suit, int value) {
            this.suit = suit;
            this.value = value;
        }

        public String getSuit() {
            return suit;
        }

        public int getValue() {
            return value;
        }

        public String getColor() {
            return suit.equals("Hearts") || suit.equals("Diamonds") ? "Red" : "Black";
        }

        @Override
        public String toString() {
            String[] names = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
            return names[value - 1] + " of " + suit;
        }
    }

    static class Deck {
        private final List<Card> cards = new ArrayList<>();

        public Deck() {
            String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
            for (String suit : suits) {
                for (int value = 1; value <= 13; value++) {
                    cards.add(new Card(suit, value));
                }
            }
        }

        public void shuffleDeck() {
            Collections.shuffle(cards);
        }

        public Card drawCard() {
            if (cards.isEmpty()) throw new IllegalStateException("Deck is empty.");
            return cards.remove(0);
        }
    }

    static class Player {
        private int ramenPoints;

        public Player(int ramenPoints) {
            this.ramenPoints = ramenPoints;
        }

        public int getRamenPoints() {
            return ramenPoints;
        }

        public void updatePoints(int delta) {
            ramenPoints += delta;
        }

        public boolean hasEnoughPoints(int bet) {
            return ramenPoints >= bet;
        }
    }

    @FXML
    private void exitGame() {
        Platform.exit();
    }
}
