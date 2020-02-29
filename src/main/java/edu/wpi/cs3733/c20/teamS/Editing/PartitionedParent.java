package edu.wpi.cs3733.c20.teamS.Editing;

import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import edu.wpi.cs3733.c20.teamS.utilities.rx.ReactiveProperty;
import io.reactivex.rxjava3.core.Observable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Groups child controls into partitions child controls based on the value of a key.
 *
 * @param <TKey>
 * @param <TChild>
 */
public class PartitionedParent<TKey, TChild extends Node> extends Parent {
    private final ReactiveProperty<TKey> currentPartition = new ReactiveProperty<>(null);
    private final Map<TKey, Group> groupMap = new HashMap<>();
    private final Map<TChild, TKey> childPartitionMap = new HashMap<>();

    public TKey currentPartition() {
        return currentPartition.value();
    }
    public void setCurrentPartition(TKey value) {
        for (Map.Entry<TKey, Group> entry : groupMap.entrySet()) {
            entry.getValue().setVisible(Objects.equals(entry.getKey(), value));
        }
        currentPartition.setValue(value);
    }
    public Observable<TKey> currentPartitionChanged() {
        return currentPartition.changed();
    }
    public void putChild(TKey partition, TChild child) {
        if (child == null) ThrowHelper.illegalNull("child");

        if (childPartitionMap.containsKey(child)) {
            TKey old = childPartitionMap.get(child);
            groupMap.get(old).getChildren().remove(child);
        }

        Group group = groupMap.computeIfAbsent(partition, key -> {
            Group result = new Group();
            getChildren().add(result);
            return result;
        });
        group.getChildren().add(child);
    }
    public boolean removeChild(TChild child) {
        if (!childPartitionMap.containsKey(child))
            return false;

        TKey partition = childPartitionMap.get(child);
        boolean wasRemoved = groupMap.get(partition).getChildren().remove(child);
        assert wasRemoved : "groupMap and childPartitionMap out of sync!";

        return true;
    }
}
