package edu.wpi.cs3733.c20.teamS.Editing.viewModels;

import edu.wpi.cs3733.c20.teamS.Settings;
import edu.wpi.cs3733.c20.teamS.database.NodeData;
import javafx.scene.Parent;

public final class NodeVm extends Parent {
    private final NodeData node;
    private final HighlightCircle mask;

    public NodeVm(NodeData node) {
        this.node = node;
        setTranslateX(node.getxCoordinate());
        setTranslateY(node.getyCoordinate());

        mask = new HighlightCircle(12, 20);
        mask.setNormalFillColor(Settings.get().nodeFillColorNormal());
        mask.setHighlightFillColor(Settings.get().nodeFillColorHighlight());
        mask.setNormalStrokeColor(Settings.get().nodeStrokeColorNormal());
        mask.setHighlightStrokeColor(Settings.get().nodeFillColorHighlight());
        getChildren().add(mask);

        node.positionChanged().subscribe(n -> updatePosition());
    }

    public NodeData node() {
        return node;
    }

    private void updatePosition() {
        setTranslateX(node.getxCoordinate());
        setTranslateY(node.getyCoordinate());
    }
}
