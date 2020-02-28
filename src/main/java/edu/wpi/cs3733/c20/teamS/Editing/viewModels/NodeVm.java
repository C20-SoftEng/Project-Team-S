package edu.wpi.cs3733.c20.teamS.Editing.viewModels;

import edu.wpi.cs3733.c20.teamS.database.NodeData;
import javafx.scene.Parent;
import javafx.scene.shape.Circle;

public class NodeVm extends Parent {
    private final NodeData node;

    public NodeVm(NodeData node) {
        this.node = node;
        Circle mainMask = new Circle();
        mainMask.setCenterX(node.getxCoordinate());
        mainMask.setCenterY(node.getyCoordinate());
        mainMask.setRadius(20);
        getChildren().add(mainMask);
    }
}
