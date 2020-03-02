package edu.wpi.cs3733.c20.teamS;

import edu.wpi.cs3733.c20.teamS.pathDisplaying.MainScreenController;
import edu.wpi.cs3733.c20.teamS.pathfinding.AStar;
import edu.wpi.cs3733.c20.teamS.pathfinding.PathfindingContext;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.HashSet;
import java.util.Set;

public final class Settings {
    private final PathfindingContext pathfinder = new PathfindingContext(new AStar());
    private boolean useQuickNodePlacingTool = false;

    public PathfindingContext pathfinder(){
        return pathfinder;
    }
    public Color pathColor() {
        return Color.RED;
    }
    public Color nodeColorElevator() {
        return Color.GREEN.deriveColor(1, 1, 1, 0.5);
    }
    public Color nodeFillColorNormal() {
        return Color.ORANGE.deriveColor(1, 1, 1, 0.5);
    }
    public Color nodeStrokeColorNormal() {
        return Color.ORANGE;
    }
    public Color nodeFillColorHighlight() {
        return Color.AQUA.deriveColor(1, 1, 1, 0.5);
    }
    public Color editRoomColorNormal() {
        return Color.BLUE.deriveColor(1, 1, 1, .45);
    }
    public Color editRoomColorHighlight() {
        return Color.AQUA.deriveColor(1, 1, 1, 0.5);
    }
    public int editRoomVertexRadius() {
        return 5;
    }
    public Color editEdgeColorNormal() {
        return Color.BLUE;
    }
    public Color editEdgeColorHighlight() {
        return Color.AQUA;
    }
    public int editEdgeStrokeWidth() {
        return 5;
    }

    /**
     * Whether to should use the node-placing tool that doesn't require a dialog interaction.
     */
    public boolean useQuickNodePlacingTool() {
        return useQuickNodePlacingTool;
    }

    /**
     * Sets whether to use the node-placing tool that doesn't require a dialog interaction.
     * @param value
     */
    public void setUseQuickNodePlacingTool(boolean value) {
        useQuickNodePlacingTool = value;
    }

    private Settings(){}

    private static class SingletonHelper {

        private static final Settings settings = new Settings();
    }

    public static Settings get(){
        return SingletonHelper.settings;
    }

    public static Set<Stage> openWindows = new HashSet<>();

    static FXMLLoader singleLoader;
    public static Stage primaryStage;
    public static Parent mainScreenRoot;
    public static Parent splashRoot;
    public static Parent employeeRoot;
    public static edu.wpi.cs3733.c20.teamS.serviceRequests.Employee loggedIn = null;
    public static MainScreenController mainScreenController;
}

