package edu.wpi.cs3733.c20.teamS;

import edu.wpi.cs3733.c20.teamS.Editing.MapEditingScreen;
import edu.wpi.cs3733.c20.teamS.applicationInitializer.ApplicationInitializer;
import edu.wpi.cs3733.c20.teamS.database.DatabaseController;
import edu.wpi.cs3733.c20.teamS.serviceRequests.AccessLevel;
import edu.wpi.cs3733.c20.teamS.serviceRequests.Employee;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    private static final StartupScreen START_SCREEN = StartupScreen.SPLASH;

    public void start(Stage primaryStage) {
        DatabaseController dbc = new DatabaseController();
        dbc.importStartUpData();

        Settings.loggedIn = new Employee(0, "Default", AccessLevel.USER);
        Settings.primaryStage = primaryStage;
        new ApplicationInitializer(dbc).initBigFXMLs();

        switch (START_SCREEN) {
            case MAIN:
                new MainToLoginScreen();
                break;
            case SPLASH:
                //new MainStartScreen(primaryStage);
                MainStartScreen.showDialog();
                break;
            case MAP_EDITING:
                Settings.loggedIn = new Employee(0, "Bob", AccessLevel.ADMIN);
                new MapEditingScreen();
                break;
            default:
                throw new IllegalArgumentException("Unexpected value in StartupScreen switch statement.");
        }
    }

    //9003,staff,staff,2,Wilson,Wong
    public static void main(String[] args) {
        App.launch();
    }
}
