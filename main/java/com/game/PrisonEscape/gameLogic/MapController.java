package com.game.PrisonEscape.gameLogic;

import com.game.PrisonEscape.maps.Cell;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class MapController {

    private final int rows;
    private final int columns;
    private final double cellSize;
    private Cell[][] map;

    public MapController(int rows, int columns, double cellSize) {
        this.rows = rows;
        this.columns = columns;
        this.cellSize = cellSize;
    }

    /** Loads a simple prison cell layout into the map. */
    public void loadMap() {
        map = new Cell[rows][columns];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                // Borders are walls
                if (row == 0 || row == rows - 1 || col == 0 || col == columns - 1) {
                    map[row][col] = new Cell("wall");
                }
                // Interior layout (e.g., wall partitions)
                else if ((row == 2 && col >= 2 && col <= columns - 3) || (col == 2 && row >= 2 && row <= rows - 3)) {
                    map[row][col] = new Cell("wall");
                }
                // Example door cell
                else if (row == rows - 2 && col == columns / 2) {
                    map[row][col] = new Cell("door");
                }
                // Floor (interior of cell)
                else {
                    map[row][col] = new Cell("floor");
                }
            }
        }
    }

    /** Renders the map on the canvas. */
    public void render(GraphicsContext gc) {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                double x = col * cellSize;
                double y = row * cellSize;

                Cell cell = map[row][col];

                if (cell == null || cell.getCellType() == null) {
                    gc.setFill(Color.WHITE);
                } else {
                    switch (cell.getCellType()) {
                        case "wall":
                            gc.setFill(Color.DARKGRAY);
                            break;
                        case "door":
                            gc.setFill(Color.SADDLEBROWN);
                            break;
                        case "floor":
                            gc.setFill(Color.LIGHTGRAY);
                            break;
                        default:
                            gc.setFill(Color.WHITE);
                            break;
                    }
                }

                gc.fillRect(x, y, cellSize, cellSize);
                gc.setStroke(Color.BLACK);
                gc.strokeRect(x, y, cellSize, cellSize);
            }
        }
    }
}
