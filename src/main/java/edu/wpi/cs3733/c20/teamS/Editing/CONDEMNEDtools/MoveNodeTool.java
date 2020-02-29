package edu.wpi.cs3733.c20.teamS.Editing.CONDEMNEDtools;

import edu.wpi.cs3733.c20.teamS.database.NodeData;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;


public final class MoveNodeTool implements IEditingTool {
    private final ScrollPane scrollPane;

    public MoveNodeTool(ScrollPane scrollPane) {
        this.scrollPane = scrollPane;
    }

    @Override
    public void onNodeDragged(NodeData node, MouseEvent e) {
        scrollPane.setPannable(false);
        node.setPosition(e.getX(), e.getY());
    }

    @Override
    public void onNodeReleased(NodeData node, MouseEvent e) {
        scrollPane.setPannable(true);
    }
}
