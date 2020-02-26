package edu.wpi.cs3733.c20.teamS.Editing;

import edu.wpi.cs3733.c20.teamS.pathfinding.*;
import javafx.scene.control.RadioButton;

public final class PathfindingAlgorithmSelector {
    private final RadioButton astarButton;
    private final RadioButton djikstraButton;
    private final RadioButton depthFirstButton;
    private final RadioButton breadthFirstButton;

    public PathfindingAlgorithmSelector(
            RadioButton astarButton,
            RadioButton djikstraButton,
            RadioButton depthFirstButton,
            RadioButton breadthFirstButton
    ) {
        this.astarButton = astarButton;
        this.djikstraButton = djikstraButton;
        this.depthFirstButton = depthFirstButton;
        this.breadthFirstButton = breadthFirstButton;

        initEventHandlers();
        getButtonForPathfinder(Settings.get().pathfinder().current()).setSelected(true);
    }

    private void initEventHandlers() {
        astarButton.setOnAction(e -> setAlgorithm(new AStar()));
        djikstraButton.setOnAction(e -> setAlgorithm(new Djikstra()));
        depthFirstButton.setOnAction(e -> setAlgorithm(new DepthFirst()));
        breadthFirstButton.setOnAction(e -> setAlgorithm(new BreadthFirst()));
    }
    private void setAlgorithm(IPathfinder pathfinder) {
        Settings.get().pathfinder().setCurrent(pathfinder);
    }

    /**
     * Newell totally did not write this abomination.
     */
    private RadioButton getButtonForPathfinder(IPathfinder pathfinder) {
        if (pathfinder instanceof AStar)
            return astarButton;
        if (pathfinder instanceof Djikstra)
            return djikstraButton;
        if (pathfinder instanceof DepthFirst)
            return depthFirstButton;
        if (pathfinder instanceof BreadthFirst)
            return breadthFirstButton;

        throw new IllegalStateException("Unexpected type of pathfinder. Update that switch statement, yo.");
    }
}
