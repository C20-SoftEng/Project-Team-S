package edu.wpi.cs3733.c20.teamS.pathFindingTests;

import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import edu.wpi.cs3733.c20.teamS.database.NodeData;
import edu.wpi.cs3733.c20.teamS.pathfinding.BreadthFirst;
import edu.wpi.cs3733.c20.teamS.pathfinding.IPathfinder;
import edu.wpi.cs3733.c20.teamS.pathfinding.Path;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BreadthFirstTests extends PathfinderTests {
    protected final IPathfinder pathFinder = new BreadthFirst();
    protected final MutableGraph<NodeData> graph = GraphBuilder.undirected().build();

    public static class DisjointNodes extends BreadthFirstTests {
        private final NodeData start;
        private final NodeData goal;

        public DisjointNodes() {
            graph.addNode(start = node(0, 1));
            graph.addNode(goal = node(2, 3));
        }

        @Test
        public void haveNoPath() {
            Path path = pathFinder.findPath(graph, start, goal);

            assertTrue(path.isEmpty());
            assertFalse(path.iterator().hasNext());
        }
    }

    public static class StartSameAsGoal extends BreadthFirstTests {
        private final NodeData singleton;
        private final Path path;

        public StartSameAsGoal() {
            graph.addNode(singleton = node(7, -4.5));
            path = pathFinder.findPath(graph, singleton, singleton);
        }

        @Test
        public void findsSingletonPath() {
            assertEquals(1, path.size());
        }

        @Test
        public void findsPathWithSingletonNode() {
            assertEquals(singleton, path.iterator().next());
        }
    }
}
