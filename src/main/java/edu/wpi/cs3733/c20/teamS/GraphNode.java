package edu.wpi.cs3733.c20.teamS;

import javafx.collections.ObservableSet;

public class GraphNode {
    //all neighbors (does not include you)
    ObservableSet<GraphNode> friends;
    //all shared edges with neighbors (you and a friend)
    ObservableSet<Edge> edges;
    NodeData node;

    public void addEdge(Edge edge){

    };
    public void removeEdge(){

    };

    public GraphNode(){

    }

    @Override
    public boolean equals(Object object){
        // If the object is compared with itself then return true
        if (object == this) {
            return true;
        }

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(object instanceof GraphNode)) {
            return false;
        }

        GraphNode node = (GraphNode)object;


        for (Edge e: edges){
            if(e.left() == node||e.right() == node){

            }
        }

    }
}
