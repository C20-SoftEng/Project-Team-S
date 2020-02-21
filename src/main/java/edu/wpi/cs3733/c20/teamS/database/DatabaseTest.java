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
        //dbCont.autoCommit(false);
        dbCont.importStartUpData();



        HashSet<NodeData> nodeSet = new HashSet<>();
        nodeSet.add(new NodeData("Hi",1,2,1,"Fuller","YESD","LONGG","SHORTT"));
        nodeSet.add(new NodeData("No",1,2,1,"Fuller","YESD","LONGG","SHORTT"));
        dbCont.addSetOfNodes(nodeSet);
        Set<NodeData> returnedSet = dbCont.getAllNodes();
        for(NodeData nd: returnedSet){
            System.out.println(nd.toString());
        }

        Set<NodeData> returnedSet2 = dbCont.getAllNodesOfType("ELEV");
        for(NodeData nd: returnedSet2){
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


        dbCont.addServiceRequestData(new ServiceData("YEET","Big yeets only", "Hello there", "Daterr",1,"Hi"));
        dbCont.addServiceRequestData(new ServiceData("DOOT","Big Doots only", "Hello there, general kenobi", "I barely know her",2,"No"));
        dbCont.commit();

        dbCont.updateServiceData(new ServiceData(1,"REEE","No more big Doots", "Hi there lad","Hi",2,null,"Hi"));


        EmployeeData BigMan = new EmployeeData(4, "BigMan2954", "password123", 1, "Jamal", "Lamar");
        dbCont.addEmployee(BigMan);


        dbCont.addEmployee(new EmployeeData("BigMan2934","password123",1,"Jamal","Lamar"));
        dbCont.addEmployee(new EmployeeData("jebus","pworddog",1,"Jamal","Lamar"));


        BigMan.setAccessLevel(2);
        BigMan.setPassword("password456");
        BigMan.setFirstName("Bon");
        BigMan.setLastName("Jovi");

        dbCont.updateEmployee(BigMan);
        Set<ServiceData> servSet = dbCont.getAllServiceRequestData();
        for(ServiceData ed : servSet){
            System.out.println(ed.toString());
        }

        System.out.println(dbCont.checkLogin("BigMan2934","password123"));
        System.out.println(dbCont.checkLogin("Bigmun2934","password123"));
        System.out.println(dbCont.checkLogin("BigMan2934","passwod123"));
        System.out.println(dbCont.getEmployee(""));


        dbCont.addCapability(1,"DOGS");
        dbCont.addCapability(1,"DRUG");
        dbCont.addCapability(1,"GIFT");
        dbCont.addCapability(1,"GEDR");
        dbCont.addCapability(1,"EDSR");


        dbCont.addCapability(2,"MAIT");
        dbCont.addCapability(2,"DOGS");

        System.out.println("Employees capable of dogs");
        for(int d : dbCont.getCapableEmployees("DOGS")){
            System.out.println(d);
        }

        System.out.println("Removing capability of 1 to service dog");
        dbCont.removeCapability(1,"DOGS");

        System.out.println("Employees capable of dogs after removing 1's abilty for dogs");
        for(int d : dbCont.getCapableEmployees("DOGS")){
            System.out.println(d);
        }

        System.out.println(dbCont.checkCapable(2,"MAIT"));
        System.out.println(dbCont.checkCapable(1,"MAIT"));




    }


}
