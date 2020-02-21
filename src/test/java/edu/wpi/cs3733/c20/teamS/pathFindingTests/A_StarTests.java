//package edu.wpi.cs3733.c20.teamS.pathFindingTests;
//
//import com.google.common.graph.GraphBuilder;
//import com.google.common.graph.MutableGraph;
//import edu.wpi.cs3733.c20.teamS.database.NodeData;
//import edu.wpi.cs3733.c20.teamS.pathfinding.A_Star;
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
////
////public class A_StarTests {
////
////    private MutableGraph<NodeData> newGraph = GraphBuilder.undirected().build();
////
////    NodeData nodeOne = new NodeData("1",1,2,2, "Hospital", "Room", "longName1", "LN1");
////    NodeData nodeTwo = new NodeData("2", 2, 4, 2, "Hospital", "Room", "longName2", "LN2");
////    NodeData nodeThree = new NodeData("3", 10,10,2, "Hospital", "Room", "longName3", "LN3");
////    NodeData nodeFour = new NodeData("4", 40,40, 2, "Hospital", "Room", "longName4", "LN4");
////    NodeData nodeFive = new NodeData("5", -1,-1, 2, "Hospital", "Room", "longName5", "LN5");
////    NodeData nodeSix = new NodeData("6", 70,70,2,"Hospital", "Room", "longName6", "LN6");
////    NodeData nodeSeven = new NodeData("7", 8,20,2,"Hospital", "Room", "longName7", "LN7");
////    NodeData nodeEight = new NodeData("8", 13,33,2,"Hospital", "Room", "longName8", "LN8");
////    NodeData nodeNine = new NodeData("9", 20,5,2,"Hospital", "Room", "longName9", "LN9");
////    NodeData nodeTen = new NodeData("10", 33,22,2,"Hospital", "Room", "longName10", "LN10");
////
////    @Test
////    public void findPath_ReturnSingleNodePath(){
////        newGraph.addNode(nodeOne);
////        newGraph.addNode(nodeTwo);
////        newGraph.putEdge(nodeOne, nodeTwo);
////       A_Star star = new A_Star();
////        List<NodeData> path = star.findPath(newGraph, nodeOne, nodeTwo);
////        List<NodeData> realPath = new ArrayList<>();
////        realPath.add(nodeOne);
////        realPath.add(nodeTwo);
////
////        assertEquals(realPath, path);
////    }
////
////    @Test
////    public void findPath_Returning(){
////        newGraph.addNode(nodeOne);
////        newGraph.addNode(nodeTwo);
////        newGraph.putEdge(nodeOne,nodeTwo);
////        newGraph.addNode(nodeThree);
////        newGraph.putEdge(nodeTwo, nodeThree);
////        newGraph.addNode(nodeFour);
////        newGraph.putEdge(nodeThree, nodeFour);
////        newGraph.addNode(nodeFive);
////        newGraph.putEdge(nodeOne,nodeFive);
////        newGraph.addNode(nodeSix);
////        newGraph.putEdge(nodeFour, nodeSix);
////        newGraph.addNode(nodeSeven);
////        newGraph.putEdge(nodeThree, nodeSeven);
////        newGraph.addNode(nodeEight);
////        newGraph.putEdge(nodeSeven, nodeEight);
////        newGraph.addNode(nodeNine);
////        newGraph.putEdge(nodeThree, nodeNine);
////        newGraph.addNode(nodeTen);
////        newGraph.putEdge(nodeNine, nodeTen);
////        A_Star star = new A_Star();
////        List<NodeData> path =  star.findPath(newGraph, nodeOne, nodeFour);
////        List<NodeData> realPath = new ArrayList<>();
////        realPath.add(nodeOne);
////        realPath.add(nodeTwo);
////        realPath.add(nodeThree);
////        realPath.add(nodeFour);
////
////        assertEquals(realPath, path);
////    }
////
////    @Test
////    public void findPath_Different(){
////        newGraph.addNode(nodeOne);
////        newGraph.addNode(nodeTwo);
////        newGraph.putEdge(nodeOne,nodeTwo);
////        newGraph.addNode(nodeThree);
////        newGraph.putEdge(nodeTwo, nodeThree);
////        newGraph.addNode(nodeFour);
////        newGraph.putEdge(nodeThree, nodeFour);
////        newGraph.addNode(nodeFive);
////        newGraph.putEdge(nodeOne,nodeFive);
////        newGraph.addNode(nodeSix);
////        newGraph.putEdge(nodeFour, nodeSix);
////        newGraph.addNode(nodeSeven);
////        newGraph.putEdge(nodeThree, nodeSeven);
////        newGraph.addNode(nodeEight);
////        newGraph.putEdge(nodeSeven, nodeEight);
////        newGraph.addNode(nodeNine);
////        newGraph.putEdge(nodeThree, nodeNine);
////        newGraph.addNode(nodeTen);
////        newGraph.putEdge(nodeNine, nodeTen);
////        newGraph.putEdge(nodeEight, nodeFour);
////        newGraph.putEdge(nodeTen, nodeFour);
////        A_Star star = new A_Star();
////        List<NodeData> path =  star.findPath(newGraph, nodeOne, nodeFour);
////        List<NodeData> realPath = new ArrayList<>();
////        realPath.add(nodeOne);
////        realPath.add(nodeTwo);
////        realPath.add(nodeThree);
////        realPath.add(nodeFour);
////
//        assertEquals(realPath, path);
//    }
//
//    @Test
//    public void findPath_NoValidPath(){
//        newGraph.addNode(nodeOne);
////        newGraph.addNode(nodeTwo);
////        newGraph.addNode(nodeThree);
////
////        A_Star star = new A_Star();
////        List<NodeData> path = star.findPath(newGraph, nodeOne, nodeTwo);
////        List<NodeData> realPath = new ArrayList<>();
////
////        for(NodeData data: path){
////            System.out.println(data.getNodeID());
////        }
////        assertEquals(realPath, path);
////    }
////
////    @Test
////    public void findPath_singleNodePath(){
////        newGraph.addNode(nodeOne);
////
////        A_Star star = new A_Star();
////        List<NodeData> path = star.findPath(newGraph, nodeOne, nodeOne);
////        List<NodeData> realPath = new ArrayList<>();
////        realPath.add(nodeOne);
////
////        assertEquals(realPath, path);
////    }
////
////    @Test
////    public void euclideanDistance_correctDistance(){
////        A_Star star = new A_Star();
////        double distance = star.distance(nodeOne, nodeTwo);
////        double realDistance =
////                Math.sqrt((nodeTwo.getxCoordinate()-nodeOne.getxCoordinate())*(nodeTwo.getxCoordinate()-nodeOne.getxCoordinate()) + (nodeTwo.getyCoordinate()-nodeOne.getyCoordinate())*(nodeTwo.getyCoordinate()-nodeOne.getyCoordinate()));
////        assertEquals(realDistance, distance);
////    }
////
////    @Test
////    public void findPath_Recursion(){
////        newGraph.addNode(nodeOne);
////        newGraph.addNode(nodeTwo);
////        newGraph.putEdge(nodeOne, nodeTwo);
////        newGraph.addNode(nodeThree);
////        newGraph.putEdge(nodeTwo, nodeThree);
////        newGraph.putEdge(nodeThree, nodeOne);
////        A_Star star = new A_Star();
////        ArrayList<NodeData> path = star.findPath(newGraph, nodeOne, nodeThree);
////        ArrayList<NodeData> realPath = new ArrayList<>();
////        realPath.add(nodeOne);
////        realPath.add(nodeThree);
////
////        assertEquals(realPath, path);
////
////    }
////
////}
