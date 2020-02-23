package edu.wpi.cs3733.c20.teamS.Editing.tools;

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

public class EditPolygonTool implements IEditingTool {
    private final PublishSubject<Polygon> polygonAdded = PublishSubject.create();
    private final Supplier<Group> groupSupplier;
    private State state = new StandbyState();

    public EditPolygonTool(Supplier<Group> groupSupplier) {
        this.groupSupplier = groupSupplier;
    }

    public Observable<Polygon> polygonAdded() {
        return polygonAdded;
    }

    @Override
    public void onMapClicked(double x, double y) {
        state.onMapClicked(x, y);
    }
    @Override
    public void onMouseMovedOverMap(double x, double y) {
        state.onMouseMovedOverMap(x, y);
    }

    private abstract class State {
        public void onMapClicked(double x, double y) {}
        public void onMouseMovedOverMap(double x, double y) {}
    }
    private final class StandbyState extends State {
        @Override
        public void onMapClicked(double x, double y) {
            state = new ChainingState(x, y);
        }
    }
    private final class ChainingState extends State {
        private final List<Circle> vertices = new ArrayList<>();
        private final Polygon polygon = new Polygon();
        private final double radius = 7;
        private final Color vertexColor = Color.BLUEVIOLET.deriveColor(
                1, 1, 1, .9);
        private final Color polygonColor = Color.BLUE.deriveColor(
                1, 1, 1, .45);

        public ChainingState(double x, double y) {
            addVertex(x, y);
            //  We need to add the first vertex twice, since the last vertex will always be
            //  "floating" with the mouse cursor.
            addVertex(x, y);
            groupSupplier.get().getChildren().add(polygon);
            polygon.setFill(polygonColor);
        }

        @Override
        public void onMapClicked(double x, double y) {
            if (isTouchingVertex(x, y)) {
                polygonAdded.onNext(polygon);
                state = new StandbyState();
                groupSupplier.get().getChildren().removeAll(vertices);
                return;
            }

            setLastVertex(x, y);
            addVertex(x, y);
        }

        @Override
        public void onMouseMovedOverMap(double x, double y) {
            setLastVertex(x, y);
        }

        private void addVertex(double x, double y) {
            Circle vertex = new Circle();
            vertex.setCenterX(x);
            vertex.setCenterY(y);
            vertex.setRadius(radius);
            vertex.setFill(vertexColor);
            vertices.add(vertex);
            polygon.getPoints().addAll(x, y);
            groupSupplier.get().getChildren().add(vertex);
        }
        private boolean isTouchingVertex(double x, double y) {
            for (Circle vertex : vertices) {
                if (vertex == getLastVertexHandle())
                    continue;
                if (Numerics.distance(x, y, vertex.getCenterX(), vertex.getCenterY()) <= radius)
                    return true;
            }
            return false;
        }
        private void setLastVertex(double x, double y) {
            int size = polygon.getPoints().size();
            polygon.getPoints().set(size - 2, x);
            polygon.getPoints().set(size - 1, y);

            Circle vertex = vertices.get(vertices.size() - 1);
            vertex.setCenterX(x);
            vertex.setCenterY(y);
        }
        private Circle getLastVertexHandle() {
            return vertices.get(vertices.size() - 1);
        }
    }
}
