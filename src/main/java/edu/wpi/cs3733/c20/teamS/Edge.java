package edu.wpi.cs3733.c20.teamS;



public final class Edge<TNode> {
    private final GraphNode left_;
    private final GraphNode right_;

    public Edge(GraphNode left, GraphNode right) {
        this.left_ = left;
        this.right_ = right;
    }

    public GraphNode left() {
        return left_;
    }
    public GraphNode right() {
        return right_;
    }

    /**
     * Indicates whether the edge contains the specified node.
     * @param node The node to look for.
     * @return True if present, false otherwise.
     */
    public boolean contains(GraphNode node) {
        if(left_ == node || right_ == node){
            return true;
        }
        return false;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean equals(Object object) {
        if (object == null)
            return false;

        if (object instanceof Edge) {
            Edge other = (Edge)object;
            return nodes_.equals(other.nodes_);
        }

        return false;
    }



    @Override
    public int hashCode() {
        return nodes_.hashCode();
    }

    @Override
    public String toString() {
        return nodes_.toString();
    }
}

