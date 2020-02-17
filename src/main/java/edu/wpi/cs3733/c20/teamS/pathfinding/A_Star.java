package edu.wpi.cs3733.c20.teamS.pathfinding;


import com.google.common.graph.MutableGraph;
import edu.wpi.cs3733.c20.teamS.GraphNode;
import edu.wpi.cs3733.c20.teamS.ThrowHelper;

import java.util.*;


public class A_Star implements IPathfinding{
    private MutableGraph<GraphNode> graph;
    private GraphNode start;
    private GraphNode goal;

    /**
     * Uses A* to find the path in the graph from the start node to the goal node
     *
     * Returns an empty array list if:
     *  start is not in the graph
     *  goal is not in the graph
     *  the graph is empty
     *  a path cannot be found
     *
     * @param graph the graph to traverse
     * @param start a node on the graph (start)
     * @param goal a node on the graph (destination)
     * @return
     */
    @Override
    public ArrayList<GraphNode> findPath(MutableGraph<GraphNode> graph, GraphNode start, GraphNode goal) {
        if(graph == null) ThrowHelper.illegalNull("graph");
        if(start == null) ThrowHelper.illegalNull("start");
        if(goal == null) ThrowHelper.illegalNull("goal");

        if(graph.nodes().isEmpty() || !graph.nodes().contains(start) || !graph.nodes().contains(goal)){
            return new ArrayList<>();
        }
        else{
            GraphNode current = start;
            PriorityQueue<GraphNode> frontier = new PriorityQueue<GraphNode>(nodeComparator);
            frontier.add(current);

            HashMap<GraphNode, Double> costSoFar = new HashMap<>();
            costSoFar.put(start, 0.0);

            //first input (key) is the node after the second input
            //a key gets the node where the path came from
            HashMap<GraphNode, GraphNode> cameFrom = new HashMap<>();
            cameFrom.put(start, null);

            while (!frontier.isEmpty()){
                current = frontier.poll();

                if (current == goal)   break;

                for (GraphNode next: graph.adjacentNodes(current)){
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

            //no valid path to destination
            if(current != goal){
                return new ArrayList<>();
            }

            ArrayList<GraphNode> reversePath = new ArrayList<>();
            reversePath.add(current);

            //reconstruct the path goal->start
            while (current != start){
                //steps back through the path
                current = cameFrom.get(current);
                reversePath.add(current);
            }

            ArrayList<GraphNode> path = new ArrayList<>();
            for(int i = 0; i< reversePath.size(); i++){
                path.add(reversePath.get(reversePath.size()-i));
            }

            return path;
        }
    }

    //Comparator anonymous class implementation
    private static Comparator<GraphNode> nodeComparator = (c1, c2) -> c1.cost()>c2.cost() ? 1 : c1.cost() < c2.cost() ? -1 : 0;

    /**
     * A heuristic function that uses the euclidean distance
     * @param goal the goal node
     * @param current the current node
     * @return the euclidean distance
     */
    private double euclideanDistance(GraphNode goal, GraphNode current){
        if(goal == null) ThrowHelper.illegalNull("goal");
        if(current == null) ThrowHelper.illegalNull("current");

         return Math.sqrt((goal.x()-current.x())*(goal.x()-current.x()) + (goal.y()-current.y())*(goal.y()-current.y()));
    }
}
