package com.game.PrisonEscape.gameLogic;

import java.util.HashMap;
import java.util.Map;

public class ObjectiveController {
    private final String overallObjective =
        "Objective:\n" +
        "1. Escape the prison\n" +
        "2. Find your phone";
    private final Map<String, String> objectives = new HashMap<>();
    private String currentObjective = "";

    public ObjectiveController() {
        objectives.put("hallway1_map.txt", "Find the exit!");
        objectives.put("cell_map.txt", "Find the Lock pick!");
    }

    public void setObjectiveForMap(String mapFileName) {
        this.currentObjective = objectives.getOrDefault(mapFileName, "Explore the area!");
    }

    public String getOverallObjective() {
        return overallObjective;
    }

    public String getCurrentObjective() {
        return "Current Objective: " + currentObjective;
    }
}
