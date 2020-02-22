package edu.wpi.cs3733.c20.teamS.Editing.tools;

import edu.wpi.cs3733.c20.teamS.database.NodeData;
import org.checkerframework.framework.qual.NoDefaultQualifierForUse;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public final class AddEdgeTool implements IEditingTool {
    private final GraphEditor graph;
    private State state;

    public AddEdgeTool(GraphEditor graph) {
        this.graph = graph;
        this.state = new StandbyState();
    }

    @Override
    public void onNodeClicked(NodeData node) {
        state.onNodeClicked(node);
    }

    private abstract class State {
        public abstract void onNodeClicked(NodeData node);
    }

    private final class StandbyState extends State {
        @Override
        public void onNodeClicked(NodeData node) {
            AddEdgeTool.this.state = new StartNodeSelectedState(node);
        }
    }

    private final class StartNodeSelectedState extends State {
        private final NodeData start;

        public StartNodeSelectedState(NodeData start) {
            this.start = start;
        }
        @Override
        public void onNodeClicked(NodeData node) {
            graph.putEdge(start, node);
            AddEdgeTool.this.state = new StandbyState();
        }
    }
}
