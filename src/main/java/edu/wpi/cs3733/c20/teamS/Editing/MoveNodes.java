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

    private String nodeID;

    public MoveNodes() {}

    public void setGroup(Group group) {
        this.group = group;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public void setNodeID(String nodeID) {
        this.nodeID = nodeID;
    }

    public EventHandler<MouseEvent> getOnMousePressedEventHandler() {
        return onMousePressedEventHandler;
    }

    public EventHandler<MouseEvent> getOnMouseDraggedEventHandler() {
        return onMouseDraggedEventHandler;
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

            DatabaseController dbc = new DatabaseController();
            Set<NodeData> nd = dbc.getAllNodes();
            NodeData temp = new NodeData();
            for(NodeData data : nd) {
                if(data.getNodeID().equals(nodeID)) {
                    System.out.println(nodeID);
                    dbc.removeNode(nodeID);
                    temp.setNodeID(data.getNodeID());
                }
            }

            temp.setxCoordinate(1000);
            temp.setyCoordinate(1000);
            dbc.addNode(temp);


            event.consume();

        }
    };
}