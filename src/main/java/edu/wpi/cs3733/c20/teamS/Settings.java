package edu.wpi.cs3733.c20.teamS;

import edu.wpi.cs3733.c20.teamS.Editing.EditScreenController;
import edu.wpi.cs3733.c20.teamS.pathDisplaying.MainScreenController;
import edu.wpi.cs3733.c20.teamS.pathfinding.AStar;
import edu.wpi.cs3733.c20.teamS.pathfinding.PathfindingContext;
import edu.wpi.cs3733.c20.teamS.serviceRequests.Employee;
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
    public double editEdgeEnlargeRatio() {
        return 2.5;
    }
    public double editEdgeCollisionMaskWidth() {
        return 20;
    }
    public int minZoomStage() {
        return -1;
    }
    public int maxZoomStage() {
        return 0;
    }

    public int floors() {
        return 7;
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

//    private static class SingletonHelper {
//
//        private static final Settings settings = new Settings();
//    }

    private static Settings set;

    //private SingletonHelper sh;

    synchronized public static Settings get(){

        if (set == null)
        {
            // if instance is null, initialize
            set = new Settings();
        }
        return set;
    }


    public static Set<Stage> openWindows = new HashSet<>();

    static FXMLLoader singleLoader;

    public Set<Stage> getOpenWindows() {
        return openWindows;
    }

    public void setOpenWindows(Set<Stage> openWindows) {
        Settings.openWindows = openWindows;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        Settings.primaryStage = primaryStage;
    }

    public Parent getMainScreenRoot() {
        return mainScreenRoot;
    }

    public void setMainScreenRoot(Parent mainScreenRoot) {
        Settings.mainScreenRoot = mainScreenRoot;
    }

    public Parent getSplashRoot() {
        return splashRoot;
    }

    public void setSplashRoot(Parent splashRoot) {
        Settings.splashRoot = splashRoot;
    }

    public Parent getEmployeeRoot() {
        return employeeRoot;
    }

    public void setEmployeeRoot(Parent employeeRoot) {
        Settings.employeeRoot = employeeRoot;
    }

    public Employee getLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(Employee loggedIn) {
        Settings.loggedIn = loggedIn;
    }

    public MainScreenController getMainScreenController() {
        return mainScreenController;
    }

    public void setMainScreenController(MainScreenController mainScreenController) {
        Settings.mainScreenController = mainScreenController;
    }

    public EditScreenController getEditScreenController() {
        return editScreenController;
    }

    public void setEditScreenController(EditScreenController editScreenController) {
        Settings.editScreenController = editScreenController;
    }

    private static Stage primaryStage;
    private static Parent mainScreenRoot;
    private static Parent splashRoot;
    private static Parent employeeRoot;
    private static edu.wpi.cs3733.c20.teamS.serviceRequests.Employee loggedIn = null;
    private static MainScreenController mainScreenController;
    private static EditScreenController editScreenController;
}

