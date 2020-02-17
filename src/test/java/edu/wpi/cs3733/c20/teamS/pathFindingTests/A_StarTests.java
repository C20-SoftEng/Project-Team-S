package edu.wpi.cs3733.c20.teamS.pathFindingTests;

import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import edu.wpi.cs3733.c20.teamS.NodeData2;
import edu.wpi.cs3733.c20.teamS.pathfinding.A_Star;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class A_StarTests {

    MutableGraph<NodeData2> newGraph = GraphBuilder.undirected().build();

    NodeData2 nodeOne = new NodeData2("1",1,2,2, "Hospital", "Room", "longName1", "LN1");

    NodeData2 nodeTwo = new NodeData2("2", 2, 4, 2, "Hospital", "Room", "longName2", "LN2");

    NodeData2 nodeThree = new NodeData2("3", 10,10,2, "Hospital", "Room", "longName3", "LN3");

    NodeData2 nodeFour = new NodeData2("4", 40,40, 2, "Hospital", "Room", "longName4", "LN4");

    NodeData2 nodeFive = new NodeData2("5", -1,-1, 2, "Hospital", "Room", "longName5", "LN5");

    NodeData2 nodeSix = new NodeData2("6", 70,70,2,"Hospital", "Room", "longName6", "LN6");


    NodeData2 nodeSeven = new NodeData2("7", 8,20,2,"Hospital", "Room", "longName7", "LN7");

    NodeData2 nodeEight = new NodeData2("8", 13,33,2,"Hospital", "Room", "longName8", "LN8");

    NodeData2 nodeNine = new NodeData2("9", 20,5,2,"Hospital", "Room", "longName9", "LN9");

    NodeData2 nodeTen = new NodeData2("10", 33,22,2,"Hospital", "Room", "longName10", "LN10");






    @Test
    public void findPath_ReturnSingleNodePath(){
       newGraph.addNode(nodeOne);
       newGraph.addNode(nodeTwo);
       newGraph.putEdge(nodeOne, nodeTwo);
       A_Star star = new A_Star();
        List<NodeData2> path = star.findPath(newGraph, nodeOne, nodeTwo);
        List<NodeData2> realPath = new ArrayList<>();
        realPath.add(nodeOne);
        realPath.add(nodeTwo);

        for(NodeData2 data: path){
            System.out.println(data.nodeID());
        }
        assertEquals(realPath, path);
    }

    @Test
    public void findPath_Returning(){
        newGraph.addNode(nodeOne);
        newGraph.addNode(nodeTwo);
        newGraph.putEdge(nodeOne,nodeTwo);
        newGraph.addNode(nodeThree);
        newGraph.putEdge(nodeTwo, nodeThree);
        newGraph.addNode(nodeFour);
        newGraph.putEdge(nodeThree, nodeFour);
        newGraph.addNode(nodeFive);
        newGraph.putEdge(nodeOne,nodeFive);
        newGraph.addNode(nodeSix);
        newGraph.putEdge(nodeFour, nodeSix);
        newGraph.addNode(nodeSeven);
        newGraph.putEdge(nodeThree, nodeSeven);
        newGraph.addNode(nodeEight);
        newGraph.putEdge(nodeSeven, nodeEight);
        newGraph.addNode(nodeNine);
        newGraph.putEdge(nodeThree, nodeNine);
        newGraph.addNode(nodeTen);
        newGraph.putEdge(nodeNine, nodeTen);
        A_Star star = new A_Star();
        List<NodeData2> path =  star.findPath(newGraph, nodeOne, nodeFour);
        List<NodeData2> realPath = new ArrayList<>();
        realPath.add(nodeOne);
        realPath.add(nodeTwo);
        realPath.add(nodeThree);
        realPath.add(nodeFour);

        assertEquals(realPath, path);
    }

    @Test
    public void findPath_Different(){
        newGraph.addNode(nodeOne);
        newGraph.addNode(nodeTwo);
        newGraph.putEdge(nodeOne,nodeTwo);
        newGraph.addNode(nodeThree);
        newGraph.putEdge(nodeTwo, nodeThree);
        newGraph.addNode(nodeFour);
        newGraph.putEdge(nodeThree, nodeFour);
        newGraph.addNode(nodeFive);
        newGraph.putEdge(nodeOne,nodeFive);
        newGraph.addNode(nodeSix);
        newGraph.putEdge(nodeFour, nodeSix);
        newGraph.addNode(nodeSeven);
        newGraph.putEdge(nodeThree, nodeSeven);
        newGraph.addNode(nodeEight);
        newGraph.putEdge(nodeSeven, nodeEight);
        newGraph.addNode(nodeNine);
        newGraph.putEdge(nodeThree, nodeNine);
        newGraph.addNode(nodeTen);
        newGraph.putEdge(nodeNine, nodeTen);
        newGraph.putEdge(nodeEight, nodeFour);
        newGraph.putEdge(nodeTen, nodeFour);
        A_Star star = new A_Star();
        List<NodeData2> path =  star.findPath(newGraph, nodeOne, nodeFour);
        List<NodeData2> realPath = new ArrayList<>();
        realPath.add(nodeOne);
        realPath.add(nodeTwo);
        realPath.add(nodeThree);
        realPath.add(nodeFour);

        assertEquals(realPath, path);
    }

//    @Test
//    public void findPath_NoValidPath(){
//        newGraph.addNode(nodeOne);
//        newGraph.addNode(nodeTwo);
//        newGraph.addNode(nodeThree);
//
//        A_Star star = new A_Star();
//        Set<NodeData> path = star.findPath(newGraph, nodeOne, nodeTwo);
//        Set<NodeData> realPath = new HashSet<>();
//
//        for(NodeData data: path){
//            System.out.println(data.nodeID());
//        }
//        assertEquals(realPath, path);
//
//    }
//



}
