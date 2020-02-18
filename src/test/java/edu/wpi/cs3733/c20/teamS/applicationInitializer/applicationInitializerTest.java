package edu.wpi.cs3733.c20.teamS.applicationInitializer;
import com.google.common.graph.EndpointPair;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import edu.wpi.cs3733.c20.teamS.database.DataClasses.EdgeData;
import edu.wpi.cs3733.c20.teamS.database.DataClasses.NodeData;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static edu.wpi.cs3733.c20.teamS.applicationInitializer.applicationInitializer.findNodeData;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class applicationInitializerTest {
    /*
    @Test
    public void TestToGraphNode(){
        //DatabaseController controller = new DatabaseController();
        //applicationInitializer initializer = new applicationInitializer(controller);
        NodeData nodeData = new NodeData("node1", 0,0,1,
                "Foisie", "regular", "LongName", "ShortName");
        GraphNode result = applicationInitializer.toGraphNode(nodeData);
        GraphNode verification = new GraphNode("node1", 0,0,1,
                "Foisie", "regular", "LongName", "ShortName");
        //assertEquals(graphNode.nodeID(), "node1");
        assertTrue(result.equals(verification));
    }

    @Test
    public void TestFindGraphNode(){
        GraphNode node1 = new GraphNode("node1", 0, 0, 1,
                "Foisie", "regular", "LongName", "ShortName");
        GraphNode node2 = new GraphNode("node2", 0, 0, 1,
                "Foisie", "regular", "LongName", "ShortName");
        GraphNode node3 = new GraphNode("node3", 0, 0, 1,
                "Foisie", "regular", "LongName", "ShortName");
        Set<GraphNode> graphNodes = new HashSet<>();
        graphNodes.add(node1);
        graphNodes.add(node2);
        graphNodes.add(node3);
        GraphNode result = findNodeData(graphNodes, "node2");
        assertEquals(result, node2);
    }
    */
    @Test
    public void TestFindNodeData(){
        Set<NodeData> testSet = new HashSet<>();
        NodeData node1 = new NodeData("node1", 0, 0, 1,
                "Foisie", "regular", "LongName", "ShortName");
        NodeData node2 = new NodeData("node2", 0, 0, 1,
                "Foisie", "regular", "LongName", "ShortName");
        NodeData node3 = new NodeData("node3", 0, 0, 1,
                "Foisie", "regular", "LongName", "ShortName");
        testSet.add(node1);
        testSet.add(node2);
        testSet.add(node3);
        NodeData result = findNodeData(testSet, "node2");
        assertEquals(result, node2);
    }

    @Test
    public void TestAddNodesToGraph(){
        Set<NodeData> testSet = new HashSet<>();
        NodeData node1 = new NodeData("node1", 0, 0, 1,
                "Foisie", "regular", "LongName", "ShortName");
        NodeData node2 = new NodeData("node2", 0, 0, 1,
                "Foisie", "regular", "LongName", "ShortName");
        NodeData node3 = new NodeData("node3", 0, 0, 1,
                "Foisie", "regular", "LongName", "ShortName");
        testSet.add(node1);
        testSet.add(node2);
        testSet.add(node3);
        MutableGraph<NodeData> testGraph = GraphBuilder.undirected().allowsSelfLoops(true).build();
        applicationInitializer.addNodesToGraph(testSet, testGraph);
        assertEquals(testGraph.nodes(), testSet);
    }

    @Test
    public void TestAddEdgesToGraph(){
        Set<NodeData> testNodeSet = new HashSet<>();
        NodeData node1 = new NodeData("node1", 0, 0, 1,
                "Foisie", "regular", "LongName", "ShortName");
        NodeData node2 = new NodeData("node2", 0, 0, 1,
                "Foisie", "regular", "LongName", "ShortName");
        NodeData node3 = new NodeData("node3", 0, 0, 1,
                "Foisie", "regular", "LongName", "ShortName");
        testNodeSet.add(node1);
        testNodeSet.add(node2);
        testNodeSet.add(node3);
        MutableGraph<NodeData> testGraph = GraphBuilder.undirected().allowsSelfLoops(true).build();
        applicationInitializer.addNodesToGraph(testNodeSet, testGraph);
        Set<EdgeData> testEdgeSet = new HashSet<>();
        EdgeData edge1_2 = new EdgeData("edge1_2", "node1", "node2");
        EdgeData edge2_3 = new EdgeData("edge2_3", "node2", "node3");
        EdgeData edge1_3 = new EdgeData("edge1_3", "node1", "node3");
        testEdgeSet.add(edge1_2);
        testEdgeSet.add(edge2_3);
        testEdgeSet.add(edge1_3);
        applicationInitializer.addEdgesToGraph(testEdgeSet, testGraph);
        EndpointPair<NodeData> node1_node2 = EndpointPair.unordered(node1, node2);
        EndpointPair<NodeData> node2_node3 = EndpointPair.unordered(node2, node3);
        EndpointPair<NodeData> node1_node3 = EndpointPair.unordered(node1, node3);
        Set<EndpointPair> resultEdgeSet = new HashSet<>();
        resultEdgeSet.add(node1_node2);
        resultEdgeSet.add(node2_node3);
        resultEdgeSet.add(node1_node3);
        assertEquals(testGraph.edges(), resultEdgeSet);
    }
}
