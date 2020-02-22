package edu.wpi.cs3733.c20.teamS.Editing;

import edu.wpi.cs3733.c20.teamS.Editing.tools.GraphEditor;
import edu.wpi.cs3733.c20.teamS.Editing.tools.IEditingTool;

class AddNodeTool implements IEditingTool {
    private final GraphEditor graph;

    AddNodeTool(GraphEditor graph) {
        this.graph = graph;
    }

    @Override
    public final void onMapClicked(double x, double y) {

    }
}
