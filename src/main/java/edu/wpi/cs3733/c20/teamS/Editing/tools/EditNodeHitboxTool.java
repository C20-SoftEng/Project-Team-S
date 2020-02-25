package edu.wpi.cs3733.c20.teamS.Editing.tools;

//import com.sun.org.apache.xpath.internal.operations.Bool;
import edu.wpi.cs3733.c20.teamS.Editing.NodeHitbox;
import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import edu.wpi.cs3733.c20.teamS.database.NodeData;
import edu.wpi.cs3733.c20.teamS.utilities.Numerics;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public final class EditNodeHitboxTool implements IEditingTool {
    private State state;
    private final Supplier<Group> groupSupplier;
    private final PublishSubject<NodeHitbox> hitboxAdded = PublishSubject.create();

    public EditNodeHitboxTool(Supplier<Group> groupSupplier) {
        if (groupSupplier == null) ThrowHelper.illegalNull("groupSupplier");

        this.groupSupplier = groupSupplier;
        state = new StandbyState();
    }

    public Observable<NodeHitbox> hitboxAdded() {
        return hitboxAdded;
    }

    @Override
    public void onMapClicked(double x, double y) {
        state.onMapClicked(x, y);
    }
    @Override
    public void onNodeClicked(NodeData node) {
        state.onNodeClicked(node);
    }

    private abstract class State {
        public void onNodeClicked(NodeData node) {}
        public void onMapClicked(double x, double y) {}
    }

    private final class StandbyState extends State {
        @Override
        public void onNodeClicked(NodeData node) {
            state = new BeginEditHitboxState(node);
        }
    }
    private final class BeginEditHitboxState extends State {
        private final NodeData node;

        public BeginEditHitboxState(NodeData node) {
            this.node = node;
        }

        @Override
        public void onMapClicked(double x, double y) {
            state = new EditHitboxState(node);
        }
    }
    private final class EditHitboxState extends State {
        private final Polygon polygon;
        private final NodeData node;
        private final List<Circle> verticeHandles = new ArrayList<>();
        private static final double HANDLE_RADIUS = 12;

        public EditHitboxState(NodeData node) {
            this.node = node;
            polygon = new Polygon();
            polygon.setFill(Color.AQUA.deriveColor(0, 0, 0, 0.4));
            groupSupplier.get().getChildren().add(polygon);
        }

        @Override
        public void onMapClicked(double x, double y) {
            if (isInsideHandle(x, y)) {
                state = new StandbyState();
                hitboxAdded.onNext(new NodeHitbox(node, polygon));
                groupSupplier.get().getChildren().removeAll(verticeHandles);
                return;
            }

            polygon.getPoints().addAll(x, y);
            Circle vertice = createVertice(x, y);
            vertice.setFill(Color.BLUEVIOLET);
            verticeHandles.add(vertice);
            groupSupplier.get().getChildren().add(vertice);
        }

        private Circle createVertice(double x, double y) {
            Circle circle = new Circle();
            circle.setCenterX(x);
            circle.setCenterY(y);
            circle.setRadius(HANDLE_RADIUS);
            return circle;
        }

        private boolean isInsideHandle(double x, double y) {
            for (Circle handle : verticeHandles) {
                if (Numerics.distance(x, y, handle.getCenterX(), handle.getCenterY()) <= HANDLE_RADIUS)
                    return true;
            }
            return false;
        }
    }

}
