package edu.wpi.cs3733.c20.teamS.applicationInitializer;
import edu.wpi.cs3733.c20.teamS.GraphNode;
import edu.wpi.cs3733.c20.teamS.database.DataClasses.NodeData;
import edu.wpi.cs3733.c20.teamS.database.DatabaseController;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static edu.wpi.cs3733.c20.teamS.applicationInitializer.applicationInitializer.findGraphNode;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class applicationInitializerTest {
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
        GraphNode result = findGraphNode(graphNodes, "node2");
        assertEquals(result, node2);
    }

    
}
