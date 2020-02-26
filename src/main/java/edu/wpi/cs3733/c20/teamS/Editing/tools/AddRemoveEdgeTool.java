package edu.wpi.cs3733.c20.teamS.Editing.tools;

import com.google.common.graph.EndpointPair;
import com.sun.scenario.effect.impl.state.MotionBlurState;
import edu.wpi.cs3733.c20.teamS.database.NodeData;
import javafx.scene.Group;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import org.checkerframework.framework.qual.NoDefaultQualifierForUse;

import java.util.function.Supplier;

public final class AddRemoveEdgeTool implements IEditingTool {
    private final ObservableGraph graph;
    public final Supplier<Group> groupSupplier;
    private State state;

    public AddRemoveEdgeTool(ObservableGraph graph, Supplier<Group> groupSupplier) {
        this.graph = graph;
        this.groupSupplier = groupSupplier;
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

    @Override
    public void onMouseMoved(MouseEvent event) {
        state.onMouseMoved(event);
    }

    private abstract class State {
        public void onNodeClicked(NodeData node, MouseEvent event) {}
        public void onEdgeClicked(EndpointPair<NodeData> edge, MouseEvent event) {}
        public void onMouseMoved(MouseEvent event) {}
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

            assert edge.nodeU() != null : "nodeU is null";
            assert edge.nodeV() != null : "nodeV is null";

            graph.removeEdge(edge.nodeU(), edge.nodeV());
        }
    }

    private final class StartNodeSelectedState extends State {
        private final NodeData start;
        private final Line edgeDisplay;
        private static final double LINE_WIDTH = 5.0;

        public StartNodeSelectedState(NodeData start) {
            assert start != null : "start was null.";
            this.start = start;
            edgeDisplay = new Line();
            edgeDisplay.setStartX(start.getxCoordinate());
            edgeDisplay.setStartY(start.getyCoordinate());
            edgeDisplay.setEndX(start.getxCoordinate());
            edgeDisplay.setEndY(start.getyCoordinate());
            edgeDisplay.setStrokeWidth(LINE_WIDTH);
            edgeDisplay.setFill(Color.BLUE);
            edgeDisplay.setMouseTransparent(true);
            groupSupplier.get().getChildren().add(edgeDisplay);
        }

        @Override
        public void onNodeClicked(NodeData node, MouseEvent event) {
            if (event.getButton() != MouseButton.PRIMARY)
                return;
            if (graph.inner().adjacentNodes(node).contains(start))
                return;

            graph.putEdge(start, node);
            switchToStandbyState();
        }

        @Override
        public void onEdgeClicked(EndpointPair<NodeData> edge, MouseEvent event) {
            if (event.getButton() != MouseButton.SECONDARY)
                return;

            switchToStandbyState();
        }

        @Override
        public void onMouseMoved(MouseEvent event) {
            edgeDisplay.setEndX(event.getX());
            edgeDisplay.setEndY(event.getY());
        }

        private void switchToStandbyState() {
            groupSupplier.get().getChildren().remove(edgeDisplay);
            state = new StandbyState();
        }
    }
}
