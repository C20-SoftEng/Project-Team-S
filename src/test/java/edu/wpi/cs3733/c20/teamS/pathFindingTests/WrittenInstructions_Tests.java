package edu.wpi.cs3733.c20.teamS.pathFindingTests;
import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.wpi.cs3733.c20.teamS.database.NodeData;
import edu.wpi.cs3733.c20.teamS.pathfinding.WrittenInstructions;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class WrittenInstructions_Tests {
    NodeData nodeOne = new NodeData("1",100,60,2, "Hospital", "Room", "longName1", "LN1");

    NodeData nodeTwo = new NodeData("2", 120, 60, 2, "Hospital", "Room", "longName2", "LN2");

    NodeData nodeTwo2 = new NodeData("2", 102, 60, 2, "Hospital", "Room", "longName2", "LN2");


    NodeData nodeThree = new NodeData("3", 180,60,3, "Hospital", "Room", "longName3", "LN3");

    NodeData nodeFour = new NodeData("4", 200,60, 2, "Hospital", "Room", "longName4", "LN4");

    NodeData nodeFive = new NodeData("5", 200,20, 2, "Hospital", "Room", "longName5", "LN5");

    NodeData nodeEleven = new NodeData("11",250, 20, 3, "Hospital", "Room", "longName11", "LN11");

    NodeData nodeTwelve = new NodeData("12", 260,30,3, "Hospital", "Room", "longName12", "LN12");

    NodeData nodeThirteen = new NodeData("13", 200,20,3, "Hospital", "ELEV", "longName13", "LN13");

    NodeData nodeFourteen = new NodeData("14", 209, 20, 3, "Hospital", "Room", "longName14", "LN14");

    ///=========================================================================================================================================
    NodeData nodeSix = new NodeData("6", 300,700,2,"Hospital", "Room", "longName6", "LN6");

    NodeData nodeSeven = new NodeData("7", 300,840,2,"Hospital", "Room", "longName7", "LN7");

    NodeData nodeEight = new NodeData("8", 300,900,2,"Hospital", "Room", "longName8", "LN8");

    NodeData nodeNine = new NodeData("9", 300,1000,2,"Hospital", "Room", "longName9", "LN9");

    NodeData nodeTen = new NodeData("10", 700,1000,2,"Hospital", "Room", "longName10", "LN10");



    @Test
    public void turnLeftTest1() {
        ArrayList<NodeData> pathtest1 = new ArrayList<>();
        pathtest1.add(nodeOne);
        pathtest1.add(nodeTwo);
        pathtest1.add(nodeThree);
        pathtest1.add(nodeFour);
        pathtest1.add(nodeFive);
        pathtest1.add(nodeEleven);
        pathtest1.add(nodeTwelve);



        WrittenInstructions wi = new WrittenInstructions(pathtest1);

      LinkedList<String> realInstructions = new LinkedList<>();
        realInstructions.add("In 27FT (8M), Turn Left");
        realInstructions.add("In 11FT (3M), Turn Right");
        realInstructions.add("In 13FT (4M), Turn Right");



        assertEquals(realInstructions, wi.directions());
    }


    @Test
    public void secondLastTurnTest_Elevator(){
        ArrayList<NodeData> pathtest3 = new ArrayList<>();
        pathtest3.add(nodeOne);
        pathtest3.add(nodeTwo);
        pathtest3.add(nodeThree);
        pathtest3.add(nodeFour);
        pathtest3.add(nodeThirteen);
        pathtest3.add(nodeEleven);

        WrittenInstructions test3 = new WrittenInstructions(pathtest3);

        List realInstructions = new ArrayList();
        realInstructions.add("In 27FT (8M), Turn Left");
        realInstructions.add("In 11FT (3M), Turn Right");
        realInstructions.add("Take The Elevator To Floor 3, Then Go Straight");

        assertEquals(realInstructions, test3.directions());

    }

    @Test
    public void elevatorTest(){
        ArrayList<NodeData> pathtest4  = new ArrayList<>();
        pathtest4.add(nodeOne);
        pathtest4.add(nodeTwo);
        pathtest4.add(nodeThirteen);
        pathtest4.add(nodeEleven);
        pathtest4.add(nodeTwelve);
        //pathtest4.add(node)

        WrittenInstructions test4 = new WrittenInstructions(pathtest4);

        List realInstructions = new ArrayList();
        realInstructions.add("In 5FT (2M), Turn Left");
        realInstructions.add("In 24FT (7M), Turn Right");
        realInstructions.add("Take The Elevator To Floor 3");
        realInstructions.add("In 13FT (4M), Turn Right");


        assertEquals(realInstructions, test4.directions());

    }

    @Test
    public void lessThan10FeetTest(){
        ArrayList<NodeData> pathtest5  = new ArrayList<>();
        pathtest5.add(nodeOne);
        pathtest5.add(nodeTwo2);
        pathtest5.add(nodeThirteen);
        pathtest5.add(nodeFourteen);
        //pathtest5.add(nodeEleven);
        //pathtest5.add(nodeTwelve);

        WrittenInstructions test5 = new WrittenInstructions(pathtest5);

        List realInstructions = new ArrayList();
        realInstructions.add("Turn Left");
        realInstructions.add("In 28FT (9M), Turn Right");
        realInstructions.add("Take The Elevator To Floor 3, Then Go Straight");
       // realInstructions.add("Turn Left");

        assertEquals(realInstructions, test5.directions());
    }




}
