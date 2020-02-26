package edu.wpi.cs3733.c20.teamS;

import edu.wpi.cs3733.c20.teamS.pathfinding.AStar;

import edu.wpi.cs3733.c20.teamS.pathfinding.PathfindingContext;

public final class Settings {
    private final PathfindingContext pathfinder = new PathfindingContext(new AStar());
    private boolean useQuickNodePlacingTool = false;

    public PathfindingContext pathfinder(){
        return pathfinder;
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

}

