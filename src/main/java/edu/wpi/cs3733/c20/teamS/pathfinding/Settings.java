package edu.wpi.cs3733.c20.teamS.pathfinding;

public class Settings {
    private final PathfindingContext pathfinder = new PathfindingContext(new DepthFirst());

    public PathfindingContext pathfinder(){
        return pathfinder;
    }

    private Settings(){}

    private static class SingletonHelper {

        private static final Settings settings = new Settings();
    }


    public static Settings get(){
        return SingletonHelper.settings;
    }

}

