package edu.wpi.cs3733.c20.teamS.applicationInitializer;



import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import com.sun.javafx.css.StyleManager;
import edu.wpi.cs3733.c20.teamS.Editing.EditScreenController;
import edu.wpi.cs3733.c20.teamS.MainStartScreenController;
import edu.wpi.cs3733.c20.teamS.SerialPoller;
import edu.wpi.cs3733.c20.teamS.Settings;
import edu.wpi.cs3733.c20.teamS.database.DatabaseController;
import edu.wpi.cs3733.c20.teamS.database.EdgeData;
import edu.wpi.cs3733.c20.teamS.database.NodeData;
import edu.wpi.cs3733.c20.teamS.pathDisplaying.MainScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.Set;

import static edu.wpi.cs3733.c20.teamS.ThrowHelper.illegalNull;

/**
 * The class is responsible for loading information from database and
 * parsing them to be displayed on UI upon starting the Application
 * Information to load: Nodes, Edges
 * Pasing target: MutableGraph<NodeData>
 */
public class ApplicationInitializer {
    private MutableGraph<NodeData> graph;
    private DatabaseController controller;

    public ApplicationInitializer(DatabaseController controller){
        if(controller == null){
            illegalNull("DatabaseController");
        }
        this.controller = controller;
        this.graph = GraphBuilder.undirected().allowsSelfLoops(true).build();
        SerialPoller.initSensor();

    }

    public MutableGraph<NodeData> graph(){
        return this.graph;
    }

    public DatabaseController controller(){
        return this.controller;
    }

    public void setGraph(MutableGraph<NodeData> graph){
        this.graph = graph;
    }

    public void setDatabaseController(DatabaseController controller){
        this.controller = controller;
    }

    /**
     * Add Nodes to graph
     * @param allNodeData A set of NodeData to be added
     * @param graph A MutableGraph where NodeData are added to
     */
    public static void addNodesToGraph (Set<NodeData> allNodeData, MutableGraph<NodeData> graph){
        if(allNodeData == null){
            illegalNull("Set of NodeData");
        }
        if(graph == null){
            illegalNull("Target Graph");
        }
        for(NodeData nodeData : allNodeData){
            graph.addNode(nodeData);
        }
    }

    /**
     * Helper Function
     * Find a NodeData with specified nodeID within a set of NodeData
     * @param nodeDataSet A set of NodeData
     * @param nodeID nodeID of interest
     * @return null if no NodeData with nodeID of interest is found
     *         NodeData with nodeID of interest
     */
    public static NodeData findNodeData(Set<NodeData> nodeDataSet, String nodeID){
        if(nodeDataSet == null){
            illegalNull("Set of NodeData");
        }
        if(nodeID == null){
            illegalNull("nodeID of Interest");
        }
        NodeData nodeData = null;
        for(NodeData node : nodeDataSet){
            if(node.getNodeID().equals(nodeID)){
                nodeData = node;
            }
        }
        return nodeData;
    }


    /**
     * Add Edges to graph
     * @param allEdges A set of EdgeData to be added to a MutableGraph
     * @param graph A MutableGraph where Edges are added
     */
    public static void addEdgesToGraph(Set<EdgeData> allEdges, MutableGraph<NodeData> graph){
        Set<NodeData> allNodeData = graph.nodes();
        for(EdgeData edge : allEdges){
            String startNodeID = edge.getStartNode();
            String endNodeID = edge.getEndNode();
            NodeData startNode = findNodeData(allNodeData, startNodeID);
            NodeData endNode = findNodeData(allNodeData, endNodeID);
            graph.putEdge(startNode, endNode);
        }
    }

    /**
     * Get all NodeData and EdgeData from database and put them into a MutableGraph
     */
    public void run(){
        Set<NodeData> allNodeData = this.controller.getAllNodes();
        Set<EdgeData> allEdgeData = this.controller.getAllEdges();
        this.addNodesToGraph(allNodeData, this.graph);
        this.addEdgesToGraph(allEdgeData, this.graph);
    }

    public void initBigFXMLs(){
        Settings set = Settings.get();
        set.getPrimaryStage().setFullScreen(true);

        initSplashScreen();
        initMainScreen();
        initEmployeeScreen();
    }

    private void initMainScreen(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/UI_client.fxml"));
        loader.setControllerFactory(c ->{ MainScreenController msc = new MainScreenController(Settings.get().getPrimaryStage());
        Settings.get().setMainScreenController(msc);
        return Settings.get().getMainScreenController();

        });

        try{
            Settings.get().setMainScreenRoot(loader.load());
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    private void initSplashScreen(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/SplashScreen.fxml"));
        loader.setControllerFactory(c -> {
            MainStartScreenController cont = new MainStartScreenController();
            return cont;
        });

        try{
            Settings.get().setSplashRoot(loader.load());
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    private void initEmployeeScreen(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/UI_employee.fxml"));

        loader.setControllerFactory(c -> { EditScreenController msc = new EditScreenController();
            Settings.get().setEditScreenController(msc);
            return Settings.get().getEditScreenController();

        });

        try{
            Settings.get().setEmployeeRoot(loader.load());
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}
