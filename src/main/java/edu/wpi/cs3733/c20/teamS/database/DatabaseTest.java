package edu.wpi.cs3733.c20.teamS.database;

import javafx.application.Application;
import javafx.stage.Stage;

import java.sql.SQLOutput;
import java.util.HashSet;
import java.util.Set;

public class DatabaseTest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        //App.launch(App.class, args);
        DatabaseController dbCont = new DatabaseController();
        dbCont.autoCommit(false);
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


        dbCont.addServiceRequestData(new ServiceData("YEET","Big yeets only", "Hello there",9000,"Hi"));
        dbCont.addServiceRequestData(new ServiceData("DOOT","Big Doots only", "Hello there, general kenobi",9001,"No"));
        dbCont.commit();

        dbCont.updateServiceData(new ServiceData(1,"No more big Doots", "Hi there lad",9000,"Hi"));
        dbCont.deleteServiceWithId(1);

        dbCont.rollBack();

        dbCont.addEmployee(new EmployeeData("BigMan2934","password123",1,"Jamal","Lamar"));
        dbCont.commit();


        Set<ServiceData> servSet = dbCont.getAllServiceRequestData();
        for(ServiceData ed : servSet){
            System.out.println(ed.toString());
        }

        System.out.println(dbCont.checkLogin("BigMan2934","password123"));
        System.out.println(dbCont.checkLogin("Bigmun2934","password123"));
        System.out.println(dbCont.checkLogin("BigMan2934","passwod123"));
        System.out.println(dbCont.getEmployee(""));
    }


}
