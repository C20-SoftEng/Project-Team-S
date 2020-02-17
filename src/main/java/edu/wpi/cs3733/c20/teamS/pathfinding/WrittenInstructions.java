package edu.wpi.cs3733.c20.teamS.pathfinding;

import com.google.common.graph.MutableGraph;
import edu.wpi.cs3733.c20.teamS.NodeData;

import java.util.ArrayList;

import static java.lang.Math.abs;

public class WrittenInstructions {
    double realLifeMeasurementFt = 594.51;
    double realLifeMeasurementM = 181.21;
    double pixelMeasurement = 2242.9;
    double ftRatio = realLifeMeasurementFt / pixelMeasurement;
    double mRatio = realLifeMeasurementM / pixelMeasurement;

    ArrayList<NodeData> path;

    public WrittenInstructions(ArrayList<NodeData> path) {
        this.path = path;
    }
//    double distanceToGoXFT = convert(0, path.get(0).x(), ftRatio);
//    double distanceToGoYFT = convert(0, path.get(0).y(), ftRatio);
//    double distanceToGoXM = convert(0, path.get(0).x(), mRatio);
//    double distanceToGoYM = convert(0, path.get(0).y(), mRatio);
    public String directions() {

        for (int i = 0; i < path.size(); i++) {
            double distanceToGoXFT = abs(convert(path.get(0).x(), path.get(i+1).x(), ftRatio));
            double distanceToGoYFT = abs(convert(path.get(0).y(), path.get(i+1).y(), ftRatio));
            double distanceToGoXM = abs(convert(path.get(0).x(), path.get(i+1).x(), mRatio));
            double distanceToGoYM = abs(convert(path.get(0).y(), path.get(i+1).y(), mRatio));
            if (getAngle(path.get(i), path.get(i + 2)) < -80) {
//                distanceToGoXFT =+ convert(path.get(i+1).x(),path.get(i+2).x(),ftRatio );
//                distanceToGoYFT =+ convert(path.get(i+1).y(), path.get(i+2).y(), ftRatio);
//                distanceToGoXM = +convert(path.get(i + 1).x(), path.get(i + 2).x(), mRatio);
//                distanceToGoYM = +convert(path.get(i + 1).y(), path.get(i + 2).y(), mRatio);

                if ((convert(path.get(0).y(), path.get(1).y(), ftRatio) < (convert(path.get(0).x(), path.get(1).x(), ftRatio)))){
                    return ("Turn Right In " + (int) distanceToGoYFT + "FT OR " + (int) distanceToGoYM + "M");

                } else {
                    return ("Turn Right In " + (int) distanceToGoXFT + "FT OR " + (int) distanceToGoXM + "M");
                }

            }

            if(abs(getAngle(path.get(i), path.get(i + 2)) )> 50){
//                double angle = getAngle(path.get(i), path.get(i + 2));
//                double distanceToGoXFT = abs(convert(path.get(0).x(), path.get(i + 1).x(), ftRatio));
//                double distanceToGoYFT = abs(convert(path.get(0).y(), path.get(i + 1).y(), ftRatio));
//                double distanceToGoXM = abs(convert(path.get(0).x(), path.get(i + 1).x(), mRatio));
//                double distanceToGoYM = abs(convert(path.get(0).y(), path.get(i + 1).y(), mRatio));
                if ((convert(path.get(0).y(), path.get(1).y(), ftRatio) > (convert(path.get(0).x(), path.get(1).x(), ftRatio)))){

                    return ("Turn Left In " + (int) distanceToGoYFT + "FT OR " + (int) distanceToGoYM + "M");
                } else {
                    return ("Turn Left In "  + (int) distanceToGoXFT + "FT OR " + (int) distanceToGoXM + "M");
                }
            }
        }
        return "1";
    }



    public double getAngle(NodeData start,NodeData end){
        double angle = Math.toDegrees(Math.atan2(end.y()-start.y(), end.x() - start.x()));
        return angle;
    }

    public double convert(double start,double end,double ratio){
    double conversion = (end-start)*ratio;
    return conversion;
}
}
