package edu.wpi.cs3733.c20.teamS.pathfinding;

import com.google.common.graph.MutableGraph;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import edu.wpi.cs3733.c20.teamS.NodeData;
import javafx.scene.Node;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

import static java.lang.Math.*;

public class WrittenInstructions {
    double realLifeMeasurementFt = 594.51;
    double realLifeMeasurementM = 181.21;
    double pixelMeasurement = 2242.9;
    double ftRatio = realLifeMeasurementFt / pixelMeasurement;
    double mRatio = realLifeMeasurementM / pixelMeasurement;

    LinkedList<NodeData> path;
    LinkedList<String> instructions = new LinkedList<>();

    Double savingDistanceM;
    Double savingDistanceFT;

    public WrittenInstructions(LinkedList<NodeData> path) {
        this.path = path;
    }


    public LinkedList<String> directions() {
        if (path.size() > 2) {
            for (int i = 0; i < path.size() - 2; i++) {

                if (directionOfPoint(path.get(i), path.get(i + 1), path.get(i + 2)) == 1) {
                    instructions.add(("Turn Right In " + Math.round(distance(path.get(i),
                            path.get(i + 1)) * ftRatio) + "FT OR " +
                            Math.round(distance(path.get(i), path.get(i + 1)) * mRatio) + "M"));
                    //System.out.println("Turn Right");
                }

                if (directionOfPoint(path.get(i), path.get(i + 1), path.get(i + 2)) == -1) {
                    instructions.add(("Turn Left In " + Math.round(distance(path.get(i),
                            path.get(i + 1)) * ftRatio) + "FT OR " +
                            Math.round(distance(path.get(i), path.get(i + 1)) * mRatio) + "M"));
                    //System.out.println("Turn Left");
                } else if ((directionOfPoint(path.get(i), path.get(i + 1), path.get(i + 2))) == 0) {
                    instructions.add(("Go Straight For " + Math.round(distance(path.get(i),
                            path.get(i + 1)) * ftRatio) + "FT OR " +
                            Math.round(distance(path.get(i), path.get(i + 1)) * mRatio) + "M"));
                    //System.out.println("Go straight");
                }
                if (i == path.size() - 2) {
                    return instructions;
                }
            }
        }

        return instructions;
    }


    public static double distance(NodeData nodeOne, NodeData nodeTwo) {
        //return Math.sqrt(Math.pow((nodeOne.x()-nodeTwo.x()),2)+ Math.pow((nodeOne.y()-nodeTwo.y()),2));
        return Math.sqrt(Math.pow((nodeOne.x() - nodeTwo.x()), 2) + Math.pow((nodeOne.y() - nodeTwo.y()), 2));


    }


    //point A,point B, point P
    static int directionOfPoint(NodeData NodeA, NodeData NodeB, NodeData NodeP) {
        // subtracting co-ordinates of point A
        // from B and P, to make A as origin

        Point A = new Point((int) NodeA.x(), (int) NodeA.y());
        Point B = new Point((int) NodeB.x(), (int) NodeB.y());
        Point P = new Point((int) NodeP.x(), (int) NodeP.y());

        B.x -= A.x;
        B.y -= A.y;
        P.x -= A.x;
        P.y -= A.y;

        // Determining cross Product
        int cross_product = B.x * P.y - B.y * P.x;

        // return RIGHT if cross product is positive
        if (cross_product > 0)
            return 1;

        // return LEFT if cross product is negative
        if (cross_product < 0)
            return -1;

        // return ZERO if cross product is zero.
        return 0;

    }
}

