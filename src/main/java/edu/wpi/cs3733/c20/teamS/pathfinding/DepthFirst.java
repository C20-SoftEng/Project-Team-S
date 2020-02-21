package edu.wpi.cs3733.c20.teamS.pathfinding;

import com.google.common.graph.MutableGraph;
import edu.wpi.cs3733.c20.teamS.database.NodeData;
import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import javafx.scene.Node;

import java.util.*;

public class DepthFirst implements IPathfinding {


    @Override
    public ArrayList<NodeData> findPath(MutableGraph<NodeData> graph, NodeData start, NodeData goal) {
        if(graph == null) ThrowHelper.illegalNull("graph");
        if(start == null) ThrowHelper.illegalNull("start");
        if(goal == null) ThrowHelper.illegalNull("goal");


        if(graph.nodes().isEmpty() || !graph.nodes().contains(start) || !graph.nodes().contains(goal)){
            return new ArrayList<>();
        }
        else{
            NodeData current = start;

            PriorityQueue<NodeData> frontier = new PriorityQueue<NodeData>(nodeComparator);
            frontier.add(current);

            LinkedList<NodeData> visited = new LinkedList<>();
            visited.add(current);

            HashMap<NodeData, NodeData> cameFrom = new HashMap<>();
            cameFrom.put(current, null);

            while(!frontier.isEmpty()){
                System.out.println(current.getNodeID());
                current = frontier.poll();

                if (current == goal){
                    break;
                }

                for(NodeData next: graph.adjacentNodes(current)){
                    if(current == next){
                        cameFrom.clear();
                        System.out.println("Start = next");
                        break;
                    }
                    if (!visited.contains(next)){
                        visited.add(next);
                        frontier.add(next);
                        cameFrom.put(next, current);}
                }
            }

            //no valid path to destination
            if(current != goal){
                return new ArrayList<>();
            }

            ArrayList<NodeData> reversePath = new ArrayList<>();
            reversePath.add(current);

            //reconstruct the path goal->start
            while (current != start){
                //steps back through the path
                current = cameFrom.get(current);
                reversePath.add(current);
            }

            ArrayList<NodeData> path = new ArrayList<>();
            for(int i = 0; i< reversePath.size(); i++){
                path.add(reversePath.get(reversePath.size()-(i+1)));
            }
            return path;
        }
    }

    //Comparator anonymous class implementation
    private static Comparator<NodeData> nodeComparator = (c1, c2) -> c1.cost()>c2.cost() ? 1 : c1.cost() < c2.cost() ? -1 : 0;

    /**
     * A heuristic function that uses the euclidean distance
     * @param goal the goal node
     * @param current the current node
     * @return the euclidean distance
     */
    public double euclideanDistance(NodeData goal, NodeData current){
        if(goal == null) ThrowHelper.illegalNull("goal");
        if(current == null) ThrowHelper.illegalNull("current");

        return Math.sqrt((goal.getxCoordinate()-current.getxCoordinate())*(goal.getxCoordinate()-current.getxCoordinate()) + (goal.getyCoordinate()-current.getyCoordinate())*(goal.getyCoordinate()-current.getyCoordinate()));
    }




}
