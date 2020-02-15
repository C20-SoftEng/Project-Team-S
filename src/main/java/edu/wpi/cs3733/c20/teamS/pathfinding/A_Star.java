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

    public A_Star (){    }


    @Override
    public Iterable<NodeData> findPath(MutableValueGraph<NodeData, Double> graph, NodeData start, NodeData goal) {
        if(graph == null) ThrowHelper.illegalNull("graph");
        if(start == null) ThrowHelper.illegalNull("start");
        if(goal == null) ThrowHelper.illegalNull("goal");

        NodeData current = start;
        PriorityQueue<NodeData> frontier = new PriorityQueue<NodeData>(nodeComparator);
        frontier.add(current);

        HashMap<NodeData, Double> costSoFar = new HashMap<>();
        costSoFar.put(start, 0.0);
        HashMap<NodeData, NodeData> cameFrom = new HashMap<>();
        costSoFar.put(start, null);

        while (!frontier.isEmpty()){
            current = frontier.poll();

            if (current == goal)   break;

            for (NodeData next: graph.adjacentNodes(current)){
                double csf = costSoFar.get(current);
                double ev = (graph.edgeValue(current, next)).orElse(0.0);
                double new_cost =  csf + ev;
                if (!costSoFar.containsKey(next) || new_cost < costSoFar.get(next)){
                    costSoFar.put(next, new_cost);
                    double priority = new_cost + heuristic(goal, next);
                    next.setCost(priority);
                    frontier.add(next);
                    cameFrom.put(next, current);
                }
            }
        }

    }

    //Comparator anonymous class implementation
    private static Comparator<NodeData> nodeComparator = new Comparator<NodeData>(){

        @Override
        public int compare(NodeData c1, NodeData c2) {
            return c1.cost()>c2.cost() ? 1 : c1.cost()==c2.cost() ? 0 : c1.cost()<c2.cost() ? -1;
        }
    };

    private double heuristic(NodeData goal, NodeData current){
         return Math.sqrt((goal.x()-current.x())*(goal.x()-current.x()) + (goal.y()-current.y())*(goal.y()-current.y()));
    }
}
