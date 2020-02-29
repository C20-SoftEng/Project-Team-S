package edu.wpi.cs3733.c20.teamS.Editing.tools;

import com.google.common.graph.EndpointPair;
import edu.wpi.cs3733.c20.teamS.Editing.events.EdgeClickedEvent;
import edu.wpi.cs3733.c20.teamS.Editing.events.NodeClickedEvent;
import edu.wpi.cs3733.c20.teamS.Editing.events.RoomClickedEvent;
import edu.wpi.cs3733.c20.teamS.collisionMasks.Room;
import edu.wpi.cs3733.c20.teamS.database.NodeData;
import io.reactivex.rxjava3.core.Observable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public interface IEditableMap {
    Observable<NodeClickedEvent> nodeClicked();
    Observable<EdgeClickedEvent> edgeClicked();
    Observable<RoomClickedEvent> roomClicked();
    Observable<MouseEvent> mapClicked();
    Observable<MouseEvent> mouseMoved();
    boolean addNode(NodeData node);
    boolean removeNode(NodeData node);
    boolean putEdge(NodeData nodeU, NodeData nodeV);
    default boolean putEdge(EndpointPair<NodeData> edge) {
        return putEdge(edge.nodeU(), edge.nodeV());
    }
    boolean removeEdge(NodeData nodeU, NodeData nodeV);
    default boolean removeEdge(EndpointPair<NodeData> edge) {
        return removeEdge(edge.nodeU(), edge.nodeV());
    }
    boolean addRoom(Room room);
    boolean removeRoom(Room room);
    int selectedFloor();
    void addWidget(Node node);
    void removeWidget(Node node);
}
