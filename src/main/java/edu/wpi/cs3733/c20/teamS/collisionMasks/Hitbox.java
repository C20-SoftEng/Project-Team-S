package edu.wpi.cs3733.c20.teamS.collisionMasks;

import edu.wpi.cs3733.c20.teamS.utilities.Vector2;
import javafx.geometry.Point2D;
import javafx.scene.effect.Light;
import javafx.scene.shape.Polygon;
import java.util.ArrayList;
import java.util.List;

public final class Hitbox {
    private int floor;
    private final ArrayList<Vector2> vertices = new ArrayList<>();

    public Hitbox(int floor) {
        this.floor = floor;
    }
    public Polygon toPolygon() {
        Polygon result = new Polygon();
        for (Vector2 vertex : vertices)
            result.getPoints().addAll(vertex.x(), vertex.y());

        return result;
    }
    public int floor() {
        return floor;
    }
    public void setFloor(int value) {
        floor = value;
    }
    public List<Vector2> vertices() {
        return vertices;
    }
    public Vector2 firstVertex() {
        return vertices.get(0);
    }
    public void setFirstVertex(Vector2 value) {
        vertices.set(0, value);
    }
    public void setFirstVertex(double x, double y) {
        setFirstVertex(new Vector2(x, y));
    }
    public Vector2 lastVertex() {
        return vertices.get(vertices.size() - 1);
    }
    public void setLastVertex(Vector2 value) {
        vertices.set(vertices.size() - 1, value);
    }
    public void setLastVertex(double x, double y) {
        setLastVertex(new Vector2(x, y));
    }
    public void setOrAddLastVertex(Vector2 value) {
        if (vertices.isEmpty()) {
            vertices.add(value);
            return;
        }
        setLastVertex(value);
    }
    public void setOrAddLastVertex(double x, double y) {
        setOrAddLastVertex(new Vector2(x, y));
    }
}
