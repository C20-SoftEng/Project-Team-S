package edu.wpi.cs3733.c20.teamS.pathFindingTests;

import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import edu.wpi.cs3733.c20.teamS.GraphNode;
import edu.wpi.cs3733.c20.teamS.pathfinding.A_Star;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class A_StarTests {

    MutableGraph<GraphNode> newGraph = GraphBuilder.undirected().build();

    GraphNode nodeOne = new GraphNode("1",1,2,2, "Hospital", "Room", "longName1", "LN1");

    GraphNode nodeTwo = new GraphNode("2", 2, 4, 2, "Hospital", "Room", "longName2", "LN2");

    GraphNode nodeThree = new GraphNode("3", 10,10,2, "Hospital", "Room", "longName3", "LN3");

    GraphNode nodeFour = new GraphNode("4", 40,40, 2, "Hospital", "Room", "longName4", "LN4");

    GraphNode nodeFive = new GraphNode("5", -1,-1, 2, "Hospital", "Room", "longName5", "LN5");

    GraphNode nodeSix = new GraphNode("6", 70,70,2,"Hospital", "Room", "longName6", "LN6");


    GraphNode nodeSeven = new GraphNode("7", 8,20,2,"Hospital", "Room", "longName7", "LN7");

    GraphNode nodeEight = new GraphNode("8", 13,33,2,"Hospital", "Room", "longName8", "LN8");

    GraphNode nodeNine = new GraphNode("9", 20,5,2,"Hospital", "Room", "longName9", "LN9");

    GraphNode nodeTen = new GraphNode("10", 33,22,2,"Hospital", "Room", "longName10", "LN10");






    @Test
    public void findPath_ReturnSingleNodePath(){
       newGraph.addNode(nodeOne);
       newGraph.addNode(nodeTwo);
       newGraph.putEdge(nodeOne, nodeTwo);
       A_Star star = new A_Star();
        List<GraphNode> path = star.findPath(newGraph, nodeOne, nodeTwo);
        List<GraphNode> realPath = new ArrayList<>();
        realPath.add(nodeOne);
        realPath.add(nodeTwo);

        for(GraphNode data: path){
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
        List<GraphNode> path =  star.findPath(newGraph, nodeOne, nodeFour);
        List<GraphNode> realPath = new ArrayList<>();
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
        List<GraphNode> path =  star.findPath(newGraph, nodeOne, nodeFour);
        List<GraphNode> realPath = new ArrayList<>();
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
