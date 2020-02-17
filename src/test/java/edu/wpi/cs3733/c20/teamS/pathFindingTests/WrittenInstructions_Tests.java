package edu.wpi.cs3733.c20.teamS.pathFindingTests;
import static org.junit.jupiter.api.Assertions.assertEquals;
import edu.wpi.cs3733.c20.teamS.NodeData;
import edu.wpi.cs3733.c20.teamS.pathfinding.WrittenInstructions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;


public class WrittenInstructions_Tests {
    NodeData nodeOne = new NodeData("1",100,60,2, "Hospital", "Room", "longName1", "LN1");

    NodeData nodeTwo = new NodeData("2", 120, 60, 2, "Hospital", "Room", "longName2", "LN2");

    NodeData nodeThree = new NodeData("3", 180,60,2, "Hospital", "Room", "longName3", "LN3");

    NodeData nodeFour = new NodeData("4", 200,60, 2, "Hospital", "Room", "longName4", "LN4");

    NodeData nodeFive = new NodeData("5", 200,20, 2, "Hospital", "Room", "longName5", "LN5");
///=========================================================================================================================================
    NodeData nodeSix = new NodeData("6", 300,700,2,"Hospital", "Room", "longName6", "LN6");

    NodeData nodeSeven = new NodeData("7", 300,640,2,"Hospital", "Room", "longName7", "LN7");

    NodeData nodeEight = new NodeData("8", 300,600,2,"Hospital", "Room", "longName8", "LN8");

    NodeData nodeNine = new NodeData("9", 300,580,2,"Hospital", "Room", "longName9", "LN9");

    NodeData nodeTen = new NodeData("10", 330,580,2,"Hospital", "Room", "longName10", "LN10");



//    @Test
//    public void turnLeftTest1() {
//        ArrayList<NodeData> pathtest1 = new ArrayList<NodeData>();
//        pathtest1.add(nodeOne);
//        pathtest1.add(nodeTwo);
//        pathtest1.add(nodeThree);
//        pathtest1.add(nodeFour);
//        pathtest1.add(nodeFive);
//
//        WrittenInstructions wi = new WrittenInstructions(pathtest1);
//
//        assertEquals("Turn Left In 26FT OR 8M", wi.directions());
//    }

    @Test
    public void turnRightTest1() {
        ArrayList<NodeData> pathtest2 = new ArrayList<NodeData>();
        pathtest2.add(nodeSix);
        pathtest2.add(nodeSeven);
        pathtest2.add(nodeEight);
        pathtest2.add(nodeNine);
        pathtest2.add(nodeTen);

        WrittenInstructions test2 = new WrittenInstructions(pathtest2);


        assertEquals("Turn Right In 26FT OR 8M",test2.directions());
    }






}
