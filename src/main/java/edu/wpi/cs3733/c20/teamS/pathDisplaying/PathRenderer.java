package edu.wpi.cs3733.c20.teamS.pathDisplaying;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import edu.wpi.cs3733.c20.teamS.database.NodeData;
//import edu.wpi.cs3733.c20.teamS.pathfinding.Path;
import edu.wpi.cs3733.c20.teamS.pathfinding.WrittenInstructions;
import edu.wpi.cs3733.c20.teamS.utilities.Board;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.Duration;

import java.util.List;
import java.util.stream.Collectors;

class PathRenderer {

    /**
     * Draws the portion of the specified path that is on the specified floor, then
     * returns a new Group containing all the elements that were drawn.
     *
     * @param path  The path to draw.
     * @param floor The floor that is being rendered.
     * @return A new Group containing all the elements that were drawn.
     */
    public Group draw(edu.wpi.cs3733.c20.teamS.pathfinding.Path path, int floor) {
        if (path == null) ThrowHelper.illegalNull("path");

        Group group = new Group();
        if (path.isEmpty())
            return group;

        List<NodeData> nodes = path.startToFinish();
        List<Line> lines = findCompleteEdges(nodes).stream()
                .filter(edge -> edge.isOnFloor(floor))
                .map(PathRenderer::drawEdge)
                .collect(Collectors.toList());
        group.getChildren().addAll(lines);

        NodeData start = nodes.get(0);
        if (start.getFloor() == floor) {
            Circle startCircle = drawStartCircle(start);
            group.getChildren().add(startCircle);
        }
        NodeData end = nodes.get(nodes.size() - 1);
        if (end.getFloor() == floor) {
            ImageView endBalloon = drawEndBalloon(end);
            group.getChildren().add(endBalloon);
        }
        boolean down = start.getFloor() > end.getFloor();
        nodes.stream()
                .filter(node -> node.getNodeType().equals("ELEV"))
                .map(node -> down ? drawDownElevator(node) : drawUpElevator(node))
                .forEach(image -> group.getChildren().add(image));

        boolean runOnce = true;
        Path animated_path = new Path();

        for (NodeData node_itrat : nodes) {
            if((node_itrat.getFloor() == floor) && runOnce){
                animated_path = new Path();
                animated_path.getElements().add(new MoveTo(node_itrat.getxCoordinate(), node_itrat.getyCoordinate()));
                runOnce = false;
            }

            if (node_itrat.getFloor() == floor) {
                animated_path.getElements().add(new LineTo(node_itrat.getxCoordinate(), node_itrat.getyCoordinate()));
            }
        }

        Image i = new Image("images/Icons/outlined_arrow.png");
        ImageView imageView = new ImageView();
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(50);
        imageView.setImage(i);

        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.seconds(7.5));
        pathTransition.setPath(animated_path);
        pathTransition.setNode(imageView);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setCycleCount(Timeline.INDEFINITE);
        //pathTransition.setAutoReverse(true);
        pathTransition.play();
        group.getChildren().add(imageView);

        return group;
    }

    /**
     * Displays the instructions for the specified Path in the specified VBox.
     *
     * @param path       The path to display instructions for.
     * @param displayBox The VBox to display the instructions in.
     */
    public void printInstructions(edu.wpi.cs3733.c20.teamS.pathfinding.Path path, VBox displayBox) {
        if (path == null) ThrowHelper.illegalNull("path");
        if (displayBox == null) ThrowHelper.illegalNull("displayBox");

        List<NodeData> nodes = path.startToFinish();
        WrittenInstructions instructionWriter = new WrittenInstructions(nodes);
        List<String> instructions = instructionWriter.directions();
        int offset = 30;
        displayBox.getChildren().clear();
        JFXTextField directionLabel = new JFXTextField();
        directionLabel.setText("Directions");
        JFXTextField space = new JFXTextField();
        for (String direct : instructions) {
            JFXTextArea text = new JFXTextArea();
            text.setText(direct);
            text.setPrefHeight(offset);
            displayBox.getChildren().add(text);
        }
    }

    private static final class Edge {
        public final NodeData start;
        public final NodeData end;

        public Edge(NodeData start, NodeData end) {
            this.start = start;
            this.end = end;
        }

        public boolean isComplete() {
            return start != null && end != null;
        }

        public boolean isOnFloor(int floor) {
            if (!isComplete())
                return false;

            return start.getFloor() == floor || end.getFloor() == floor;
        }
    }

    private static List<Edge> findCompleteEdges(Iterable<NodeData> nodes) {
        return Board.asList(nodes).stream()
                .map(board -> new Edge(board.start(), board.end()))
                .collect(Collectors.toList());
    }

    private static Line drawEdge(Edge edge) {
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

    private static Circle drawStartCircle(NodeData node) {
        Circle circle = new Circle(node.getxCoordinate(), node.getyCoordinate(), 15);
        circle.setFill(Color.RED);
        return circle;
    }

    private static ImageView drawEndBalloon(NodeData node) {
        ImageView pinIcon = new ImageView();
        pinIcon.setImage(new Image("images/Icons/pin.png"));
        pinIcon.setX(node.getxCoordinate() - 20);
        pinIcon.setY(node.getyCoordinate() - 60);
        pinIcon.setPreserveRatio(true);
        pinIcon.setFitWidth(40);
        return pinIcon;
    }

    private ImageView drawDownElevator(NodeData node2) {
        ImageView elevator_icon_down = new ImageView();
        elevator_icon_down.setImage(new Image("images/Balloons/down_arrow.gif"));
        elevator_icon_down.setX(node2.getxCoordinate() - 25);
        elevator_icon_down.setY(node2.getyCoordinate() - 20);
        elevator_icon_down.setPreserveRatio(true);
        elevator_icon_down.setFitWidth(40);
        return elevator_icon_down;
    }

    private ImageView drawUpElevator(NodeData node2) {
        ImageView result = drawDownElevator(node2);
        result.setRotate(180);
        return result;
    }
}


