package edu.wpi.cs3733.c20.teamS.Editing;

import edu.wpi.cs3733.c20.teamS.database.DatabaseController;
import edu.wpi.cs3733.c20.teamS.database.NodeData;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import org.apache.derby.iapi.db.Database;

import java.util.Set;

class MoveNodes {

    private double mouseX;
    private double mouseY;

    private double moveX;
    private double moveY;

    private Group group;
    private Double scale;

    public MoveNodes() {}

    public void setGroup(Group group) {
        this.group = group;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public EventHandler<MouseEvent> getOnMousePressedEventHandler() {
        return onMousePressedEventHandler;
    }

    public EventHandler<MouseEvent> getOnMouseDraggedEventHandler() {
        return onMouseDraggedEventHandler;
    }

    public EventHandler<MouseEvent> getOnMouseDragReleasedEventHandler() {
        return onMouseDragReleasedEventHandler;
    }

    private EventHandler<MouseEvent> onMousePressedEventHandler = new EventHandler<MouseEvent>() {

        public void handle(MouseEvent event) {

            // left mouse button => dragging
            if( !event.isPrimaryButtonDown())
                return;

            mouseX = event.getSceneX();
            mouseY = event.getSceneY();

            Node node = (Node) event.getSource();

            moveX = node.getTranslateX();
            moveY = node.getTranslateY();
        }

    };

    private EventHandler<MouseEvent> onMouseDraggedEventHandler = new EventHandler<MouseEvent>() {
        public void handle(MouseEvent event) {

            // left mouse button => dragging
            if( !event.isPrimaryButtonDown())
                return;

            Node node = (Node) event.getSource();

            node.setTranslateX(moveX + (( event.getSceneX() - mouseX) / scale));
            node.setTranslateY(moveY + (( event.getSceneY() - mouseY) / scale));

            event.consume();

        }
    };

    private EventHandler<MouseEvent> onMouseDragReleasedEventHandler = new EventHandler<MouseEvent>() {
        public void handle(MouseEvent event) {
            NodeData data = findNearestNode(event.getX(), event.getY());
            DatabaseController dbc = new DatabaseController();
            dbc.removeNode(data.getNodeID());
            data.setxCoordinate(data.getxCoordinate() + (moveX + (( event.getSceneX() - mouseX) / scale)));
            data.setyCoordinate(data.getyCoordinate() + (moveY + (( event.getSceneY() - mouseY) / scale)));
            dbc.addNode(data);
        }
    };

    private NodeData findNearestNode(double x, double y) {
        NodeData nearest = new NodeData();
        double distance = 200;

        DatabaseController dbc = new DatabaseController();
        Set<NodeData> nd = dbc.getAllNodes();

        for(NodeData temp : nd) {
            if(temp.getFloor() == 2) {
                if (Math.sqrt(Math.pow((x - temp.getxCoordinate()), 2) + Math.pow((y - temp.getyCoordinate()), 2)) < distance) {
                    distance = Math.sqrt(Math.pow((x - temp.getxCoordinate()), 2) + Math.pow((y - temp.getyCoordinate()), 2));
                    nearest = temp;
                }
            }
        }
        return nearest;
    }
}