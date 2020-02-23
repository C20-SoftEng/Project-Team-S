package edu.wpi.cs3733.c20.teamS.Editing.tools;

import com.google.common.graph.EndpointPair;
import com.sun.scenario.effect.impl.state.MotionBlurState;
import edu.wpi.cs3733.c20.teamS.database.NodeData;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.checkerframework.framework.qual.NoDefaultQualifierForUse;

public final class AddRemoveEdgeTool implements IEditingTool {
    private final ObservableGraph graph;
    private State state;

    public AddRemoveEdgeTool(ObservableGraph graph) {
        this.graph = graph;
        this.state = new StandbyState();
    }

    @Override
    public void onNodeClicked(NodeData node, MouseEvent event) {
        state.onNodeClicked(node, event);
    }
    @Override
    public void onEdgeClicked(EndpointPair<NodeData> edge, MouseEvent event) {
        state.onEdgeClicked(edge, event);
    }

    private abstract class State {
        public void onNodeClicked(NodeData node, MouseEvent event) {}
        public void onEdgeClicked(EndpointPair<NodeData> edge, MouseEvent event) {}
    }

    private final class StandbyState extends State {
        @Override
        public void onNodeClicked(NodeData node, MouseEvent event) {
            if (event.getButton() != MouseButton.PRIMARY)
                return;

            state = new StartNodeSelectedState(node);
        }

        @Override
        public void onEdgeClicked(EndpointPair<NodeData> edge, MouseEvent event) {
            if (event.getButton() != MouseButton.SECONDARY)
                return;

            graph.removeEdge(edge.nodeU(), edge.nodeV());
        }
    }

    private final class StartNodeSelectedState extends State {
        private final NodeData start;

        public StartNodeSelectedState(NodeData start) {
            this.start = start;
        }

        @Override
        public void onNodeClicked(NodeData node, MouseEvent event) {
            if (event.getButton() != MouseButton.PRIMARY)
                return;

            graph.putEdge(start, node);
            state = new StandbyState();
        }

        @Override
        public void onEdgeClicked(EndpointPair<NodeData> edge, MouseEvent event) {
            if (event.getButton() != MouseButton.SECONDARY)
                return;

            state = new StandbyState();
        }
    }
}
