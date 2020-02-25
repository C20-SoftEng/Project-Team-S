package edu.wpi.cs3733.c20.teamS.Editing.tools;

import com.google.common.graph.EndpointPair;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import edu.wpi.cs3733.c20.teamS.database.DatabaseController;
import edu.wpi.cs3733.c20.teamS.database.NodeData;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.function.IntSupplier;

public class QuickAddRemoveNodeTool implements IEditingTool {
    private final UI ui;
    private final ObservableGraph graph;
    private final IntSupplier floorSupplier;

    public QuickAddRemoveNodeTool(
            ObservableGraph graph,
            VBox vbox, IntSupplier floorSupplier) {
        if (graph == null) ThrowHelper.illegalNull("graph");
        if (vbox == null) ThrowHelper.illegalNull("vbox");
        if (floorSupplier == null) ThrowHelper.illegalNull("floorSupplier");

        this.graph = graph;
        this.ui = new UI(vbox);
        this.floorSupplier = floorSupplier;
    }

    @Override
    public void onMapClicked(MouseEvent event) {
        if (event.getButton() != MouseButton.PRIMARY)
            return;

        NodeData node = createNodeAt(event.getX(), event.getY());
        graph.addNode(node);
    }

    @Override
    public void onClosed() {
        ui.vbox.getChildren().clear();
    }

    private NodeData createNodeAt(double x, double y) {
        NodeData node = new NodeData();
        node.setFloor(floorSupplier.getAsInt());
        node.setPosition(x, y);
        node.setNodeType(ui.nodeType());
        node.setBuilding("Faulkner");
        node.setLongName(ui.longName());
        node.setShortName(ui.shortName());
        return node;
    }

    @Override
    public void onNodeClicked(NodeData node, MouseEvent event) {
        if (event.getButton() != MouseButton.SECONDARY)
            return;

        graph.removeNode(node);
    }

    @Override
    public void onEdgeClicked(EndpointPair<NodeData> edge, MouseEvent event) {
        if (event.getButton() != MouseButton.PRIMARY)
            return;

        graph.removeEdge(edge.nodeU(), edge.nodeV());
        NodeData node = createNodeAt(event.getX(), event.getY());
        graph.addNode(node);
        graph.putEdge(edge.nodeU(), node);
        graph.putEdge(node, edge.nodeV());
        event.consume();
    }

    private static class UI {
        private final VBox vbox;
        private final ComboBox<String> nodeTypeComboBox;
        private final JFXTextField longNameTextField;
        private final JFXTextField shortNameTextField;

        public UI(VBox vbox) {
            this.vbox = vbox;
            this.vbox.getChildren().clear();
            List<Node> controls = vbox.getChildren();
            Insets padding = new Insets(8, 5, 8, 5);
            nodeTypeComboBox = new ComboBox<>();
            nodeTypeComboBox.setPadding(padding);
            nodeTypeComboBox.getItems().addAll(
                    "HALL", "DEPT", "CONF",
                    "SERV", "RETL", "INFO",
                    "LABS", "ELEV", "STAI", "EXIT"
            );
            nodeTypeComboBox.getSelectionModel().selectFirst();
            longNameTextField = new JFXTextField();
            longNameTextField.setText("Unnamed");
            longNameTextField.setPadding(padding);
            shortNameTextField = new JFXTextField();
            shortNameTextField.setPadding(padding);
            shortNameTextField.setText("NA");

            controls.add(withText("Node Type:"));
            controls.add(nodeTypeComboBox);
            controls.add(withText("Long name: "));
            controls.add(longNameTextField);
            controls.add(withText("Short name: "));
            controls.add(shortNameTextField);
        }

        public String nodeType() {
            return nodeTypeComboBox.getValue();
        }
        public String longName() {
            return longNameTextField.getText();
        }
        public String shortName() {
            return shortNameTextField.getText();
        }

        private Label withText(String text) {
            Label result = new Label();
            result.setText(text);
            result.setPadding(new Insets(8, 5, 8, 5));
            return result;
        }
    }
}
