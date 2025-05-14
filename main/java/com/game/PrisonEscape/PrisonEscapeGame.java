package com.game.PrisonEscape;

import com.game.PrisonEscape.character.PlayerController;
import com.game.PrisonEscape.gameLogic.BackStoryOverlay;
import com.game.PrisonEscape.gameLogic.MapController;
import com.game.PrisonEscape.gameLogic.ObjectiveController;
import com.game.PrisonEscape.gameLogic.TimerController;
import com.game.PrisonEscape.input.InputManager;
import com.game.PrisonEscape.render.BackStoryOverlayRenderer;
import com.game.PrisonEscape.render.ObjectiveRenderer;
import com.game.PrisonEscape.render.TimeRenderer;
import com.game.PrisonEscape.render.TileRenderer;
import com.game.PrisonEscape.gameLogic.Effect;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class PrisonEscapeGame extends Application {

    private double windowWidth;
    private double windowHeight;

    private final Set<KeyCode> keysPressed = new HashSet<>();
    private PlayerController playerController;
    private TimerController timerController;
    private boolean backstoryActive = true;
    private BackStoryOverlay backStoryOverlay;

    private double skipBtnX, skipBtnY, skipBtnW = 100, skipBtnH = 40;

    private final BackStoryOverlayRenderer overlayRenderer = new BackStoryOverlayRenderer();
    private final TimeRenderer timerRenderer = new TimeRenderer();
    private final ObjectiveRenderer objectiveRenderer = new ObjectiveRenderer();
    private final ObjectiveController objectiveController = new ObjectiveController();

    private double mapOffsetX = 0;
    private double mapOffsetY = 0;

    private MapController mapController;
    private TileRenderer tileRenderer;
    private double tileSize = 40.0;

    private String currentMapFile = "hallway2_map.txt";

    private double overallBoxWidth = 0;
    private double overallBoxHeight = 0;
    private String lastOverallObjective = "";

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        windowWidth = screenBounds.getWidth();
        windowHeight = screenBounds.getHeight();

        mapController = new MapController("/maps/" + currentMapFile, tileSize);
        tileRenderer = new TileRenderer();

        int mapRows = mapController.getRows();
        int mapCols = mapController.getCols();

        double mapWidth = mapCols * tileSize;
        double mapHeight = mapRows * tileSize;
        mapOffsetX = (windowWidth - mapWidth) / 2;
        mapOffsetY = (windowHeight - mapHeight) / 2;

        double playerX = 0;
        double playerY = 0;
        int[][] map = mapController.getMap();
        outer:
        for (int row = 0; row < mapRows; row++) {
            for (int col = 0; col < mapCols; col++) {
                if (map[row][col] == 2) {
                    playerX = col * tileSize;
                    playerY = row * tileSize;
                    break outer;
                }
            }
        }
        playerController = new PlayerController(playerX, playerY, windowWidth, windowHeight);
        playerController.setMap(mapController.getMap());
        playerController.setTileSize(mapController.getTileSize());

        timerController = new TimerController();

        String backstoryText = loadBackStory("/story/backstory.txt");
        backStoryOverlay = new BackStoryOverlay(backstoryText, () -> {
            backstoryActive = false;
            timerController.start();
        });

        objectiveController.setObjectiveForMap(currentMapFile);

        Canvas canvas = new Canvas(windowWidth, windowHeight);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        javafx.scene.layout.Pane prisonBg = Effect.createPrisonBackground(windowWidth, windowHeight);

        StackPane root = new StackPane();
        root.getChildren().addAll(prisonBg, canvas);

        Scene scene = new Scene(root, windowWidth, windowHeight);

        InputManager inputManager = new InputManager(
            keysPressed,
            () -> {
                backstoryActive = false;
                timerController.start();
            },
            event -> {
                backStoryOverlay.handleKeyEvent(event);
                if (!backStoryOverlay.isActive()) {
                    backstoryActive = false;
                    timerController.start();
                }
            }
        );
        inputManager.attach(
            scene,
            () -> backstoryActive,
            () -> skipBtnX,
            () -> skipBtnY,
            skipBtnW,
            skipBtnH
        );

        primaryStage.setTitle("Prison Escape");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();

        primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> {
            canvas.setWidth(primaryStage.getWidth());
            prisonBg.setPrefWidth(primaryStage.getWidth());
            windowWidth = primaryStage.getWidth();
            updateMapOffset();
        });
        primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> {
            canvas.setHeight(primaryStage.getHeight());
            prisonBg.setPrefHeight(primaryStage.getHeight());
            windowHeight = primaryStage.getHeight();
            updateMapOffset();
        });

        AnimationTimer gameLoop = new AnimationTimer() {
            private long lastUpdate = 0;
            private final long frameInterval = 16_666_667;

            @Override
            public void handle(long now) {
                if (now - lastUpdate < frameInterval) {
                    return;
                }
                lastUpdate = now;

                if (backstoryActive) {
                    double boxWidth = canvas.getWidth() * 0.7;
                    double boxHeight = 200;
                    double boxX = (canvas.getWidth() - boxWidth) / 2;
                    double boxY = canvas.getHeight() / 2 - boxHeight / 2;
                    skipBtnX = boxX + boxWidth - skipBtnW - 20;
                    skipBtnY = boxY + boxHeight - skipBtnH - 20;
                    overlayRenderer.render(gc, backStoryOverlay, canvas.getWidth(), canvas.getHeight(), skipBtnX, skipBtnY, skipBtnW, skipBtnH);
                } else {
                    update(now);
                    render(gc, canvas.getWidth(), canvas.getHeight());
                }
            }
        };
        gameLoop.start();
    }

    private void updateMapOffset() {
        int mapRows = mapController.getRows();
        int mapCols = mapController.getCols();
        double mapWidth = mapCols * tileSize;
        double mapHeight = mapRows * tileSize;
        mapOffsetX = (windowWidth - mapWidth) / 2;
        mapOffsetY = (windowHeight - mapHeight) / 2;
    }

    private String loadBackStory(String filePath) {
        StringBuilder backstory = new StringBuilder();
        InputStream inputStream = getClass().getResourceAsStream(filePath);
        if (inputStream != null) {
            try (Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8)) {
                while (scanner.hasNextLine()) {
                    backstory.append(scanner.nextLine()).append("\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            backstory.append("No backstory available.");
        }
        return backstory.toString();
    }

    private void update(long now) {
        timerController.update(now);
        playerController.tick(now, keysPressed);
    }

    private void updateOverallObjectiveBoxSize(GraphicsContext gc, String overallObj) {
        if (overallObj.equals(lastOverallObjective)) return;
        lastOverallObjective = overallObj;
        String[] lines = overallObj.split("\n");
        Font font = Font.font("Consolas", 20);
        gc.setFont(font);
        double lineHeight = 28;
        double maxLineWidth = Arrays.stream(lines)
            .mapToDouble(line -> {
                javafx.scene.text.Text textNode = new javafx.scene.text.Text(line);
                textNode.setFont(font);
                return textNode.getLayoutBounds().getWidth();
            })
            .max()
            .orElse(0);
        double paddingX = 44;
        double paddingY = 32;
        overallBoxWidth = maxLineWidth + paddingX;
        overallBoxHeight = lines.length * lineHeight + paddingY;
    }

    private void render(GraphicsContext gc, double width, double height) {
        gc.save();

        EffectPrisonBackground.draw(gc, width, height);
        tileRenderer.render(gc, mapController.getMap(), mapOffsetX, mapOffsetY, tileSize);

        gc.translate(mapOffsetX, mapOffsetY);
        playerController.render(gc);
        gc.restore();

        timerRenderer.render(gc, "⏱ " + timerController.getFormattedTime());

        double currObjBoxWidth = 440;
        double currObjBoxHeight = 48;
        double currObjBoxX = (width - currObjBoxWidth) / 2;
        double currObjBoxY = 80;
        objectiveRenderer.render(
            gc,
            currObjBoxX,
            currObjBoxY,
            currObjBoxWidth,
            currObjBoxHeight,
            objectiveController.getCurrentObjective()
        );

        String overallObj = objectiveController.getOverallObjective();
        updateOverallObjectiveBoxSize(gc, overallObj);
        double overallBoxX = width - overallBoxWidth - 40;
        double overallBoxY = height / 2 - overallBoxHeight / 2;
        objectiveRenderer.render(
            gc,
            overallBoxX,
            overallBoxY,
            overallBoxWidth,
            overallBoxHeight,
            overallObj
        );

        gc.setStroke(Color.LIMEGREEN);
        gc.setLineWidth(4);
        gc.strokeRect(2, 2, width - 4, height - 4);

        if (backstoryActive) {
            drawBackstoryOnEmpty(gc);
        }
    }

    private void drawBackstoryOnEmpty(GraphicsContext gc) {
        int[][] map = mapController.getMap();
        int rows = mapController.getRows();
        int cols = mapController.getCols();

        int maxArea = 0, bestRow = 0, bestCol = 0, bestW = 1, bestH = 1;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (map[r][c] != TileRenderer.EMPTY) continue;
                int maxW = 0;
                while (c + maxW < cols && map[r][c + maxW] == TileRenderer.EMPTY) maxW++;
                for (int h = 1; r + h <= rows; h++) {
                    for (int w = 0; w < maxW; w++) {
                        if (r + h - 1 >= rows || map[r + h - 1][c + w] != TileRenderer.EMPTY) {
                            maxW = w;
                            break;
                        }
                    }
                    int area = maxW * h;
                    if (area > maxArea) {
                        maxArea = area;
                        bestRow = r;
                        bestCol = c;
                        bestW = maxW;
                        bestH = h;
                    }
                    if (maxW == 0) break;
                }
            }
        }

        double x = mapOffsetX + bestCol * tileSize;
        double y = mapOffsetY + bestRow * tileSize;
        double w = bestW * tileSize;
        double h = bestH * tileSize;

        gc.setFill(new Color(0, 0, 0, 0.7));
        gc.fillRoundRect(x, y, w, h, 32, 32);

        String[] lines = backStoryOverlay.getCurrentLines();
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Consolas", Math.max(18, Math.min(32, h / (lines.length + 1)))));
        gc.setTextAlign(TextAlignment.CENTER);
        double lineHeight = h / (lines.length + 1);
        for (int i = 0; i < lines.length; i++) {
            gc.fillText(lines[i], x + w / 2, y + (i + 1) * lineHeight);
        }
    }

    static class EffectPrisonBackground {
        public static void draw(GraphicsContext gc, double width, double height) {
            gc.setFill(new LinearGradient(0, 0, width, height, false, CycleMethod.NO_CYCLE,
                    new Stop(0, Color.web("#4a5670")),
                    new Stop(0.5, Color.web("#23272e")),
                    new Stop(1, Color.web("#181b20"))
            ));
            gc.fillRect(0, 0, width, height);

            gc.setFill(new LinearGradient(0, 0, width, height, false, CycleMethod.NO_CYCLE,
                    new Stop(0, Color.rgb(80, 90, 110, 0.07)),
                    new Stop(1, Color.rgb(30, 30, 40, 0.10))
            ));
            gc.fillRect(0, 0, width, height);

            gc.setStroke(Color.rgb(60, 70, 90, 0.18));
            gc.setLineWidth(2);
            for (int i = 0; i < width; i += 48) {
                gc.strokeLine(i, 0, i, height);
            }
            for (int i = 0; i < height; i += 48) {
                gc.strokeLine(0, i, width, i);
            }

            for (int i = 0; i < width; i += 120) {
                gc.setFill(new LinearGradient(0, 0, 0, 80, false, CycleMethod.NO_CYCLE,
                        new Stop(0, Color.rgb(30, 30, 40, 0.55)),
                        new Stop(1, Color.TRANSPARENT)));
                gc.fillRect(i + 8, 0, 16, 80);
            }
        }
    }
}
