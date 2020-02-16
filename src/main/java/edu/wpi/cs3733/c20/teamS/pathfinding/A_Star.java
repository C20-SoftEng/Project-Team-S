package edu.wpi.cs3733.c20.teamS.pathfinding;


import com.google.common.graph.MutableGraph;
import com.google.common.graph.MutableValueGraph;
import edu.wpi.cs3733.c20.teamS.NodeData;
import edu.wpi.cs3733.c20.teamS.ThrowHelper;

import java.util.*;


public class A_Star implements IPathfinding{
    private MutableValueGraph<NodeData, Double> graph;
    private NodeData start;
    private NodeData goal;

    @Override
    public Set<NodeData> findPath(MutableGraph<NodeData> graph, NodeData start, NodeData goal) {
        if(graph == null) ThrowHelper.illegalNull("graph");
        if(start == null) ThrowHelper.illegalNull("start");
        if(goal == null) ThrowHelper.illegalNull("goal");

        NodeData current = start;
        PriorityQueue<NodeData> frontier = new PriorityQueue<NodeData>(nodeComparator);
        frontier.add(current);

        HashMap<NodeData, Double> costSoFar = new HashMap<>();
        costSoFar.put(start, 0.0);

        //first input (key) is the node after the second input
        //a key gets the node where the path came from
        HashMap<NodeData, NodeData> cameFrom = new HashMap<>();
        cameFrom.put(start, null);

        while (!frontier.isEmpty()){
            current = frontier.poll();

            if (current == goal)   break;

            for (NodeData next: graph.adjacentNodes(current)){
                double csf = costSoFar.get(current);
                double ev = euclideanDistance(start, current);
                double new_cost =  csf + ev;
                if (!costSoFar.containsKey(next) || new_cost < costSoFar.get(next)){
                    costSoFar.put(next, new_cost);
                    double priority = new_cost + euclideanDistance(goal, next);
                    next.setCost(priority);
                    frontier.add(next);
                    cameFrom.put(next, current);
                }
            }
        }

        Set<NodeData> path = new HashSet<NodeData>();
        path.add(current);

        while (current != start){
            //steps back through the path
            current = cameFrom.get(current);
        }

        return path;
    }

    //Comparator anonymous class implementation
    private static Comparator<NodeData> nodeComparator = (c1, c2) -> c1.cost()>c2.cost() ? 1 : c1.cost() < c2.cost() ? -1 : 0;

    /**
     * A heuristic function that uses the euclidean distance
     * @param goal the goal node
     * @param current the current node
     * @return the euclidean distance
     */
    private double euclideanDistance(NodeData goal, NodeData current){
         return Math.sqrt((goal.x()-current.x())*(goal.x()-current.x()) + (goal.y()-current.y())*(goal.y()-current.y()));
    }
}
