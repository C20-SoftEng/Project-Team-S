package edu.wpi.cs3733.c20.teamS.Editing.tools;

import edu.wpi.cs3733.c20.teamS.collisionMasks.Room;
import edu.wpi.cs3733.c20.teamS.utilities.Vector2;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import javafx.scene.Group;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

public class AddRemoveHitboxTool implements IEditingTool {
    private final PublishSubject<Room> hitboxAdded = PublishSubject.create();
    private final Consumer<Room> hitboxRemover;
    private final Supplier<Group> groupSupplier;
    private final IntSupplier floorSupplier;
    private State state = new StandbyState();

    /**
     * @param hitboxRemover Function to delete a hitbox.
     * @param groupSupplier Function to supply the group to draw in.
     * @param floorSupplier Function to supply the current floor.
     */
    public AddRemoveHitboxTool(
            Consumer<Room> hitboxRemover,
            Supplier<Group> groupSupplier,
            IntSupplier floorSupplier) {

        this.hitboxRemover = hitboxRemover;
        this.groupSupplier = groupSupplier;
        this.floorSupplier = floorSupplier;
    }

    public Observable<Room> hitboxAdded() {
        return hitboxAdded;
    }

    @Override public void onMapClicked(MouseEvent event) {
        state.onMapClicked(event);
    }
    @Override public void onMouseMoved(MouseEvent event) {
        state.onMouseMoved(event.getX(), event.getY());
    }
    @Override public void onHitboxClicked(Room room, MouseEvent event) {
        state.onHitboxClicked(room, event);
    }

    @Override
    public void onClosed() {
        state.onClosed();
    }

    private abstract static class State {
        public void onMapClicked(MouseEvent event) {}
        public void onMouseMoved(double x, double y) {}

        public void onHitboxClicked(Room room, MouseEvent event) {}

        public void onClosed() {}
    }
    private final class StandbyState extends State {
        @Override
        public void onMapClicked(MouseEvent event) {
            if (event.getButton() != MouseButton.PRIMARY)
                return;
            state = new ChainingState(event.getX(), event.getY());
        }

        @Override
        public void onHitboxClicked(Room room, MouseEvent event) {
            if (event.getButton() != MouseButton.SECONDARY)
                return;

            hitboxRemover.accept(room);
        }
    }
    private final class ChainingState extends State {
        private final ArrayList<Circle> handles = new ArrayList<>();
        private final Room room;
        private final Polygon displayPolygon;
        private final double radius = 7;
        private final Color vertexColor = Color.BLUEVIOLET.deriveColor(
                1, 1, 1, .9);
        private final Color polygonColor = Color.BLUE.deriveColor(
                1, 1, 1, .45);

        public ChainingState(double x, double y) {
            room = new Room(floorSupplier.getAsInt());
            displayPolygon = new Polygon();
            displayPolygon.setFill(polygonColor);

            addVertex(x, y);
            //  We need to add the first vertex twice, since the last vertex will always be
            //  "floating" with the mouse cursor.
            addVertex(x, y);

            groupSupplier.get().getChildren().add(displayPolygon);
        }

        @Override
        public void onClosed() {
            Group group = groupSupplier.get();
            group.getChildren().removeAll(handles);
            group.getChildren().remove(displayPolygon);
        }

        @Override
        public void onMapClicked(MouseEvent event) {
            switch (event.getButton()) {
                case PRIMARY:
                    setLastVertex(event.getX(), event.getY());
                    addVertex(event.getX(), event.getY());
                    break;
                case SECONDARY:
                    switchToStandbyState();
                    break;
                default:
                    break;
            }
        }
        private void onHandleClicked(Circle handle, MouseEvent event) {
            switch (event.getButton()) {
                case PRIMARY:
                    event.consume();
                    hitboxAdded.onNext(room);
                    switchToStandbyState();
                    break;
                case SECONDARY:
                    switchToStandbyState();
                    break;
                default:
                    break;
            }
        }
        @Override
        public void onMouseMoved(double x, double y) {
            setLastVertex(x, y);
        }

        private void switchToStandbyState() {
            state = new StandbyState();
            Group group = groupSupplier.get();
            group.getChildren().removeAll(handles);
            group.getChildren().remove(displayPolygon);
        }

        /**
         * Gets the handle that is following the mouse cursor.
         */
        private Circle cursorFollowingHandle() {
            return handles.get(handles.size() - 1);
        }
        private void addVertex(double x, double y) {
            Circle lastHandle = getLastHandle();
            if (lastHandle != null)
                lastHandle.setVisible(true);

            Circle handle = createHandle(x, y);
            handle.setVisible(false);
            handles.add(handle);
            room.vertices().add(new Vector2(x, y));
            displayPolygon.getPoints().addAll(x, y);
            groupSupplier.get().getChildren().add(handle);
        }
        private Circle getLastHandle() {
            if (handles.isEmpty())
                return null;
            return handles.get(handles.size() - 1);
        }
        private Circle createHandle(double x, double y) {
            Circle handle = new Circle();
            handle.setCenterX(x);
            handle.setCenterY(y);
            handle.setRadius(radius);
            handle.setFill(vertexColor);
            handle.setOnMouseClicked(e -> onHandleClicked(handle, e));
            return handle;
        }
        private void setLastVertex(double x, double y) {
            room.setLastVertex(x, y);

            Circle handle = cursorFollowingHandle();
            handle.setCenterX(x);
            handle.setCenterY(y);

            int size = displayPolygon.getPoints().size();
            displayPolygon.getPoints().set(size - 2, x);
            displayPolygon.getPoints().set(size - 1, y);
        }
    }
}
