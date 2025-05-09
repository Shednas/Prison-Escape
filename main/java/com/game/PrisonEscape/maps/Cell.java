package com.game.PrisonEscape.maps;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

public class Cell {

    @FXML
    private AnchorPane root;

    @FXML
    private Rectangle cellDoor;

    @FXML
    private Label roomLabel;

    @FXML
    public void initialize() {
        roomLabel.setText("Welcome to your Cell...");
    }

    @FXML
    private void handleDoorClick(MouseEvent event) {
        System.out.println("You clicked the door...");
        // Trigger door logic, sound, or attempt to escape here
    }

        private String cellType;

        public Cell(String cellType) {
            this.cellType = cellType;
        }

        public String getCellType() {
            return cellType;
        }

        public void setCellType(String cellType) {
            this.cellType = cellType;
        }
}
