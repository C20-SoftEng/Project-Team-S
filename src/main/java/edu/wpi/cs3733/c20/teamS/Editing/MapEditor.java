package edu.wpi.cs3733.c20.teamS.Editing;

import edu.wpi.cs3733.c20.teamS.Editing.viewModels.NodeVm;
import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import edu.wpi.cs3733.c20.teamS.database.NodeData;
import edu.wpi.cs3733.c20.teamS.utilities.ReactiveProperty;
import io.reactivex.rxjava3.core.Observable;
import javafx.scene.Group;

import java.util.HashMap;
import java.util.Map;

public final class MapEditor {
    private final Group root;
    private final Map<Integer, Group> floorGroupMap = new HashMap<>();
    private final ReactiveProperty<Integer> floor = new ReactiveProperty<>(2);

    public MapEditor(Group root) {
        if (root == null) ThrowHelper.illegalNull("root");

        this.root = root;
    }

    public int floor() {
        return floor.value();
    }
    public void setFloor(int value) {
        for (Map.Entry<Integer, Group> entry : floorGroupMap.entrySet())
            entry.getValue().setVisible(entry.getKey() == value);

        floor.setValue(value);
    }
    public Observable<Integer> floorChanged() {
        return floor.changed();
    }

    public void drawNode(NodeData node) {
        if (!floorGroupMap.containsKey(node.getFloor()))
            floorGroupMap.put(node.getFloor(), new Group());
        Group group = floorGroupMap.get(node.getFloor());
        NodeVm vm = new NodeVm(node);
        group.getChildren().add(vm);
    }
}
