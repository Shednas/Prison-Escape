package com.game.PrisonEscape.gameLogic.physics;

import com.game.PrisonEscape.render.TileRenderer;

public class Collision {
    public static boolean isCollidable(int tileType) {
        return tileType == TileRenderer.WALL ||
            tileType == TileRenderer.BED ||
            tileType == TileRenderer.DRAWER ||
            tileType == TileRenderer.TOILET ||
            tileType == TileRenderer.TOILET_WALL ||
            tileType == TileRenderer.GATE ||
            tileType == TileRenderer.GATE_DOOR ||
            tileType == TileRenderer.GATE_LOCKED ||
            tileType == TileRenderer.MAIN_GATE ||
            tileType == TileRenderer.SHOWER_WALL ||
            tileType == TileRenderer.SHOWER_HEAD ||
            tileType == TileRenderer.CLOSET ||
            tileType == TileRenderer.LAPTOP ||
            tileType == TileRenderer.SHOWER_BOX ||
            tileType == TileRenderer.FIRST_AID ||
            tileType == TileRenderer.BACK_GATE;
    }

    // Returns true if the player can move to (row, col)
    public static boolean canMoveTo(int[][] map, int row, int col) {
        if (row < 0 || col < 0 || row >= map.length || col >= map[0].length)
            return false;
        return !isCollidable(map[row][col]);
    }
}