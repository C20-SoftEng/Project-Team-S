package edu.wpi.cs3733.c20.teamS.Editing.tools;

import com.google.common.graph.EndpointPair;
import edu.wpi.cs3733.c20.teamS.database.EdgeData;
import edu.wpi.cs3733.c20.teamS.database.NodeData;

final class AddEdgeTool extends EditingTool {
    private final GraphEditor graphEditor;
    private State state;

    public AddEdgeTool(GraphEditor graphEditor) {
        this.graphEditor = graphEditor;
        this.state = new StandbyState();
    }

    @Override
    public void onNodeClicked(NodeData node) {
        state.onNodeClicked(node);
    }
    @Override
    public void onEdgeClicked(EndpointPair<NodeData> edge) {}
    @Override
    public void onMapClicked(double x, double y) {}

    private abstract class State {
        public abstract void onNodeClicked(NodeData node);
    }
    private final class StandbyState extends State {
        @Override
        public void onNodeClicked(NodeData node) {
            state = new StartNodeSelectedState(node);
        }
    }
    private final class StartNodeSelectedState extends State {
        private final NodeData start;

        public StartNodeSelectedState(NodeData start) {
            this.start = start;
        }

        @Override
        public void onNodeClicked(NodeData node) {
            if (!graphEditor.graph.putEdge(start, node))
                return;

            EdgeData ed = new EdgeData(start, node);
            graphEditor.database.addEdge(ed);
            graphEditor.edgeAdded.onNext(EndpointPair.unordered(start, node));

            AddEdgeTool.this.state = new StandbyState();
        }
    }
}
