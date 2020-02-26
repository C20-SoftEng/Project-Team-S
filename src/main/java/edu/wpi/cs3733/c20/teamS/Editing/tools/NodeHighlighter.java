package edu.wpi.cs3733.c20.teamS.Editing.tools;

import edu.wpi.cs3733.c20.teamS.database.NodeData;

/**
 * A function to set the highlight state of a node on a UI.
 */
@FunctionalInterface
public interface NodeHighlighter {
    public void setHighlight(NodeData node, boolean highlight);
}
