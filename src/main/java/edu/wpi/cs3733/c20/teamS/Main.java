package edu.wpi.cs3733.c20.teamS;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        App.launch(Main.class, args);
    }

import com.sun.javafx.geom.Edge;
import edu.wpi.cs3733.c20.teamS.database.DataClasses.EdgeData;
import edu.wpi.cs3733.c20.teamS.database.DataClasses.NodeData;
import edu.wpi.cs3733.c20.teamS.database.DataClasses.ServiceData;
import edu.wpi.cs3733.c20.teamS.database.DatabaseController;

import java.util.HashSet;
import java.util.Set;

public class Main {

  public void start(Stage primaryStage) throws Exception {
    try {
      AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("resources/FXML/UI_client.fxml"));
      Scene scene = new Scene(root, root.getPrefWidth(), root.getPrefHeight());
      // scene.getStylesheets().add(getClass().getResource("").toExternalForm());
      primaryStage.setScene(scene);
      primaryStage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    //App.launch(App.class, args);
    DatabaseController dbCont = new DatabaseController();
    dbCont.importStartUpData();

    HashSet<NodeData> nodeSet = new HashSet<>();
    nodeSet.add(new NodeData("Hi",1,2,1,"Fuller","YESD","LONGG","SHORTT"));
    nodeSet.add(new NodeData("No",1,2,1,"Fuller","YESD","LONGG","SHORTT"));
    dbCont.addSetOfNodes(nodeSet);
    Set<NodeData> returnedSet = dbCont.getAllNodes();
    for(NodeData nd: returnedSet){
      System.out.println(nd.toString());
    }

    NodeData gotNode = dbCont.getNode("Hi");
    System.out.println(gotNode.toString());
    dbCont.addEdge(new EdgeData("EdgeNamee","Hi","No"));
    dbCont.addEdge(new EdgeData("Edge?","No","Hi"));
    Set<EdgeData> edgeSet = dbCont.getAllEdges();
    for(EdgeData ed: edgeSet){
      System.out.println(ed.toString());
    }

    dbCont.addServiceRequest(new ServiceData("YEET","Big yeets only", "Hello there",9000,"Hi"));
    dbCont.addServiceRequest(new ServiceData("DOOT","Big Doots only", "Hello there, general kenobi",9001,"No"));

    dbCont.updateService(new ServiceData(1,"No more big Doots", "Hi there lad",9000,"Hi"));
    dbCont.deleteService(1);

    Set<ServiceData> servSet = dbCont.getAllServiceRequests();
    for(ServiceData ed : servSet){
      System.out.println(ed.toString());
    }
    //dbCont.purgeTable("EDGES");
    //dbCont.addNode(new NodeData("Hi",1,2,1,"Fuller","YESD","LONGG","SHORTT"));
  }

    @Override
    public void start(Stage primaryStage) {
        mainToLoginScreen test = new mainToLoginScreen(primaryStage);
    }

 // mainToLoginScreen test = new mainToLoginScreen(primaryStage);

}
