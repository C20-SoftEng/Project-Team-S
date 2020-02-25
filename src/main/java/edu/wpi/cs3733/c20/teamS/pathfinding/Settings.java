package edu.wpi.cs3733.c20.teamS.pathfinding;

public class Settings {


    IPathfinder pathFinder = new AStar();

    public IPathfinder pathfinder(){
        return pathFinder;
    }

    public void setPathFinder(IPathfinder pathFinder){
        this.pathFinder = pathFinder;

    }




    private Settings(){}

    private static class SingletonHelper{

        private static final Settings settings = new Settings();
    }


    public static Settings settings(){
        return SingletonHelper.settings;
    }

}

