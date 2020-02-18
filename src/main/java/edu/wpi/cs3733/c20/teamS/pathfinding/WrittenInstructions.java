package edu.wpi.cs3733.c20.teamS.pathfinding;

import com.google.common.graph.MutableGraph;
import edu.wpi.cs3733.c20.teamS.NodeData;
import javafx.scene.Node;

import java.util.ArrayList;
import java.util.LinkedList;

import static java.lang.Math.abs;
import static java.lang.Math.acos;

public class WrittenInstructions {
    double realLifeMeasurementFt = 594.51;
    double realLifeMeasurementM = 181.21;
    double pixelMeasurement = 2242.9;
    double ftRatio = realLifeMeasurementFt / pixelMeasurement;
    double mRatio = realLifeMeasurementM / pixelMeasurement;

    ArrayList<NodeData> path;
    ArrayList<String> instructions = new ArrayList<String>();

    public WrittenInstructions(ArrayList<NodeData> path) {
        this.path = path;
    }



    public ArrayList<String> directions() {
        if (path.size() > 2) {
            for (int i = 0; i < path.size()-2; i++) {

                if (getAngle(path.get(i), path.get(i + 1), path.get(i + 2)) > 90) {
                        instructions. add(("Turn Right In " +  distance(path.get(i),
                                path.get(i+1)) * ftRatio + "FT OR " +
                                 distance(path.get(i), path.get(i+1)) * mRatio + "M"));
                    }

                if (abs(getAngle(path.get(i), path.get(i + 1), path.get(i + 2))) > 0) {
                    instructions. add(("Turn Left In " + ( distance(path.get(i),
                            path.get(i+1)) * ftRatio + "FT OR " +
                             distance(path.get(i), path.get(i+1)) * mRatio + "M")));
                    }
                else{
                    instructions. add(("Go Straight For " +  distance(path.get(i),
                            path.get(i+1)) * ftRatio + "FT OR " +
                             distance(path.get(i), path.get(i+1)) * mRatio + "M"));
                }
                if(i == path.size()-2){
                    return instructions;
                }
            }
        }

        return instructions;
    }



    public double distance(NodeData nodeOne, NodeData nodeTwo){
        //return Math.sqrt(Math.pow((nodeOne.x()-nodeTwo.x()),2)+ Math.pow((nodeOne.y()-nodeTwo.y()),2));
        return  Math.sqrt(Math.pow((nodeOne.x()-nodeTwo.x()),2)+ Math.pow((nodeOne.y()-nodeTwo.y()),2));


    }

    public double getAngle(NodeData one, NodeData two, NodeData three){

        double angle = Math.acos((Math.pow(distance(one,two),2)-Math.pow(distance(two,three),2) - Math.pow(distance(one,three),2))/
                (-2*distance(two,three)*distance(one,three)));
        return angle;
    }

    public double convert(double start,double end,double ratio){
    double conversion = (end-start)*ratio;
    return conversion;
}
}
