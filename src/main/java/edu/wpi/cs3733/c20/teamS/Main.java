package edu.wpi.cs3733.c20.teamS;

import edu.wpi.cs3733.c20.teamS.database.DatabaseController;

public class Main {

  public static void main(String[] args) {
    App.launch(App.class, args);
    DatabaseController dbCont = new DatabaseController();
  }
}
