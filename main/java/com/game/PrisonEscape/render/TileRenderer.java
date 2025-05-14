package com.game.PrisonEscape.render;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class TileRenderer {
    public static final int EMPTY = 0;
    public static final int WALL = 1;
    public static final int FLOOR = 2;
    public static final int DOOR = 3;
    public static final int BED = 4;
    public static final int DRAWER = 5;
    public static final int TOILET = 6;
    public static final int FIRST_AID = 7;
    public static final int SINK = 8;
    public static final int TOILET_WALL = 9;
    public static final int GATE = 10;
    public static final int GATE_DOOR = 11;
    public static final int BROKEN_TILE = 12;
    public static final int NEXT_LEVEL = 13;
    public static final int NEXT_LEVEL2 = 14;
    public static final int NEXT_LEVEL3 = 15;
    public static final int GATE_LOCKED = 16;
    public static final int SHOWER = 17;
    public static final int MAIN_GATE = 18;
    public static final int SHOWER_WALL = 19;
    public static final int SHOWER_HEAD = 20;
    public static final int SHOWER_FLOOR = 21;
    public static final int CLOSET = 22;
    public static final int LAPTOP = 23;
    public static final int SHOWER_BOX = 24;
    public static final int PAVEMENT = 25;
    public static final int POND = 26;
    public static final int DIRT = 27;
    public static final int GRASS = 28;
    public static final int BACK_GATE = 29;

    public void render(GraphicsContext gc, int[][] map, double offsetX, double offsetY, double tileSize) {
        if (map == null) return;
        int rows = map.length;
        int cols = map[0].length;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                double x = offsetX + col * tileSize;
                double y = offsetY + row * tileSize;
                switch (map[row][col]) {
                    case EMPTY: gc.setFill(Color.TRANSPARENT); break;
                    case WALL: gc.setFill(Color.DIMGRAY); break;
                    case FLOOR: gc.setFill(Color.BEIGE); break;
                    case DOOR: gc.setFill(Color.SADDLEBROWN); break;
                    case BED: gc.setFill(Color.ROYALBLUE); break;
                    case DRAWER: gc.setFill(Color.SIENNA); break;
                    case TOILET: gc.setFill(Color.LIGHTGRAY); break;
                    case FIRST_AID: gc.setFill(Color.FIREBRICK); break;
                    case SINK: gc.setFill(Color.ALICEBLUE); break;
                    case TOILET_WALL: gc.setFill(Color.GRAY); break;
                    case GATE: gc.setFill(Color.DARKSLATEGRAY); break;
                    case GATE_DOOR: gc.setFill(Color.GOLDENROD); break;
                    case BROKEN_TILE: gc.setFill(Color.SANDYBROWN); break;
                    case NEXT_LEVEL: gc.setFill(Color.ORANGE); break;
                    case NEXT_LEVEL2: gc.setFill(Color.ORANGERED); break;
                    case NEXT_LEVEL3: gc.setFill(Color.DARKORANGE); break;
                    case GATE_LOCKED: gc.setFill(Color.DARKRED); break;
                    case SHOWER: gc.setFill(Color.LIGHTCYAN); break;
                    case MAIN_GATE: gc.setFill(Color.BLACK); break;
                    case SHOWER_WALL: gc.setFill(Color.NAVY); break;
                    case SHOWER_HEAD: gc.setFill(Color.LIGHTSKYBLUE); break;
                    case SHOWER_FLOOR: gc.setFill(Color.LIGHTBLUE); break;
                    case CLOSET: gc.setFill(Color.DARKOLIVEGREEN); break;
                    case LAPTOP: gc.setFill(Color.DARKVIOLET); break;
                    case SHOWER_BOX: gc.setFill(Color.ORANGERED); break;
                    case PAVEMENT: gc.setFill(Color.DARKGRAY); break;
                    case POND: gc.setFill(Color.DODGERBLUE); break;
                    case DIRT: gc.setFill(Color.SADDLEBROWN); break;
                    case GRASS: gc.setFill(Color.LIMEGREEN); break;
                    case BACK_GATE: gc.setFill(Color.DARKGREEN); break;
                    default: gc.setFill(Color.LIGHTGRAY); break;
                }
                gc.fillRect(x, y, tileSize, tileSize);
                gc.setStroke(Color.DARKGRAY);
                gc.setLineWidth(1);
                gc.strokeRect(x, y, tileSize, tileSize);
            }
        }
    }
}
