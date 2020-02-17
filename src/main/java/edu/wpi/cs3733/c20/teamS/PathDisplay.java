package edu.wpi.cs3733.c20.teamS;

import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import edu.wpi.cs3733.c20.teamS.database.DataClasses.EdgeData;
import edu.wpi.cs3733.c20.teamS.database.DataClasses.NodeData;
import edu.wpi.cs3733.c20.teamS.database.DatabaseController;
import edu.wpi.cs3733.c20.teamS.pathfinding.A_Star;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class PathDisplay {
    private String floornum;
    private int counter = 0;
    private NodeData startNode;
    private NodeData endNode;
    Group group;

    public PathDisplay(Group group, String floornum) {
        this.group = group;
        this.floornum = floornum;
    }

    public void setNode(NodeData data) {
        if(counter % 2 == 0) {startNode = data;}
        if(counter % 2 != 0) {endNode = data;}
        counter++;
    }

    public void pathDraw() {
        if(counter >= 2) {
            boolean sameFloor = startNode.getFloor() == endNode.getFloor();
            DatabaseController dbc = new DatabaseController();
            Set<NodeData> nd = dbc.getAllNodes();

            Set<EdgeData> ed = dbc.getAllEdges();
            MutableGraph<NodeData> graph = GraphBuilder.undirected().build();

            for(NodeData data: nd) {
                if(data.getNodeID().equals(startNode.getNodeID())) {startNode = data;}
                if(data.getNodeID().equals(endNode.getNodeID())) {endNode = data;}
                graph.addNode(data);
            }
            for(EdgeData data: ed) {
                String startID = data.getStartNode();
                String endID = data.getEndNode();
                NodeData start = new NodeData();
                NodeData end = new NodeData();
                for(NodeData data1: nd) {
                    if(startID.equals(data1.getNodeID())) {start = data1;}
                    if(endID.equals(data1.getNodeID())) {end = data1;}
                }
                if(start != null && end != null && !sameFloor) {
                    graph.putEdge(start, end);
                }
                else if(start != null && end != null) {
                    if(start.getNodeType() != "ELEV" && end.getNodeType() != "ELEV") {
                        graph.putEdge(start, end);
                    }
                }
            }
            A_Star please = new A_Star();
            ArrayList<NodeData> work = please.findPath(graph, startNode, endNode);
            for(NodeData mhm : work) {
                System.out.println(mhm.getNodeID());
            }
            for(int i = 0; i < work.size() - 1; i++) {
                EdgeData data = new EdgeData(work.get(i).getNodeID() + "_" + work.get(i + 1).getNodeID(), work.get(i).getNodeID(),work.get(i + 1).getNodeID());
                if(data.getEdgeID().substring(data.getEdgeID().length()-2).equals(floornum)) {
                    String start = data.getStartNode();
                    String end = data.getEndNode();
                    int startX = 0;
                    int startY = 0;
                    int endX = 0;
                    int endY = 0;
                    boolean checker1 = false;
                    boolean checker2 = false;
                    for(NodeData check: nd) {
                        if(check.getNodeID().equals(start)) {
                            checker1 = true;
                            startX = (int)check.getxCoordinate();
                            startY = (int)check.getyCoordinate();
                        }
                        if(check.getNodeID().equals(end)) {
                            checker2 = true;
                            endX = (int)check.getxCoordinate();
                            endY = (int)check.getyCoordinate();
                        }
                    }
                    if(checker1 && checker2) {
                        Line line1 = new Line();
                        line1.setStartX(startX);
                        line1.setStartY(startY);
                        line1.setEndX(endX);
                        line1.setEndY(endY);
                        if(start.substring(start.length() - 2).equals("03")) {
                            line1.setStroke(Color.RED);}
                        else {line1.setStroke(Color.BLACK);}
                        line1.setFill(Color.RED.deriveColor(1, 1, 1, 0.5));
                        line1.setStrokeWidth(10);
                        group.getChildren().add(line1);
                    }
                }
            }
        }
    }
}
