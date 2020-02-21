package edu.wpi.cs3733.c20.teamS.pathDisplaying;

import com.google.common.graph.MutableGraph;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.c20.teamS.database.EdgeData;
import edu.wpi.cs3733.c20.teamS.database.NodeData;
import edu.wpi.cs3733.c20.teamS.pathfinding.*;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.*;
import java.util.stream.Collectors;

public class PathDisplay {
    private String floornum;
    private String floornum2;
    private int counter = 0;
    private NodeData startNode;
    private NodeData endNode;
    private Group groupPath = new Group();
    private Group group;
    private IPathfinding algorithm;
    private VBox parentVBox;

    public int getCounter() {return counter;}

    public PathDisplay(Group group, VBox parentVBox, IPathfinding pathfinding) {
        this.group = group;
        this.parentVBox = parentVBox;
        this.algorithm = pathfinding;
    }

    public void setNode(NodeData data) {
        if(counter % 2 == 0) {startNode = data; floornum = "0" + data.getFloor();}
        if(counter % 2 != 0) {endNode = data; floornum2 = "0" + data.getFloor();}
        counter++;
    }

    private static final class Edge {
        public final NodeData start;
        public final NodeData end;

        private Edge() {
            this(null, null);
        }
        private Edge(NodeData start, NodeData end) {
            this.start = start;
            this.end = end;
        }
        public static Edge empty() {
            return new Edge();
        }
        public Edge next(NodeData node) {
            if (start == null)
                return new Edge(node, null);
            if (end == null)
                return new Edge(start, node);

            return new Edge(this.end, node);
        }
        public boolean isComplete() {
            return start != null && end != null;
        }
        public boolean isElevator() {
            return isComplete() &&
                    start.getFloor() != end.getFloor();
        }
        public boolean isOnFloor(int floor) {
            if (!isComplete())
                return false;

            return start.getFloor() == floor || end.getFloor() == floor;
        }
    }

    private static List<Edge> findCompleteEdges(Iterable<NodeData> nodes) {
        Edge edge = Edge.empty();
        List<Edge> result = new ArrayList<>();
        for (NodeData node : nodes) {
            edge = edge.next(node);
            if (edge.isComplete())
                result.add(edge);
        }

        return result;
    }

    public void pathDraw(MutableGraph<NodeData> graph, int currentFloor) {
        int first = 1;
        String cf = "0" + currentFloor;
        group.getChildren().remove(groupPath);
        groupPath.getChildren().clear();

        if (startNode == null || endNode == null)
            return;

        PathingContext pathingContext = new PathingContext(this.algorithm);
        Path path = pathingContext.executePathfind(graph, startNode, endNode);
        List<NodeData> nodes = path.startToFinish();
        List<Edge> edges = findCompleteEdges(nodes);
        populateWrittenInstructions(nodes);
        List<Line> drawnEdges = edges.stream()
                .filter(edge -> edge.isOnFloor(currentFloor))
                .map(this::drawEdge)
                .collect(Collectors.toList());
        groupPath.getChildren().addAll(drawnEdges);

        if (startNode.getFloor() == currentFloor) {
            Circle startCircle = drawStartCircle(startNode);
            groupPath.getChildren().add(startCircle);
        }

        if (endNode.getFloor() == currentFloor) {
            ImageView endBalloon = drawEndBalloon(endNode);
            groupPath.getChildren().add(endBalloon);
        }

        group.getChildren().add(groupPath);
//        if(counter >= 2) {
//            PathingContext pathContext = new PathingContext(this.algorithm);
//            Path path = pathContext.executePathfind(graph, startNode, endNode);
//            List<NodeData> work = path.startToFinish();
//
//            for(int i = 0; i < work.size() - 1; i++) {
//                EdgeData data = new EdgeData(work.get(i).getNodeID() +
//                        "_" + work.get(i + 1).getNodeID(), work.get(i).getNodeID(),work.get(i + 1).getNodeID());
//                if(data.getEdgeID().substring(data.getEdgeID().length()-2).equals(cf)) {
//                    String start = data.getStartNode();
//                    String end = data.getEndNode();
//                    int startX = 0;
//                    int startY = 0;
//                    int endX = 0;
//                    int endY = 0;
//                    boolean checker1 = false;
//                    boolean checker2 = false;
//                    for(NodeData check: nd) {
//                        if(check.getNodeID().equals(start)) {
//                            checker1 = true;
//                            startX = (int)check.getxCoordinate();
//                            startY = (int)check.getyCoordinate();
//                        }
//                        if(check.getNodeID().equals(end)) {
//                            checker2 = true;
//                            endX = (int)check.getxCoordinate();
//                            endY = (int)check.getyCoordinate();
//                        }
//                    }
//                    if(checker1 && checker2) {
//
//                        groupPath.getChildren().add(line1);
//                        if(first == 1) {
//                            if(startNode.getFloor() == currentFloor) {
//                            Circle circle = new Circle (startNode.getxCoordinate(),startNode.getyCoordinate(),15);
//                            circle.setFill(Color.RED);
//                            groupPath.getChildren().add(circle);}
//
//                            if(endNode.getFloor() == currentFloor) {
//                                ImageView pinIcon = drawEndBalloon();
//                                groupPath.getChildren().add(pinIcon);
//                            }
//                            first = 2;
//                        }
//                    }
//                }
//            }

//            group.getChildren().add(groupPath);
//        }
    }

    private ImageView drawEndBalloon(NodeData node) {
        ImageView pinIcon = new ImageView();
        pinIcon.setImage(new Image("images/Icons/pin.png"));
        pinIcon.setX(endNode.getxCoordinate() - 20);
        pinIcon.setY(endNode.getyCoordinate() - 60);
        pinIcon.setPreserveRatio(true);
        pinIcon.setFitWidth(40);
        return pinIcon;
    }

    private Line drawEdge(Edge edge) {
        Line line = new Line();
        line.setStartX(edge.start.getxCoordinate());
        line.setStartY(edge.start.getyCoordinate());
        line.setEndX(edge.end.getxCoordinate());
        line.setEndY(edge.end.getyCoordinate());
        line.setStroke(Color.RED);
        line.setFill(Color.RED.deriveColor(1, 1, 1, 0.5));
        line.setStrokeWidth(10);

        return line;
    }
    private Circle drawStartCircle(NodeData node) {
        Circle circle = new Circle(node.getxCoordinate(), node.getyCoordinate(),15);
        circle.setFill(Color.RED);
        return circle;
    }

    private void populateWrittenInstructions(List<NodeData> work) {
        WrittenInstructions directions = new WrittenInstructions(work);
        List<String> words = directions.directions();
        System.out.println(words.size());
        int offset = 30;
        parentVBox.getChildren().clear();
        JFXTextField directionLabel = new JFXTextField();
        directionLabel.setText("Directions");
        JFXTextField space = new JFXTextField();
        for(String direct : words) {
            JFXTextArea text = new JFXTextArea();
            text.setText(direct);
            text.setPrefHeight(offset);
            parentVBox.getChildren().add(text);
        }
    }
}
