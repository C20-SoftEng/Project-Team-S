package edu.wpi.teamname;

import edu.wpi.teamname.database.DatabaseController;

public class Main {

  public static void main(String[] args) {
    App.launch(App.class, args);
    DatabaseController dbCont = new DatabaseController();
  }
}
