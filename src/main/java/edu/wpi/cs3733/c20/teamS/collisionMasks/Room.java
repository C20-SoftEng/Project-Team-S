package edu.wpi.cs3733.c20.teamS.collisionMasks;

import edu.wpi.cs3733.c20.teamS.utilities.numerics.Vector2;
import edu.wpi.cs3733.c20.teamS.utilities.rx.ReactiveProperty;
import io.reactivex.rxjava3.core.Observable;
import javafx.scene.shape.Polygon;

import java.util.*;

public final class Room {
    private int floor;
    private final ReactiveProperty<String> name = new ReactiveProperty<>("");
    private final ArrayList<Vector2> vertices = new ArrayList<>();
    private final HashSet<String> touchingNodes = new HashSet<>();

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
    public List<Vector2> vertices() {
        return vertices;
    }
    public Set<String> touchingNodes() {
        return touchingNodes;
    }

    public void setLastVertex(Vector2 value) {
        vertices.set(vertices.size() - 1, value);
    }
    public void setLastVertex(double x, double y) {
        setLastVertex(new Vector2(x, y));
    }
}
