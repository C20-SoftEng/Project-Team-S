package edu.wpi.cs3733.c20.teamS.applicationInitializer;


import com.google.common.graph.Graph;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import edu.wpi.cs3733.c20.teamS.GraphNode;
import edu.wpi.cs3733.c20.teamS.database.DataClasses.EdgeData;
import edu.wpi.cs3733.c20.teamS.database.DataClasses.NodeData;
import edu.wpi.cs3733.c20.teamS.database.DatabaseController;

import java.util.HashSet;
import java.util.Set;

import static edu.wpi.cs3733.c20.teamS.ThrowHelper.illegalNull;

/**
 * The class is responsible for loading information from database and
 * parsing them to be displayed on UI upon starting the Application
 * Information to load: Nodes, Edges
 * Pasing target: MutableGraph<NodeData>
 */
public class applicationInitializer {
    private MutableGraph<GraphNode> graph;
    private DatabaseController controller;

    public applicationInitializer(DatabaseController controller){
        if(controller == null){
            illegalNull("DatabaseController");
        }
        this.controller = controller;
        this.graph = GraphBuilder.undirected().allowsSelfLoops(true).build();
    }

    public MutableGraph<GraphNode> graph(){
        return this.graph;
    }

    public DatabaseController controller(){
        return this.controller;
    }

    public void setGraph(MutableGraph<GraphNode> graph){
        this.graph = graph;
    }

    public void setDatabaseController(DatabaseController controller){
        this.controller = controller;
    }
    /**
     * Helper Method
     * Convert NodeData to GraphNode
     * @param nodeData The NodeData to be converted
     * @return A GraphNode after conversion
     */
    public static GraphNode toGraphNode(NodeData nodeData){
        if(nodeData == null){
            illegalNull("NodeData");
        }
        GraphNode graphNode = new GraphNode(nodeData.getNodeID(), nodeData.getxCoordinate(),
                nodeData.getyCoordinate(), nodeData.getFloor(), nodeData.getBuilding(),
                nodeData.getNodeType(), nodeData.getLongName(), nodeData.getShortName());
        return graphNode;
    }

    /**
     * Convert a set of NodeData to a set of GraphNode
     * @param nodes A set of NodeData to be converted
     * @return A set of GraphNodes after conversion
     */
    public Set<GraphNode> toSetGraphNode(Set<NodeData> nodes){
        if (nodes == null){
            illegalNull("Set of NodeData");
        }
        Set<GraphNode> graphNodes = new HashSet<>();
        for(NodeData node : nodes){
            GraphNode graphNode = this.toGraphNode(node);
            graphNodes.add(graphNode);
        }
        return graphNodes;
    }

    /**
     * Add Nodes to graph
     * @param allGraphNode A set of GraphNode to be added
     * @param graph A MutableGraph where GraphNodes are added to
     */
    public void addNodesToGraph (Set<GraphNode> allGraphNode, MutableGraph<GraphNode> graph){
        if(allGraphNode == null){
            illegalNull("Set of GraphNode");
        }
        if(graph == null){
            illegalNull("Target Graph");
        }
        for(GraphNode graphNode : allGraphNode){
            graph.addNode(graphNode);
        }
    }

    /**
     * Helper Function
     * Find a GraphNode with specified nodeID within a set of GraphNodes
     * @param graphNodes A set of GraphNodes
     * @param nodeID nodeID of interest
     * @return null if no GraphNode with nodeID of interest is found
     *         GraphNode with nodeID of interest
     */
    public static GraphNode findGraphNode(Set<GraphNode> graphNodes, String nodeID){
        if(graphNodes == null){
            illegalNull("Set of GraphNodes");
        }
        if(nodeID == null){
            illegalNull("nodeID of Interest");
        }
        GraphNode graphNode = null;
        for(GraphNode node : graphNodes){
            if(node.nodeID().equals(nodeID)){
                graphNode = node;
            }
        }
        return graphNode;
    }


    /**
     * Add Edges to graph
     * @param allEdges A set of EdgeData to be added to a MutableGraph
     * @param graph A MutableGraph where Edges are added
     */
    public void addEdgesToGraph(Set<EdgeData> allEdges, MutableGraph<GraphNode> graph){
        Set<GraphNode> allGraphNodes = graph.nodes();
        for(EdgeData edge : allEdges){
            String startNodeID = edge.getStartNode();
            String endNodeID = edge.getEndNode();
            GraphNode startNode = findGraphNode(allGraphNodes, startNodeID);
            GraphNode endNode = findGraphNode(allGraphNodes, endNodeID);
            graph.putEdge(startNode, endNode);
        }
    }
}
