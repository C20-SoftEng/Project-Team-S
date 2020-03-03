package edu.wpi.cs3733.c20.teamS.collisionMasks;

import edu.wpi.cs3733.c20.teamS.utilities.numerics.Vector2;
import edu.wpi.cs3733.c20.teamS.utilities.rx.ReactiveProperty;
import io.reactivex.rxjava3.core.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.scene.shape.Polygon;

import java.util.HashSet;

public final class Room {
    private int floor;
    private final ReactiveProperty<String> name = new ReactiveProperty<>("");
    private final ReactiveProperty<String> description = new ReactiveProperty<>("");
    private final ReactiveProperty<String> icon = new ReactiveProperty<>("");
    private final ObservableList<Vector2> vertices = FXCollections.observableArrayList();
    private final ObservableSet<String> touchingNodes = FXCollections.observableSet(new HashSet<>());

    public Room() {}
    public Room(int floor) {
        this.floor = floor;
    }
    public Polygon toPolygon() {
        Polygon result = new Polygon();
        for (Vector2 vertex : vertices)
            result.getPoints().addAll(vertex.x(), vertex.y());

        return result;
    }
    public Vector2 centroid() {
        return vertices.stream()
                .reduce(Vector2.ZERO, Vector2::add)
                .divide(vertices.size());
    }
    public int floor() {
        return floor;
    }
    public void setFloor(int value) {
        floor = value;
    }
    public String name() {
        return name.value();
    }
    public void setName(String value) {
        name.setValue(value);
    }
    public Observable<String> nameChanged() {
        return name.changed();
    }
    public String description() {
        return description.value();
    }
    public void setDescription(String value) {
        description.setValue(value);
    }
    public Observable<String> descriptionChanged() {
        return description.changed();
    }
    public String icon() {
        return icon.value();
    }
    public void setIcon(String value) {
        icon.setValue(value);
    }
    public Observable<String> iconChanged() {
        return icon.changed();
    }
    public ObservableList<Vector2> vertices() {
        return vertices;
    }
    public ObservableSet<String> touchingNodes() {
        return touchingNodes;
    }

    public void setLastVertex(Vector2 value) {
        vertices.set(vertices.size() - 1, value);
    }
    public void setLastVertex(double x, double y) {
        setLastVertex(new Vector2(x, y));
    }

    @Override public String toString() {
        return name();
    }
}
