package edu.wpi.cs3733.c20.teamS.pathfinding;


import com.google.common.graph.MutableGraph;
import com.google.common.graph.MutableValueGraph;
import edu.wpi.cs3733.c20.teamS.NodeData2;
import edu.wpi.cs3733.c20.teamS.ThrowHelper;

import java.util.*;


public class A_Star implements IPathfinding{
    private MutableGraph<NodeData2> graph;
    private NodeData2 start;
    private NodeData2 goal;

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
    public ArrayList<NodeData2> findPath(MutableGraph<NodeData2> graph, NodeData2 start, NodeData2 goal) {
        if(graph == null) ThrowHelper.illegalNull("graph");
        if(start == null) ThrowHelper.illegalNull("start");
        if(goal == null) ThrowHelper.illegalNull("goal");

        if(graph.nodes().isEmpty() || !graph.nodes().contains(start) || !graph.nodes().contains(goal)){
            return new ArrayList<>();
        }
        else{
            NodeData2 current = start;
            PriorityQueue<NodeData2> frontier = new PriorityQueue<NodeData2>(nodeComparator);
            frontier.add(current);

            HashMap<NodeData2, Double> costSoFar = new HashMap<>();
            costSoFar.put(start, 0.0);

            //first input (key) is the node after the second input
            //a key gets the node where the path came from
            HashMap<NodeData2, NodeData2> cameFrom = new HashMap<>();
            cameFrom.put(start, null);

            while (!frontier.isEmpty()){
                current = frontier.poll();

                if (current == goal)   break;

                for (NodeData2 next: graph.adjacentNodes(current)){
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

            ArrayList<NodeData2> reversePath = new ArrayList<>();
            reversePath.add(current);

            //reconstruct the path goal->start
            while (current != start){
                //steps back through the path
                current = cameFrom.get(current);
                reversePath.add(current);
            }

            ArrayList<NodeData2> path = new ArrayList<>();
            for(int i = 0; i< reversePath.size(); i++){
                path.add(reversePath.get(reversePath.size()-i));
            }

            return path;
        }
    }

    //Comparator anonymous class implementation
    private static Comparator<NodeData2> nodeComparator = (c1, c2) -> c1.cost()>c2.cost() ? 1 : c1.cost() < c2.cost() ? -1 : 0;

    /**
     * A heuristic function that uses the euclidean distance
     * @param goal the goal node
     * @param current the current node
     * @return the euclidean distance
     */
    private double euclideanDistance(NodeData2 goal, NodeData2 current){
        if(goal == null) ThrowHelper.illegalNull("goal");
        if(current == null) ThrowHelper.illegalNull("current");

         return Math.sqrt((goal.x()-current.x())*(goal.x()-current.x()) + (goal.y()-current.y())*(goal.y()-current.y()));
    }
}
