package com.game.PrisonEscape.gameLogic;

import com.game.PrisonEscape.render.TileRenderer;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class MapController {
    private int[][] map;
    private int rows;
    private int cols;
    private double tileSize = 40.0;

    public static final Map<String, Integer> TILE_NAME_MAP = new HashMap<>();
    static {
        TILE_NAME_MAP.put("wall", TileRenderer.WALL);
        TILE_NAME_MAP.put("floor", TileRenderer.FLOOR);
        TILE_NAME_MAP.put("floor2", TileRenderer.FLOOR);
        TILE_NAME_MAP.put("bed", TileRenderer.BED);
        TILE_NAME_MAP.put("drawer", TileRenderer.DRAWER);
        TILE_NAME_MAP.put("toilet", TileRenderer.TOILET);
        TILE_NAME_MAP.put("first_aid", TileRenderer.FIRST_AID);
        TILE_NAME_MAP.put("sink", TileRenderer.SINK);
        TILE_NAME_MAP.put("toilet_wall", TileRenderer.TOILET_WALL);
        TILE_NAME_MAP.put("gate", TileRenderer.GATE);
        TILE_NAME_MAP.put("gate_door", TileRenderer.GATE_DOOR);
        TILE_NAME_MAP.put("broken_tile", TileRenderer.BROKEN_TILE);
        TILE_NAME_MAP.put("next_level", TileRenderer.NEXT_LEVEL);
        TILE_NAME_MAP.put("next_level2", TileRenderer.NEXT_LEVEL2);
        TILE_NAME_MAP.put("next_level3", TileRenderer.NEXT_LEVEL3);
        TILE_NAME_MAP.put("gate_locked", TileRenderer.GATE_LOCKED);
        TILE_NAME_MAP.put("maingate", TileRenderer.MAIN_GATE);
        TILE_NAME_MAP.put("shower", TileRenderer.SHOWER);
        TILE_NAME_MAP.put("shower_wall", TileRenderer.SHOWER_WALL);
        TILE_NAME_MAP.put("shower_head", TileRenderer.SHOWER_HEAD);
        TILE_NAME_MAP.put("shower_floor", TileRenderer.SHOWER_FLOOR);
        TILE_NAME_MAP.put("closet", TileRenderer.CLOSET);
        TILE_NAME_MAP.put("laptop", TileRenderer.LAPTOP);
        TILE_NAME_MAP.put("shower_box", TileRenderer.SHOWER_BOX);
        TILE_NAME_MAP.put("empty", TileRenderer.EMPTY);
        TILE_NAME_MAP.put("pavement", TileRenderer.PAVEMENT);
        TILE_NAME_MAP.put("pond", TileRenderer.POND);
        TILE_NAME_MAP.put("dirt", TileRenderer.DIRT);
        TILE_NAME_MAP.put("grass", TileRenderer.GRASS);
        TILE_NAME_MAP.put("back_gate", TileRenderer.BACK_GATE);
    }

    public MapController(String resourcePath, double tileSize) {
        this.tileSize = tileSize;
        loadMapFromFile(resourcePath);
    }

    private void loadMapFromFile(String resourcePath) {
        List<int[]> mapRowsList = new ArrayList<>();
        int maxCols = 0;
        try (InputStream is = getClass().getResourceAsStream(resourcePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tiles = line.trim().split("\\s+|,");
                int[] rowArr = new int[tiles.length];
                for (int col = 0; col < tiles.length; col++) {
                    String tileName = tiles[col].trim();
                    Integer tileIndex = TILE_NAME_MAP.get(tileName);
                    rowArr[col] = tileIndex != null ? tileIndex : TileRenderer.EMPTY;
                }
                mapRowsList.add(rowArr);
                if (tiles.length > maxCols) maxCols = tiles.length;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        rows = mapRowsList.size();
        cols = maxCols;
        map = new int[rows][cols];
        for (int i = 0; i < mapRowsList.size(); i++) {
            int[] rowArr = mapRowsList.get(i);
            System.arraycopy(rowArr, 0, map[i], 0, rowArr.length);
        }
    }

    public int[][] getMap() {
        return map;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public double getTileSize() {
        return tileSize;
    }
}
