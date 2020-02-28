package edu.wpi.cs3733.c20.teamS.Editing;

import com.google.common.graph.EndpointPair;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.c20.teamS.Editing.tools.*;
import edu.wpi.cs3733.c20.teamS.Editing.viewModels.EdgeVm;
import edu.wpi.cs3733.c20.teamS.Editing.viewModels.NodeVm;
import edu.wpi.cs3733.c20.teamS.Editing.viewModels.RoomVm;
import edu.wpi.cs3733.c20.teamS.MainToLoginScreen;
import edu.wpi.cs3733.c20.teamS.Settings;
import edu.wpi.cs3733.c20.teamS.app.EmployeeEditor.EmployeeEditingScreen;
import edu.wpi.cs3733.c20.teamS.app.serviceRequests.ActiveServiceRequestScreen;
import edu.wpi.cs3733.c20.teamS.collisionMasks.HitboxRepository;
import edu.wpi.cs3733.c20.teamS.collisionMasks.ResourceFolderHitboxRepository;
import edu.wpi.cs3733.c20.teamS.collisionMasks.Room;
import edu.wpi.cs3733.c20.teamS.database.DatabaseController;
import edu.wpi.cs3733.c20.teamS.database.EdgeData;
import edu.wpi.cs3733.c20.teamS.database.NodeData;
import edu.wpi.cs3733.c20.teamS.database.ServiceData;
import edu.wpi.cs3733.c20.teamS.pathDisplaying.Floor;
import edu.wpi.cs3733.c20.teamS.pathDisplaying.FloorSelector;
import edu.wpi.cs3733.c20.teamS.pathDisplaying.MapZoomer;
import edu.wpi.cs3733.c20.teamS.serviceRequests.AccessLevel;
import edu.wpi.cs3733.c20.teamS.serviceRequests.Employee;
import edu.wpi.cs3733.c20.teamS.serviceRequests.SelectServiceScreen;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

public class EditScreenController implements Initializable {
    //region fields
    private Stage stage;
    private Employee loggedIn;
    private MoveNodes moveNode = new MoveNodes();
    private MapZoomer zoomer;
    private FloorSelector floorSelector;
    private ObservableGraph graph;
    private IEditingTool editingTool;

    private final DatabaseController database = new DatabaseController();
    private final HitboxRepository hitboxRepo = new ResourceFolderHitboxRepository();
    private final Group group = new Group();
    private final Set<Room> rooms = new HashSet<>();
    private ExportToDirectoryController exportController;

    private static Color getNodeColorNonHighlighted(NodeData node) {
        return node.getNodeType().equals("ELEV") ?
                Settings.get().nodeColorElevator() :
                Settings.get().nodeFillColorNormal();
    }
    //endregion

    /**
     *
     * @param stage the stage to take over
     * @param employee the employee that logged in
     */
    public EditScreenController(Stage stage, Employee employee) {
        this.stage  = stage;
        this.loggedIn = employee;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        zoomer = new MapZoomer(scrollPane);
        loggedInUserLabel.setText("Welcome " + loggedIn.name() + "!");
        editPrivilegeBox.setVisible(loggedIn.accessLevel() == AccessLevel.ADMIN);

        initGraph();
        initFloorSelector();
        initPathfindingAlgorithmSelector();

        group.setOnMouseClicked(e -> editingTool.onMapClicked(e));
        group.setOnMouseMoved(e -> editingTool.onMouseMoved(e));
        floorSelector.currentChanged()
                .subscribe(floor -> {
                    mapImage.setImage(floorSelector.floor(floor).image);
                    redrawMap();
                });

        if (hitboxRepo.canLoad())
            rooms.addAll(hitboxRepo.load());
        editingTool = createAddRemoveNodeTool();
        exportController = new ExportToDirectoryController(directoryPathTextField, exportButton, () -> rooms);

        redrawMap();
    }

    private void initGraph() {
        this.graph = new ObservableGraph(database.loadGraph());
        graph.nodeAdded().subscribe(node -> {
            database.addNode(node);
            redrawMap();
        }, e -> System.out.println(e.getMessage()));
        graph.nodeRemoved().subscribe(e -> {
            database.removeNode(e.getNodeID());
            redrawMap();
        }, e -> System.out.println(e.getMessage()));
        graph.edgeAdded().subscribe(e -> {
            database.addEdge(e.nodeU(), e.nodeV());
            redrawMap();
        }, e -> System.out.println(e.getMessage()));
        graph.edgeRemoved().subscribe(e -> {
            database.removeEdge(new EdgeData(e.nodeU(), e.nodeV()).getEdgeID());
            redrawMap();
        }, e -> System.out.println(e.getMessage()));
    }
    private void initFloorSelector() {
        floorSelector = new FloorSelector(
                upButton, downButton,
                new Floor(floorButton1, "images/Floors/HospitalFloor1.png"),
                new Floor(floorButton2, "images/Floors/HospitalFloor2.png"),
                new Floor(floorButton3, "images/Floors/HospitalFloor3.png"),
                new Floor(floorButton4, "images/Floors/HospitalFloor4.png"),
                new Floor(floorButton5, "images/Floors/HospitalFloor5.png")
        );

        floorSelector.setCurrent(2);
    }
    private void initPathfindingAlgorithmSelector() {
        PathfindingAlgorithmSelector pathfindingAlgorithmSelector = new PathfindingAlgorithmSelector(
                astarRadioButton, djikstraRadioButton,
                depthFirstRadioButton, breadthFirstRadioButton
        );
    }

    //region gui components
    @FXML private VBox editPrivilegeBox;
    @FXML private Label loggedInUserLabel;
    @FXML private ImageView mapImage;
    @FXML private ScrollPane scrollPane;
    @FXML private JFXButton floorButton1;
    @FXML private JFXButton floorButton2;
    @FXML private JFXButton floorButton3;
    @FXML private JFXButton floorButton4;
    @FXML private JFXButton floorButton5;
    @FXML private JFXButton downButton;
    @FXML private JFXButton upButton;

    @FXML private ToggleGroup pathGroup;
    @FXML private JFXButton zoomInButton;
    @FXML private JFXButton zoomOutButton;

    @FXML private VBox editToolFieldsVBox;

    @FXML private JFXButton editEmpButton;

    @FXML private RadioButton astarRadioButton;
    @FXML private RadioButton djikstraRadioButton;
    @FXML private RadioButton depthFirstRadioButton;
    @FXML private RadioButton breadthFirstRadioButton;

    @FXML private JFXButton cancelEditsButton;
    @FXML private JFXButton confirmEditButton;
    @FXML private JFXTextField directoryPathTextField;
    @FXML private JFXButton exportButton;
    //endregion

    //region event handlers
    @FXML private void onUpClicked() {
        floorSelector.setCurrent(floorSelector.current() + 1);
    }
    @FXML private void onDownClicked() {
        floorSelector.setCurrent(floorSelector.current() - 1);
    }
    @FXML private void onFloorClicked1() {
        floorSelector.setCurrent(1);
    }
    @FXML private void onFloorClicked2() {
        floorSelector.setCurrent(2);
    }
    @FXML private void onFloorClicked3() {
        floorSelector.setCurrent(3);
    }
    @FXML private void onFloorClicked4() {
        floorSelector.setCurrent(4);
    }
    @FXML private void onFloorClicked5() {
        floorSelector.setCurrent(5);
    }

    @FXML private void onEditButtonPressed(ActionEvent event) {
        EmployeeEditingScreen.showDialog();
    }

    @FXML private void onHelpClicked() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/TutorialScreen.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Help");
            window.setScene(new Scene(root1));
            window.setResizable(false);
            window.show();
        } catch (Exception e) {
            System.out.println("Can't load new window");
        }
    }
    @FXML private void onStaffClicked() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/loginScreen.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Login to the System");
            window.setScene(new Scene(root1));
            window.setResizable(false);
            window.show();
        } catch (Exception e) {
            System.out.println("Can't load new window");
        }
    }
    @FXML private void onZoomInClicked() {
        this.zoomer.zoomIn();
        zoomInButton.setDisable(!zoomer.canZoomIn());
        zoomOutButton.setDisable(!zoomer.canZoomOut());
    }
    @FXML private void onZoomOutClicked() {
        this.zoomer.zoomOut();
        zoomInButton.setDisable(!zoomer.canZoomIn());
        zoomOutButton.setDisable(!zoomer.canZoomOut());
    }
    @FXML private void onNewServiceClicked() {
        SelectServiceScreen.showDialog(loggedIn);
    }
    @FXML private void onActiveServiceClicked() {
        ObservableList<ServiceData> setOfActives = FXCollections.observableArrayList();
        DatabaseController dbc = new DatabaseController();
        Set<ServiceData> dbData = dbc.getAllServiceRequestData();
        for(ServiceData sd : dbData){
            if(!(sd.getStatus().equals("COMPLETE"))){
                setOfActives.add(sd);
                System.out.println(sd.toString());
            }
        }
        ActiveServiceRequestScreen.showDialog(setOfActives);
    }

    @FXML private void onAddRemoveNodeClicked() {
        IEditingTool tool = createAddRemoveNodeTool();
        changeEditingTool(tool);
    }
    @FXML private void onAddRemoveEdgeClicked() {
        IEditingTool tool = new AddRemoveEdgeTool(graph, () -> group);
        changeEditingTool(tool);
    }
    @FXML private void onAddRemoveHitboxClicked() {
        AddRemoveHitboxTool tool = new AddRemoveHitboxTool(
                hitbox -> {
                    rooms.remove(hitbox);
                    redrawMap();
                },
                () -> group,
                () -> floorSelector.current()
        );
        tool.hitboxAdded().subscribe(hitbox -> {
            rooms.add(hitbox);
            redrawMap();
        });
        changeEditingTool(tool);
    }
    @FXML private void onMoveNodeClicked() {
        changeEditingTool(new MoveNodeTool(scrollPane));
    }
    @FXML private void onShowInfoClicked() {
        changeEditingTool(new ShowNodeInfoTool());
    }
    @FXML private void onEditRoomEntrancesClicked() {
        IEditingTool tool = new EditHitboxTool(
                graph.nodes(),
                () -> group,
                editToolFieldsVBox
        );
        changeEditingTool(tool);
    }

    @FXML private void onConfirmEditClicked() {
        if (hitboxRepo.canSave())
            hitboxRepo.save(rooms);
    }
    @FXML private void onCancelEditClicked() {
        if (hitboxRepo.canLoad()) {
            rooms.clear();
            rooms.addAll(hitboxRepo.load());
            redrawMap();
        }
    }
    //endregion

    private void changeEditingTool(IEditingTool editingTool) {
        IEditingTool previous = this.editingTool;
        this.editingTool = editingTool;
        if (previous == null)
            return;
        previous.onClosed();
    }
    private IEditingTool createAddRemoveNodeTool() {
        return Settings.get().useQuickNodePlacingTool() ?
                new QuickAddRemoveNodeTool(graph, editToolFieldsVBox, () -> floorSelector.current()) :
                new AddRemoveNodeTool(graph, () -> floorSelector.current());
    }
    private void redrawMap() {
        double currentHval = scrollPane.getHvalue();
        double currentVval = scrollPane.getVvalue();
        moveNode.setScale(zoomer.zoomFactor());
        moveNode.setCurrent_floor(floorSelector.current());

        group.getChildren().clear();
        group.getChildren().add(mapImage);

        group.getChildren().add(drawAllRooms());
        group.getChildren().add(drawAllEdges());
        group.getChildren().add(drawAllNodes());

        scrollPane.setContent(group);

        //Keeps the zoom the same throughout each screen/floor change.
        keepCurrentPosition(currentHval, currentVval, zoomer);

    }
    private Group drawAllNodes() {
        Group group = new Group();
        Set<NodeData> nodes = graph.nodes().stream()
                .filter(node -> node.getFloor() == floorSelector.current())
                .collect(Collectors.toSet());

        for (NodeData node : nodes) {
            NodeVm vm = createNodeVm(node);
            group.getChildren().add(vm);
        }
        return group;
    }
    private Group drawAllEdges() {
        Group group = new Group();
        graph.edges().stream()
                .filter(edge -> {
                    return edge.nodeU().getFloor() == floorSelector.current() ||
                            edge.nodeV().getFloor() == floorSelector.current();
                })
                .map(edge -> createEdgeVm(edge.nodeU(), edge.nodeV()))
                .forEach(vm -> group.getChildren().add(vm));

        return group;
    }
    private Group drawAllRooms() {
        Group result = new Group();
        rooms.stream()
                .filter(room -> room.floor() == floorSelector.current())
                .map(this::createRoomVm)
                .forEach(vm -> result.getChildren().add(vm));
        return result;
    }

    private NodeVm createNodeVm(NodeData node) {
        NodeVm result = new NodeVm(node);
        result.setOnMouseClicked(e -> editingTool.onNodeClicked(node, e));
        result.setOnMouseDragged(e -> editingTool.onNodeDragged(node, e));
        result.setOnMouseReleased(e -> editingTool.onNodeReleased(node, e));

        return result;
    }
    private EdgeVm createEdgeVm(NodeData start, NodeData end) {
        EdgeVm edgeVm = new EdgeVm(start, end);
        edgeVm.setOnMouseClicked(e -> {
            EndpointPair<NodeData> edge = EndpointPair.unordered(start, end);
            editingTool.onEdgeClicked(edge, e);
        });

        return edgeVm;
    }
    private RoomVm createRoomVm(Room room) {
        RoomVm result = new RoomVm(room);
        result.setOnMouseClicked(e -> editingTool.onRoomClicked(room, e));
        return result;
    }

    public void onLogOut() {
        MainToLoginScreen back = new MainToLoginScreen(stage);
    }

    private void keepCurrentPosition(double hval, double vval, MapZoomer zoomer){
        zoomer.zoomSet();
        scrollPane.setHvalue(hval);
        scrollPane.setVvalue(vval);
    }
}
