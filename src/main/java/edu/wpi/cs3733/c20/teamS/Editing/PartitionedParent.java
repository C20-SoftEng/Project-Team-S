package edu.wpi.cs3733.c20.teamS.Editing;

import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;

import java.util.*;

/**
 * Groups child controls into partitions child controls based on the value of a key.
 *
 * @param <TKey>
 * @param <TChild>
 */
public class PartitionedParent<TKey, TChild extends Node> extends Parent {
    private final Map<TKey, Partition<TKey, TChild>> partitions = new HashMap<>();
    private final Map<TChild, TKey> childPartitionMap = new HashMap<>();

    public void putChild(TKey key, TChild child) {
        if (key == null) ThrowHelper.illegalNull("partition");
        if (child == null) ThrowHelper.illegalNull("child");

        removeChild(child);

        Partition<TKey, TChild> partition = partitions.computeIfAbsent(
                key, k -> {
                    Group group = new Group();
                    getChildren().add(group);
                    return new Partition<>(k, group, new HashSet<>());
                }
        );
        childPartitionMap.put(child, key);
        partition.mutableChildren.add(child);
        partition.group.getChildren().add(child);
    }
    public boolean removeChild(TChild child) {
        if (!childPartitionMap.containsKey(child))
            return false;

        TKey key = childPartitionMap.get(child);
        Partition<TKey, TChild> partition = partitions.get(key);
        partition.group.getChildren().remove(child);
        partition.mutableChildren.remove(child);
        childPartitionMap.remove(child);

        return true;
    }
    public Collection<Partition<TKey, TChild>> partitions() {
        return partitions.values();
    }
    public void showOnly(TKey key) {
        partitions().forEach(partition -> partition.group.setVisible(Objects.equals(partition.key(), key)));
    }
    public boolean containsKey(TKey key) {
        return partitions.containsKey(key);
    }
    public Partition<TKey, TChild> get(TKey key) {
        return partitions.get(key);
    }
    public Set<TKey> keys() {
        return partitions.keySet();
    }

    public static final class Partition<TKey, TChild> {
        private final TKey key;
        private final Group group;
        private final Set<TChild> mutableChildren;
        private final Set<TChild> readOnlyChildren;

        Partition(TKey key, Group group, Set<TChild> children) {
            if (key == null) ThrowHelper.illegalNull("key");
            if (group == null) ThrowHelper.illegalNull("group");
            if (children == null) ThrowHelper.illegalNull("children");

            this.key = key;
            this.group = group;
            this.mutableChildren = children;
            this.readOnlyChildren = Collections.unmodifiableSet(children);
        }

        public TKey key() {
            return key;
        }
        public Group group() {
            return group;
        }
        public Set<TChild> children() {
            return readOnlyChildren;
        }
    }
}
