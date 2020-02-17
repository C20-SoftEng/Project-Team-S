package edu.wpi.cs3733.c20.teamS.pathfinding;

import com.google.common.graph.MutableGraph;
import edu.wpi.cs3733.c20.teamS.NodeData;
import edu.wpi.cs3733.c20.teamS.ThrowHelper;

import java.util.*;

public class BreadthFirst implements IPathfinding{

    @Override
    public ArrayList<NodeData> findPath(MutableGraph<NodeData> graph, NodeData start, NodeData goal) {
        if (graph == null) ThrowHelper.illegalNull("graph");
        if (start == null) ThrowHelper.illegalNull("start");
        if (goal == null) ThrowHelper.illegalNull("goal");

        if (graph.nodes().isEmpty() || !graph.nodes().contains(start) || !graph.nodes().contains(goal)) {
            return new ArrayList<>();
        } else {
            NodeData current = start;

            LinkedList<NodeData> queue = new LinkedList<>();
            queue.add(current);

            LinkedList<NodeData> visited = new LinkedList<>();
            visited.add(current);

            HashMap<NodeData, NodeData> cameFrom = new HashMap<>();
            cameFrom.put(current, null);

            while (queue.size() != 0) {
                if (current == goal) {
                    break;
                }
                current = queue.poll();

                Iterator<NodeData> i = graph.adjacentNodes(current).iterator();
                while (i.hasNext()) {

                    NodeData n = i.next();
                    if (!visited.contains(n)) {
                        visited.add(n);
                        queue.add(n);
                        cameFrom.put(n, current);
                    }

                }


            }


            //no valid path to destination
            if (current != goal) {
                return new ArrayList<>();

            }

            ArrayList<NodeData> reversePath = new ArrayList<>();
            reversePath.add(current);

            //reconstruct the path goal->start
            while (current != start) {
                //steps back through the path
                current = cameFrom.get(current);
                reversePath.add(current);
            }

            ArrayList<NodeData> path = new ArrayList<>();
            for (int i = 0; i < reversePath.size(); i++) {
                path.add(reversePath.get(reversePath.size() - (i + 1)));
            }

            return path;
        }
    }





}
