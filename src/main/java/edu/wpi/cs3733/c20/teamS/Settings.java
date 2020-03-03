package edu.wpi.cs3733.c20.teamS;

import edu.wpi.cs3733.c20.teamS.database.DatabaseController;
import edu.wpi.cs3733.c20.teamS.database.NodeData;
import edu.wpi.cs3733.c20.teamS.pathfinding.AStar;

import edu.wpi.cs3733.c20.teamS.pathfinding.PathfindingContext;
import javafx.scene.paint.Color;

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
    public Color editHitboxColorNormal() {
        return Color.BLUE.deriveColor(1, 1, 1, .45);
    }
    public Color editHitboxColorHighlight() {
        return Color.AQUA.deriveColor(1, 1, 1, 0.5);
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

    DatabaseController getter = new DatabaseController();
    public NodeData currentLocation = getter.getNode("SHALL02002");
    //SHALL02002,1480.0,1145.0,2,Faulkner,HALL,2nd Floor Elevator Hallway,2main_elev_hall
   // public NodeData testingLocation = NodeData();

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

}

