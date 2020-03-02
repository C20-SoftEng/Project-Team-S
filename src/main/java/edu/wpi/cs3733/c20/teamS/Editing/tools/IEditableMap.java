package edu.wpi.cs3733.c20.teamS.Editing.tools;

import com.google.common.graph.EndpointPair;
import edu.wpi.cs3733.c20.teamS.Editing.ObservableGraph;
import edu.wpi.cs3733.c20.teamS.Editing.events.EdgeClickedEvent;
import edu.wpi.cs3733.c20.teamS.Editing.events.NodeClickedEvent;
import edu.wpi.cs3733.c20.teamS.Editing.events.RoomClickedEvent;
import edu.wpi.cs3733.c20.teamS.Editing.viewModels.EdgeVm;
import edu.wpi.cs3733.c20.teamS.Editing.viewModels.NodeVm;
import edu.wpi.cs3733.c20.teamS.Editing.viewModels.RoomVm;
import edu.wpi.cs3733.c20.teamS.collisionMasks.Room;
import edu.wpi.cs3733.c20.teamS.database.NodeData;
import io.reactivex.rxjava3.core.Observable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

import java.util.stream.Stream;

public interface IEditableMap {
    Stream<NodeVm> nodeViewModels();
    Stream<EdgeVm> edgeViewModels();
    Stream<RoomVm> roomViewModels();
    void setNodesVisible(boolean value);
    void setNodesVisible(boolean value, int floor);
    void setEdgesVisible(boolean value);
    void setEdgesVisible(boolean value, int floor);
    void setRoomsVisible(boolean value);
    void setRoomsVisible(boolean value, int floor);

    Observable<NodeClickedEvent> nodeClicked();
    Observable<NodeClickedEvent> nodeDragged();
    Observable<NodeClickedEvent> nodeReleased();
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

    void setSelectedFloor(int value);

    boolean isPannable();
    void setPannable(boolean value);
    ObservableGraph graph();
    NodeVm getNodeViewModel(NodeData node);
    EdgeVm getEdgeViewModel(EndpointPair<NodeData> edge);
    default EdgeVm getEdgeViewModel(NodeData nodeU, NodeData nodeV) {
        return getEdgeViewModel(EndpointPair.unordered(nodeU, nodeV));
    }
    RoomVm getRoomViewModel(Room room);
}
