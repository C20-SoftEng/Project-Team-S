package edu.wpi.cs3733.c20.teamS.Editing;

import com.google.common.graph.EndpointPair;
import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.c20.teamS.Editing.tools.*;
import edu.wpi.cs3733.c20.teamS.collisionMasks.Hitbox;
import edu.wpi.cs3733.c20.teamS.collisionMasks.HitboxRepository;
import edu.wpi.cs3733.c20.teamS.collisionMasks.ResourceFolderHitboxRepository;
import edu.wpi.cs3733.c20.teamS.pathDisplaying.MapZoomer;

import edu.wpi.cs3733.c20.teamS.app.serviceRequests.ActiveServiceRequestScreen;
import edu.wpi.cs3733.c20.teamS.database.ServiceData;
import edu.wpi.cs3733.c20.teamS.pathfinding.*;
import edu.wpi.cs3733.c20.teamS.serviceRequests.*;

import edu.wpi.cs3733.c20.teamS.serviceRequests.Employee;
import edu.wpi.cs3733.c20.teamS.serviceRequests.SelectServiceScreen;

import edu.wpi.cs3733.c20.teamS.database.DatabaseController;
import edu.wpi.cs3733.c20.teamS.database.NodeData;
import edu.wpi.cs3733.c20.teamS.MainToLoginScreen;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeType;
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
    private final Set<Hitbox> hitboxes = new HashSet<>();
    //endregion

    private static class Floor {
        public final Image image;
        public final JFXButton button;

        public Floor(JFXButton button, Image image) {
            this.image = image;
            this.button = button;
        }
        public Floor(JFXButton button, String imagePath) {
            this(button, new Image(imagePath));
        }
    }
    private class FloorSelector {
        private static final String SELECTED_BUTTON_STYLE = "-fx-background-color: #f6bd38; -fx-font: 32 System;";
        private static final String UNSELECTED_BUTTON_STYLE = "-fx-background-color: #ffffff; -fx-font: 22 System;";
        private final Floor[] floors_;
        private final JFXButton upButton;
        private final JFXButton downButton;
        private int current;
        private final int lowestFloor;
        private final int highestFloor;

        public FloorSelector(JFXButton upButton, JFXButton downButton, Floor... floors) {
            this.upButton = upButton;
            this.downButton = downButton;
            this.floors_ = floors;
            lowestFloor = 1;
            highestFloor = floors_.length;
        }

        public int current() {
            return current;
        }
        public void setCurrent(int floorNumber) {
            for (Floor floor : floors_) {
                floor.button.setStyle(UNSELECTED_BUTTON_STYLE);
            }
            floor(floorNumber).button.setStyle(SELECTED_BUTTON_STYLE);
            this.upButton.setDisable(floorNumber == this.highestFloor);
            this.downButton.setDisable(floorNumber == this.lowestFloor);

            mapImage.setImage(floor(floorNumber).image);
            this.current = floorNumber;
            redrawMap();
        }

        private Floor floor(int floorNumber) {
            return floors_[floorNumber - 1];
        }
    }

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

        group.setOnMouseClicked(e -> editingTool.onMapClicked(e));
        group.setOnMouseMoved(e -> editingTool.onMouseMoved(e));

        editingTool = new AddRemoveNodeTool(graph, () -> floorSelector.current());
        if (hitboxRepo.canLoad())
            hitboxes.addAll(hitboxRepo.load());

        redrawMap();
    }

    private void initGraph() {
        this.graph = new ObservableGraph(database.loadGraph());
        graph.nodeAdded().subscribe(node -> {
            database.addNode(node, graph.nodes());
            redrawMap();
        });
        graph.nodeRemoved().subscribe(e -> redrawMap());
        graph.edgeAdded().subscribe(e -> redrawMap());
        graph.edgeRemoved().subscribe(e -> redrawMap());
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
    @FXML private JFXButton cancelEditsButton;
    @FXML private JFXButton confirmEditButton;
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
        editingTool = new AddRemoveNodeTool(graph, () -> floorSelector.current());
    }
    @FXML private void onAddRemoveEdgeClicked() {
        editingTool = new AddRemoveEdgeTool(graph, () -> group);
    }
    @FXML private void onAddRemoveHitboxClicked() {
        AddRemoveHitboxTool tool;
        editingTool = tool = new AddRemoveHitboxTool(
                hitbox -> {
                    hitboxes.remove(hitbox);
                    redrawMap();
                },
                () -> group,
                () -> floorSelector.current()
        );
        tool.hitboxAdded().subscribe(hitbox -> {
            hitboxes.add(hitbox);
            redrawMap();
        });
    }
    @FXML private void onMoveNodeClicked() {
        editingTool = new MoveNodeTool(scrollPane);
    }

    @FXML private void onConfirmEditClicked() {
        if (hitboxRepo.canSave())
            hitboxRepo.save(hitboxes);
    }
    @FXML private void onCancelEditClicked() {
        if (hitboxRepo.canLoad()) {
            hitboxes.clear();
            hitboxes.addAll(hitboxRepo.load());
            redrawMap();
        }
    }
    //endregion

    private void redrawMap() {
        double currentHval = scrollPane.getHvalue();
        double currentVval = scrollPane.getVvalue();
        moveNode.setScale(zoomer.zoomFactor());
        moveNode.setCurrent_floor(floorSelector.current());

        group.getChildren().clear();
        group.getChildren().add(mapImage);

        group.getChildren().add(drawAllHitboxes());
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
            drawCircle(group, node);
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
                .forEach(edge -> drawLine(group, edge.nodeU(), edge.nodeV()));

        return group;
    }
    private Group drawAllHitboxes() {
        Group result = new Group();
        hitboxes.stream()
                .filter(hitbox -> hitbox.floor() == floorSelector.current())
                .map(hitbox -> drawHitbox(hitbox))
                .forEach(polygon -> result.getChildren().add(polygon));
        return result;
    }

    private void drawCircle(Group group, NodeData node) {
        Circle circle = new Circle(node.getxCoordinate(), node.getyCoordinate(), 25);
        circle.setStroke(Color.ORANGE);
        final Color normal = node.getNodeType().equals("ELEV") ?
                Color.GREEN.deriveColor(1, 1, 1, 0.5) :
                Color.ORANGE.deriveColor(1, 1, 1, 0.5);
        final Color highlighted = Color.AQUA.deriveColor(1, 1, 1, 0.5);
        circle.setFill(normal);
        circle.setOnMouseEntered(e -> {
            circle.setFill(highlighted);
            circle.setStroke(highlighted);
            circle.setStrokeWidth(3);
            circle.setStrokeType(StrokeType.OUTSIDE);
        });
        circle.setOnMouseExited(e -> {
            circle.setFill(normal);
            circle.setStroke(normal);
            circle.setStrokeWidth(1);
            circle.setStrokeType(StrokeType.CENTERED);

        });

        group.getChildren().add(circle);
        node.positionChanged().subscribe(position -> {
            circle.setCenterX(position.getX());
            circle.setCenterY(position.getY());
        });
        circle.setOnMouseClicked(e -> editingTool.onNodeClicked(node, e));
        circle.setOnMouseReleased(e -> editingTool.onNodeReleased(node, e));
        circle.setOnMouseDragged(e -> editingTool.onNodeDragged(node, e));
    }
    private void drawLine(Group group, NodeData start, NodeData end) {
        Line line = createEdgeLine(
                start.getxCoordinate(), start.getyCoordinate(),
                end.getxCoordinate(), end.getyCoordinate());
        group.getChildren().add(line);

        line.setOnMouseClicked(e -> {
            EndpointPair<NodeData> edge = EndpointPair.unordered(start, end);
            editingTool.onEdgeClicked(edge, e);
        });
        start.positionChanged().subscribe(e -> updateLinePosition(line, start, end));
        end.positionChanged().subscribe(e -> updateLinePosition(line, start, end));
    }
    private Polygon drawHitbox(Hitbox hitbox) {
        Polygon result = hitbox.toPolygon();
        result.setFill(Color.BLUE.deriveColor(1, 1, 1, .45));
        result.setOnMouseClicked(e -> editingTool.onHitboxClicked(hitbox, e));
        return result;
    }

    /**
     * Creates a line used for rendering edges.
     */
    private Line createEdgeLine(double startX, double startY, double endX, double endY) {
        Line line = new Line();
        line.setStartX(startX);
        line.setStartY(startY);
        line.setEndX(endX);
        line.setEndY(endY);
        final Color normal = Color.BLUE;
        final Color highlight = Color.AQUA;
        line.setStroke(Color.BLUE);
        line.setFill(Color.BLUE.deriveColor(1, 1, 1, 0.5));
        line.setStrokeWidth(5);
        line.setOnMouseEntered(e -> line.setStroke(highlight));
        line.setOnMouseExited(e -> line.setStroke(normal));

        return line;
    }
    private void updateLinePosition(Line line, NodeData start, NodeData end) {
        line.setStartX(start.getxCoordinate());
        line.setStartY(start.getyCoordinate());
        line.setEndX(end.getxCoordinate());
        line.setEndY(end.getyCoordinate());
    }

    public void onLogOut() {

        //IPathfinding pathfinder = new AStar();
        switch(((RadioButton)pathGroup.getSelectedToggle()).getText()){
            case "A*":
                Settings.settings().setPathFinder(new AStar());
            case "BreadthFirst":
                Settings.settings().setPathFinder(new BreadthFirst());
                break;
            case "DepthFirst":
                Settings.settings().setPathFinder(new DepthFirst());
                break;
        }
        MainToLoginScreen back = new MainToLoginScreen(stage, Settings.settings().pathfinder());
    }

    private void keepCurrentPosition(double Hval, double Vval, MapZoomer zoomer){
        zoomer.zoomSet();
        scrollPane.setHvalue(Hval);
        scrollPane.setVvalue(Vval);
    }
}
