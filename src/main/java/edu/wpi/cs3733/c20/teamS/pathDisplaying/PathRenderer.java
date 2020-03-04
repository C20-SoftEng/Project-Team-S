package edu.wpi.cs3733.c20.teamS.pathDisplaying;

import com.jfoenix.controls.JFXTextArea;
import edu.wpi.cs3733.c20.teamS.Settings;
import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import edu.wpi.cs3733.c20.teamS.database.NodeData;
import edu.wpi.cs3733.c20.teamS.pathfinding.WrittenInstructions;
import edu.wpi.cs3733.c20.teamS.utilities.Board;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.util.List;
import java.util.stream.Collectors;

//import edu.wpi.cs3733.c20.teamS.pathfinding.Path;

class PathRenderer {

    /**
     * Draws the portion of the specified path that is on the specified floor, then
     * returns a new Group containing all the elements that were drawn.
     *
     * @param path  The path to draw.
     * @param floor The floor that is being rendered.
     * @return A new Group containing all the elements that were drawn.
     */
    private List<NodeData> TDnodes;
    public Group draw(edu.wpi.cs3733.c20.teamS.pathfinding.Path path, int floor) {
        if (path == null) ThrowHelper.illegalNull("path");

        Group group = new Group();
        if (path.isEmpty())
            return group;

        List<NodeData> nodes = path.startToFinish();
        TDnodes = nodes;
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
                .map(node -> down ? drawDownElevator(node, start, end, floor) : drawUpElevator(node, start, end, floor))
                .forEach(image -> group.getChildren().add(image));

        boolean runOnce = true;
        double length = 0;
        Path animated_path = new Path();
        double X = start.getxCoordinate();
        double Y = start.getyCoordinate();

        Image i = new Image("images/Icons/outlined_arrow.png");
        ImageView imageView = new ImageView();
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(50);
        imageView.setImage(i);
        imageView.setVisible(false);

        for (NodeData node_itrat : nodes) {
            if((node_itrat.getFloor() == floor) && runOnce){
                animated_path = new Path();
                animated_path.getElements().add(new MoveTo(node_itrat.getxCoordinate(), node_itrat.getyCoordinate()));
                runOnce = false;
            }

            if (node_itrat.getFloor() == floor) {
                animated_path.getElements().add(new LineTo(node_itrat.getxCoordinate(), node_itrat.getyCoordinate()));
                length += Math.sqrt(Math.pow((Math.abs(X - node_itrat.getxCoordinate())), 2) + Math.pow((Math.abs(Y - node_itrat.getyCoordinate())),2));
                X = node_itrat.getxCoordinate();
                Y = node_itrat.getyCoordinate();
                imageView.setVisible(true);
            }
        }

        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.seconds(getPathTime(length)));
        pathTransition.setPath(animated_path);
        pathTransition.setNode(imageView);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setCycleCount(Timeline.INDEFINITE);
        //pathTransition.setAutoReverse(true); //enable this if you want for reverse for some reason
        pathTransition.play();
        group.getChildren().add(imageView);
        return group;
    }

    public List<NodeData> getTDnodes() {return TDnodes;}

    private double getPathTime(double lengthOfPath){
        return lengthOfPath/250;
    }
    /**
     * Displays the instructions for the specified Path in the specified VBox.
     *  @param path       The path to display instructions for.
     * @param directoryBox The VBox to display the directory
     * @param displayBox The VBox to display the instructions in.
     */
    public void printInstructions(edu.wpi.cs3733.c20.teamS.pathfinding.Path path, VBox displayBox,  VBox directoryBox) {
        if (path == null) ThrowHelper.illegalNull("path");
        if (displayBox == null) ThrowHelper.illegalNull("displayBox");
        if (directoryBox == null) ThrowHelper.illegalNull("directoryBox");

        List<NodeData> nodes = path.startToFinish();
        WrittenInstructions instructionWriter = new WrittenInstructions(nodes);
        List<String> instructions = instructionWriter.directions();
        displayBox.setStyle("-fx-text-fill: black");
        displayBox.getChildren().clear();



        //JFXTextField directionLabel = new JFXTextField();
        //directionLabel.setText("Directions");
        //JFXTextField space = new JFXTextField();
        //directoryBox.setVisible(false);

        for (int i = 0; i < instructions.size(); i++) {
            HBox imageHolder = new HBox();
            imageHolder.setStyle("-fx-background-color: #CCC");
            JFXTextArea text = new JFXTextArea();
            text.setText(" " + instructions.get(i));
            text.setStyle(("-fx-background-color: #CCC"));
            ImageView image = new ImageView();
            image.setStyle("-fx-background-color: #CCC");

            String word = instructions.get(i).trim();

            if(word.contains("Left")){
                Image newImage = new Image(String.valueOf(getClass().getResource("/images/Instructions/leftTurn2.png")));
                image.setImage(newImage);

                image.setFitHeight(25);
                image.setFitWidth(25);
//                image.setX(50);
//                image.setY(-20);
                image.setTranslateX(10);
                image.setTranslateY(4);


                image.setPreserveRatio(true);
            }
            else if(word.contains("Right")){
                Image newImage = new Image(String.valueOf(getClass().getResource("/images/Instructions/rightTurn2.png")));
                image.setImage(newImage);
                image.setFitHeight(25);
                image.setFitWidth(25);
                image.setTranslateX(15);
                image.setTranslateY(4);

                image.setPreserveRatio(true);
            }
            else if(word.contains("straight")){
                Image newImage = new Image(String.valueOf(getClass().getResource("/images/Instructions/up-straight-arrow.png")));
                image.setImage(newImage);
                image.setFitHeight(25);
                image.setFitWidth(25);
                image.setTranslateX(15);
                image.setTranslateY(4);

                image.setPreserveRatio(true);

            }
            else if(word.contains("Elevator")){
                Image newImage = new Image(String.valueOf(getClass().getResource("/images/Instructions/elevator-2.png")));
                image.setImage(newImage);
                image.setFitHeight(25);
                image.setFitWidth(25);
                image.setTranslateX(23);
                image.setTranslateY(5);
                image.setPreserveRatio(false);
            }
            else {
                System.out.println("in else statement");
                if(word.contains("Floor 1")){
                    Image newImage = new Image(String.valueOf(getClass().getResource("/images/Instructions/one.png")));
                    image.setImage(newImage);
                    image.setFitHeight(30);
                    image.setFitWidth(30);
                    image.setTranslateX(0);
                    image.setTranslateY(5);
                    image.setPreserveRatio(false);
                }
                if(word.contains("Floor 2")){
                    Image newImage = new Image(String.valueOf(getClass().getResource("/images/Instructions/two.png")));
                    image.setImage(newImage);
                    image.setFitHeight(30);
                    image.setFitWidth(30);
                    image.setTranslateX(0);
                    image.setTranslateY(5);
                    image.setPreserveRatio(false);
                }
                if(word.contains("Floor 3")){
                    Image newImage = new Image(String.valueOf(getClass().getResource("/images/Instructions/three.png")));
                    image.setImage(newImage);
                    image.setFitHeight(30);
                    image.setFitWidth(30);
                    image.setTranslateX(0);
                    image.setTranslateY(5);
                    image.setPreserveRatio(false);
                }
                if(word.contains("Floor 4")){
                    Image newImage = new Image(String.valueOf(getClass().getResource("/images/Instructions/four.png")));
                    image.setImage(newImage);
                    image.setFitHeight(30);
                    image.setFitWidth(30);
                    image.setTranslateX(0);
                    image.setTranslateY(5);
                    text.setTranslateX(25);
                    image.setPreserveRatio(false);
                }
                if(word.contains("Floor 5")){
                    Image newImage = new Image(String.valueOf(getClass().getResource("/images/Instructions/five.png")));
                    image.setImage(newImage);
                    image.setFitHeight(30);
                    image.setFitWidth(30);
                    image.setTranslateX(0);
                    image.setTranslateY(5);
                    image.setPreserveRatio(false);
                }
                if(word.contains("Floor 6")){
                    Image newImage = new Image(String.valueOf(getClass().getResource("/images/Instructions/six.png")));
                    image.setImage(newImage);
                    image.setFitHeight(30);
                    image.setFitWidth(30);
                    image.setTranslateX(0);
                    image.setTranslateY(5);
                    image.setPreserveRatio(false);
                }
                if(word.contains("Floor 7")){
                    Image newImage = new Image(String.valueOf(getClass().getResource("/images/Instructions/seven.png")));
                    image.setImage(newImage);
                    image.setFitHeight(30);
                    image.setFitWidth(30);
                    image.setTranslateX(0);
                    image.setTranslateY(5);
                    image.setPreserveRatio(false);
                }

            }
            text.setEditable(false);
            if (text.getLength() > 27){
                text.setFont(Font.font ("System", 15));
                text.setPrefHeight(30);
                text.setPrefWidth(250);
               text.setTranslateX(30);

            }
            else if (text.getLength() >= 22){
                text.setFont(Font.font ("System", 15));
                text.setPrefHeight(30);
                text.setPrefWidth(250);

                text.setTranslateX(30);

            }
            else {
                text.setFont(Font.font ("System", 15));
                text.setPrefHeight(30);
                text.setPrefWidth(250);
                //text.setPref
                text.setTranslateX(10);

            }
            text.prefWidth(Region.USE_COMPUTED_SIZE);
            imageHolder.getChildren().add(image);
            imageHolder.getChildren().add(text);
            displayBox.getChildren().add(imageHolder);

            displayBox.setVisible(true);

            directoryBox.setVisible(false);
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
        line.setStroke(Settings.get().pathColor());
        line.setFill(
                Settings.get().pathColor()
                .deriveColor(1, 1, 1, 0.5));
        line.setStrokeWidth(10);

        return line;
    }

    private static Circle drawStartCircle(NodeData node) {
        Circle circle = new Circle(node.getxCoordinate(), node.getyCoordinate(), 15);
        circle.setFill(Settings.get().pathColor());
        return circle;
    }

    private static ImageView drawEndBalloon(NodeData node) {
        ImageView pinIcon = new ImageView();
        pinIcon.setImage(new Image("images/Icons/end_pin.png"));
        pinIcon.setX(node.getxCoordinate() - 20);
        pinIcon.setY(node.getyCoordinate() - 60);
        pinIcon.setPreserveRatio(true);
        pinIcon.setFitWidth(40);
        return pinIcon;
    }

    private ImageView drawDownElevator(NodeData node2, NodeData startNode, NodeData endNode, int floor) {
        if((node2 != startNode) && (node2 != endNode) && (node2.getFloor() == floor)) {
            ImageView elevator_icon_down = new ImageView();
            elevator_icon_down.setImage(new Image("images/Balloons/greeeeeeeeen.gif"));
            elevator_icon_down.setX(node2.getxCoordinate() - 50);
            elevator_icon_down.setY(node2.getyCoordinate() - 40);
            elevator_icon_down.setPreserveRatio(true);
            elevator_icon_down.setFitWidth(80);
            elevator_icon_down.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    MainScreenController.floorSelector.setCurrent(endNode.getFloor());
                }
            });
            return elevator_icon_down;
        }else
            return new ImageView();
    }

    private ImageView drawUpElevator(NodeData node2, NodeData startNode, NodeData endNode, int floor) {
        if((node2 != startNode) && (node2 != endNode && (node2.getFloor() == floor))) {
            ImageView elevator_icon_up = new ImageView();
            elevator_icon_up.setImage(new Image("images/Balloons/greeeeeeeeen.gif"));
            elevator_icon_up.setX(node2.getxCoordinate() - 50);
            elevator_icon_up.setY(node2.getyCoordinate() - 40);
            elevator_icon_up.setPreserveRatio(true);
            elevator_icon_up.setFitWidth(80);
            elevator_icon_up.setRotate(180);
            elevator_icon_up.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    MainScreenController.floorSelector.setCurrent(endNode.getFloor());
                }
            });
            return elevator_icon_up;
        }else
            return new ImageView();
    }
}


