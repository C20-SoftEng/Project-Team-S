package edu.wpi.cs3733.c20.teamS.Editing.tools;

import edu.wpi.cs3733.c20.teamS.collisionMasks.Hitbox;
import edu.wpi.cs3733.c20.teamS.utilities.Numerics;
import edu.wpi.cs3733.c20.teamS.utilities.Vector2;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

import java.util.ArrayList;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

public class EditPolygonTool implements IEditingTool {
    private final PublishSubject<Hitbox> hitboxAdded = PublishSubject.create();
    private final Supplier<Group> groupSupplier;
    private final IntSupplier floorSupplier;
    private State state = new StandbyState();

    public EditPolygonTool(Supplier<Group> groupSupplier, IntSupplier floorSupplier) {
        this.groupSupplier = groupSupplier;
        this.floorSupplier = floorSupplier;
    }

    public Observable<Hitbox> hitboxAdded() {
        return hitboxAdded;
    }

    @Override
    public void onMapClicked(MouseEvent event) {
        state.onMapClicked(event.getX(), event.getY());
    }
    @Override
    public void onMouseMovedOverMap(double x, double y) {
        state.onMouseMoved(x, y);
    }
    @Override
    public void onEscapeKey() {
        state.onEscapeKey();
    }

    private abstract class State {
        public void onMapClicked(double x, double y) {}
        public void onMouseMoved(double x, double y) {}
        public void onEscapeKey() {}
    }
    private final class StandbyState extends State {
        @Override
        public void onMapClicked(double x, double y) {
            state = new ChainingState(x, y);
        }
    }
    private final class ChainingState extends State {
        private final ArrayList<Circle> handles = new ArrayList<>();
        private final Hitbox hitbox;
        private final Polygon displayPolygon;
        private final double radius = 7;
        private final Color vertexColor = Color.BLUEVIOLET.deriveColor(
                1, 1, 1, .9);
        private final Color polygonColor = Color.BLUE.deriveColor(
                1, 1, 1, .45);

        public ChainingState(double x, double y) {
            hitbox = new Hitbox(floorSupplier.getAsInt());
            displayPolygon = new Polygon();
            displayPolygon.setFill(polygonColor);

            addVertex(x, y);
            //  We need to add the first vertex twice, since the last vertex will always be
            //  "floating" with the mouse cursor.
            addVertex(x, y);

            groupSupplier.get().getChildren().add(displayPolygon);
        }

        @Override
        public void onMapClicked(double x, double y) {
            if (isTouchingVertex(x, y)) {
                hitboxAdded.onNext(hitbox);
                switchToStandbyState();
                return;
            }
            setLastVertex(x, y);
            addVertex(x, y);
        }

        @Override
        public void onMouseMoved(double x, double y) {
            setLastVertex(x, y);
        }
        @Override
        public void onEscapeKey() {
            switchToStandbyState();
        }

        private void switchToStandbyState() {
            state = new StandbyState();
            Group group = groupSupplier.get();
            group.getChildren().removeAll(handles);
            group.getChildren().remove(displayPolygon);
        }

        private boolean isTouchingVertex(double x, double y) {
            for (Circle handle : handles) {
                if (handle == getLastHandle())
                    continue;
                if (Numerics.distance(x, y, handle.getCenterX(), handle.getCenterY()) <= radius)
                    return true;
            }
            return false;
        }
        private Circle getLastHandle() {
            return handles.get(handles.size() - 1);
        }
        private void addVertex(double x, double y) {
            Circle handle = createHandle(x, y);
            handles.add(handle);
            hitbox.vertices().add(new Vector2(x, y));
            displayPolygon.getPoints().addAll(x, y);
            groupSupplier.get().getChildren().add(handle);
        }
        private Circle createHandle(double x, double y) {
            Circle handle = new Circle();
            handle.setCenterX(x);
            handle.setCenterY(y);
            handle.setRadius(radius);
            handle.setFill(vertexColor);
            return handle;
        }
        private void setLastVertex(double x, double y) {
            hitbox.setLastVertex(x, y);

            Circle handle = getLastHandle();
            handle.setCenterX(x);
            handle.setCenterY(y);

            int size = displayPolygon.getPoints().size();
            displayPolygon.getPoints().set(size - 2, x);
            displayPolygon.getPoints().set(size - 1, y);
        }
    }
}
