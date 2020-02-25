package edu.wpi.cs3733.c20.teamS.pathfinding;

import com.google.common.graph.MutableGraph;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import edu.wpi.cs3733.c20.teamS.database.NodeData;
import javafx.scene.Node;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;

import static java.lang.Math.*;

public class WrittenInstructions {
    double realLifeMeasurementFt = 594.51;
    double realLifeMeasurementM = 181.21;
    double pixelMeasurement = 2242.9;
    double ftRatio = realLifeMeasurementFt / pixelMeasurement;
    double mRatio = realLifeMeasurementM / pixelMeasurement;

    List<NodeData> path;
    List<String> instructions = new ArrayList<>();

    int savingDistance=0;

    public WrittenInstructions(List<NodeData> path) {
        this.path = path;
    }


    public List<String> directions() {
        double elevatorX = 0;
        double elevatorY = 0;

//        path.get(path.size()-1).getxCoordinate();
//        path.get(path.size()-1).getyCoordinate();
        if (path.size()>2) {
            for (int i = 0; i < path.size()-2  ; i++) {


                if (directionOfPoint(path.get(i), path.get(i + 1), path.get(i + 2)) == 1) {

//                    if(isElevator(path.get(i+1), path.get(i+3))){
//                        instructions.add("Take The Elevator To Floor " + path.get(i+2).getFloor());
//                    }
                    if(path.get(i).getNodeType().equals("ELEV") && !path.get(i+1).getNodeType().equals("ELEV") && i != 0){
                        elevatorX = path.get(i).getxCoordinate();
                        elevatorY = path.get(i).getyCoordinate();
                        instructions.add("Take The Elevator To Floor " + path.get(i+1).getFloor() );

                    }
                    if(distance(path.get(i), path.get(i+1)) < 5){
                        instructions.add("Turn Right");
                    }
                    else if(path.get(i).getNodeType().equals("STAI") && !path.get(i+1).getNodeType().equals("STAI") && i != 0){
                        instructions.add("Take The Stairs To Floor " + path.get(i+1).getFloor());
                    }
                    else {

                        instructions.add(("In " + Math.round((savingDistance + distance(path.get(i), path.get(i + 1))) *
                                ftRatio) + "FT (" + Math.round((savingDistance + distance(path.get(i), path.get(i + 1))) * mRatio) + "M), Turn Right"));
                        savingDistance = 0;
                    }

                }
                if (directionOfPoint(path.get(i), path.get(i + 1), path.get(i + 2)) == -1) {

                    if(path.get(i).getNodeType().equals("ELEV") && !path.get(i+1).getNodeType().equals("ELEV") && i != 0){
                        elevatorX = path.get(i).getxCoordinate();
                        elevatorY = path.get(i).getyCoordinate();
                        instructions.add("Take The Elevator To Floor " + path.get(i+1).getFloor());

                    }
                    if(distance(path.get(i), path.get(i+1)) < 5){
                        instructions.add("Turn Left");
                    }
                    else if(path.get(i).getNodeType().equals("STAI") && !path.get(i+1).getNodeType().equals("STAI") && i != 0){
                        instructions.add("Take The Stairs To Floor " + path.get(i+1).getFloor());
                    }
                    else {

                        instructions.add(("In " + Math.round((savingDistance + distance(path.get(i), path.get(i + 1))) *
                                ftRatio) + "FT (" + Math.round((savingDistance + distance(path.get(i), path.get(i + 1))) * mRatio) + "M), Turn Left"));
                        savingDistance = 0;
                    }

                } else if ((directionOfPoint(path.get(i), path.get(i + 1), path.get(i + 2))) == 0) {

                    savingDistance += Math.round(distance(path.get(i), path.get(i + 1)));
                }



                if (i == path.size()-2) {
                    return (instructions);
                }


            }
            if (path.get(path.size()-2).getNodeType().equals("ELEV")){
                instructions.add("Take The Elevator To Floor " + path.get(path.size()-1).getFloor() + ", Then Go Straight");
            }

        }
        return instructions;
    }


    public static double distance(NodeData nodeOne, NodeData nodeTwo) {
        return sqrt(pow((nodeOne.getxCoordinate() - nodeTwo.getxCoordinate()), 2) + pow((nodeOne.getyCoordinate() - nodeTwo.getyCoordinate()), 2));


    }



    //point A,point B, point P
   public static int directionOfPoint(NodeData NodeA, NodeData NodeB, NodeData NodeP) {

        Point A = new Point((int) NodeA.getxCoordinate(), (int) NodeA.getyCoordinate());
        Point B = new Point((int) NodeB.getxCoordinate(), (int) NodeB.getyCoordinate());
        Point P = new Point((int) NodeP.getxCoordinate(), (int) NodeP.getyCoordinate());

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

