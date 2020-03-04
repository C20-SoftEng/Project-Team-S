package edu.wpi.cs3733.c20.teamS.pathDisplaying;

import animatefx.animation.Pulse;
import animatefx.animation.*;
import com.google.common.graph.MutableGraph;
import com.jfoenix.controls.JFXButton;
import com.sun.javafx.application.PlatformImpl;
import com.sun.javafx.css.StyleManager;
import edu.wpi.cs3733.c20.teamS.*;
import edu.wpi.cs3733.c20.teamS.collisionMasks.HitboxRepository;
import edu.wpi.cs3733.c20.teamS.collisionMasks.ResourceFolderHitboxRepository;
import edu.wpi.cs3733.c20.teamS.collisionMasks.Room;
import edu.wpi.cs3733.c20.teamS.database.DatabaseController;
import edu.wpi.cs3733.c20.teamS.database.NodeData;
import edu.wpi.cs3733.c20.teamS.pathDisplaying.viewModels.RoomDisplayVm;
import edu.wpi.cs3733.c20.teamS.pathfinding.IPathfinder;
import edu.wpi.cs3733.c20.teamS.pathfinding.Path;
import edu.wpi.cs3733.c20.teamS.pathfinding.WrittenInstructions;
import edu.wpi.cs3733.c20.teamS.utilities.numerics.Vector2;
import edu.wpi.cs3733.c20.teamS.utilities.rx.RxAdaptors;
import edu.wpi.cs3733.c20.teamS.widgets.AutoComplete;
import edu.wpi.cs3733.c20.teamS.widgets.LookupResult;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
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
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MainScreenController implements Initializable {
    //region fields
    private Stage stage;
    private PathRenderer renderer;
    private NodeSelector nodeSelector;
    private MapZoomer zoomer;
    public static FloorSelector floorSelector;
    private MutableGraph<NodeData> graph;
    private final Group group = new Group();
    private final HitboxRepository hitboxRepo = new ResourceFolderHitboxRepository();
    private final Set<Room> rooms = new HashSet<>();
    private final Image Ra = new Image("images/Icons/DarkMode_Sun.png", 160, 160, false, true);
    private final Image Khons = new Image("images/Icons/DarkMode_Moon.png", 160, 160, false, true);

    private boolean flip = true;
    private boolean darkmode = false;
    //endregion

    public MainScreenController(Stage stage){
        if (stage == null) ThrowHelper.illegalNull("stage");
        this.stage = stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        zoomer = new MapZoomer(scrollPane);
        initGraph();
        renderer = new PathRenderer();
        initFloorSelector();
        initNodeSelector();
        initHitboxes();
        initDirectorySidebar();
        initSearchComboBox();
        initDirectoryPathfinding();

        scrollPane.setContent(group);
        redraw();
    }

    public void clearPathDisplay() {
        nodeSelector.reset();
    }

    private void initDirectorySidebar() {
        popDeptList();
        popLabList();
        popServList();
        popInfoList();
        popShopList();
        popRestRoomList();
        popConfList();
        popExitList();
    }
    private void initNodeSelector() {
        nodeSelector = new NodeSelector(graph, pathfinder(), () -> floorSelector.current());
        nodeSelector.pathChanged().subscribe(path -> {
            redraw();
            renderer.printInstructions(path, instructionVBox, directoryVBox);
        });
        nodeSelector.startChanged()
                .subscribe(pin -> {
                    String text = pin.isPresent() ? pin.get().room().name() : "";
                    location1.setText(text);
                });
        nodeSelector.goalChanged()
                .subscribe(pin -> {
                    String text = pin.isPresent() ? pin.get().room().name() : "";
                    location2.setText(text);
                });
    }
    private void initHitboxes() {
        rooms.addAll(hitboxRepo.load());
    }
    private void initGraph() {
        DatabaseController database = new DatabaseController();
        graph = database.loadGraph();
    }
    private void initFloorSelector() {
        floorSelector = new FloorSelector(
                upButton, downButton,
                new Floor(floorButton1, "images/Floors/HospitalFloor1.png"),
                new Floor(floorButton2, "images/Floors/HospitalFloor2.png"),
                new Floor(floorButton3, "images/Floors/HospitalFloor3.png"),
                new Floor(floorButton4, "images/Floors/HospitalFloor4.png"),
                new Floor(floorButton5, "images/Floors/HospitalFloor5.png"),
                new Floor(floorButton6, "images/Floors/HospitalFloor6.png"),
                new Floor(floorButton7, "images/Floors/HospitalFloor7.png")
        );
        floorSelector.setCurrent(2);
        floorSelector.currentChanged().subscribe(e -> redraw());
    }
    private void initSearchComboBox() {
        String fontFamily = searchComboBox.getEditor().getFont().getFamily();
        Font font = new Font(fontFamily, 18);
        searchComboBox.getEditor().setFont(font);

        AutoComplete.start(rooms, searchComboBox, Room::name);
        AutoComplete.propertyStream(searchComboBox.valueProperty())
                .subscribe(result -> {
                    Room room = result.value();
                    Vector2 centroid = room.vertices().stream()
                            .reduce(new Vector2(0, 0), Vector2::add)
                            .divide(Math.max(1, room.vertices().size()));
                    floorSelector.setCurrent(room.floor());
                    nodeSelector.onRoomClicked(room, centroid.x(), centroid.y());
                });
    }
    private IPathfinder pathfinder() {
        return Settings.get().pathfinder();
    }

    private void redraw() {
        double currentHval = scrollPane.getHvalue();
        double currentVval = scrollPane.getVvalue();

        mapImage.setImage(floorSelector.floor(floorSelector.current()).image);
        zoomer.zoomSet();

        group.getChildren().clear();
        group.getChildren().add(mapImage);

        Group pathGroup = renderer.draw(nodeSelector.path(), floorSelector.current());
        group.getChildren().add(pathGroup);

        rooms.stream()
                .filter(hitbox -> hitbox.floor() == floorSelector.current())
                .map(this::createHitboxRenderingMask)
                .forEach(polygon -> group.getChildren().add(polygon));

        maintainScrollPosition(currentHval, currentVval);
    }
    private void maintainScrollPosition(double currentHval, double currentVval) {
        int nodesOnFloor = (int)StreamSupport.stream(nodeSelector.path().spliterator(), false)
                .filter(node -> node.getFloor() == floorSelector.current())
                .count();

        if (nodesOnFloor == 0)
            keepCurrentPosition(currentHval, currentVval, zoomer);
        else {
            Vector2 centroid = findPathCentroid(nodeSelector.path(), floorSelector.current());
            double hval = centroid.x() / scrollPane.getContent().getBoundsInLocal().getWidth();
            double vval = centroid.y() / scrollPane.getContent().getBoundsInLocal().getHeight();
            //0.5 is hardcoded offset to fix centering
            keepCurrentPosition(hval+0.5, vval, zoomer);
        }
    }
    private Vector2 findPathCentroid(Path path, int floor) {
        List<Vector2> vertices = path.startToFinish().stream()
                .filter(node -> node.getFloor() == floor)
                .map(node -> new Vector2(node.getxCoordinate(), node.getyCoordinate()))
                .collect(Collectors.toList());
        return vertices.stream()
                .reduce(Vector2.ZERO, Vector2::add)
                .divide(vertices.size());
    }
    private RoomDisplayVm createHitboxRenderingMask(Room room) {
        RoomDisplayVm result = new RoomDisplayVm(room);
        result.setTranslateY(result.getTranslateY() - 10);
        RxAdaptors.eventStream(result::setOnMouseClicked)
                .map(e -> result.localToParent(e.getX(), e.getY()))
                .subscribe(point -> nodeSelector.onRoomClicked(room, point.getX(), point.getY()));
        return result;
    }
    private void keepCurrentPosition(double Hval, double Vval, MapZoomer zoomer){
        zoomer.zoomSet();
        scrollPane.setHvalue(Hval);
        scrollPane.setVvalue(Vval);
    }

    //region ui widgets
    @FXML private ImageView mapImage;
    @FXML private ImageView darkModeImage;
    @FXML private ScrollPane scrollPane;
    @FXML private JFXButton floorButton1;
    @FXML private JFXButton floorButton2;
    @FXML private JFXButton floorButton3;
    @FXML private JFXButton floorButton4;
    @FXML private JFXButton floorButton5;
    @FXML private JFXButton floorButton6;
    @FXML private JFXButton floorButton7;
    @FXML private JFXButton downButton;
    @FXML private JFXButton upButton;
    @FXML private JFXButton viewThreeD;
    @FXML private JFXButton DarkModeButton;
    @FXML private Label location1;
    @FXML private VBox instructionVBox;
    @FXML private VBox directoryVBox;
    @FXML private JFXButton zoomInButton;
    @FXML private JFXButton zoomOutButton;
    @FXML private Label location2;
    @FXML private ComboBox<LookupResult<Room>> searchComboBox;
    @FXML private TitledPane AccDEPT;
    @FXML private TitledPane AccSERV;
    @FXML private TitledPane AccLABS;
    @FXML private TitledPane AccINFO;
    @FXML private TitledPane AccRETL;
    @FXML private TitledPane AccREST;
    @FXML private TitledPane AccCONF;
    @FXML private TitledPane AccEXIT;

    @FXML private ListView<LookupResult<NodeData>> deptList;
    @FXML private ListView<LookupResult<NodeData>> servList;
    @FXML private ListView<LookupResult<NodeData>> labList;
    @FXML private ListView<LookupResult<NodeData>> infoList;
    @FXML private ListView<LookupResult<NodeData>> shopList;
    @FXML private ListView<LookupResult<NodeData>> restRoomList;
    @FXML private ListView<LookupResult<NodeData>> confList;
    @FXML private ListView<LookupResult<NodeData>> exitList;
    //endregion

    private void initDirectoryPathfinding(){
        deptList.getSelectionModel().selectedItemProperty()
                .addListener((a, b, current) -> {
                            nodeSelector.onNodeClicked(current.value());
                        }
                );

        servList.getSelectionModel().selectedItemProperty()
                .addListener((a, b, current) -> {
                    nodeSelector.onNodeClicked(current.value());
                });

        labList.getSelectionModel().selectedItemProperty()
                .addListener((a, b, current) -> {
                    nodeSelector.onNodeClicked(current.value());
                });

        infoList.getSelectionModel().selectedItemProperty()
                .addListener((a, b, current) -> {
                    nodeSelector.onNodeClicked(current.value());
                });

        shopList.getSelectionModel().selectedItemProperty()
                .addListener((a, b, current) -> {
                    nodeSelector.onNodeClicked(current.value());
                });

        restRoomList.getSelectionModel().selectedItemProperty()
                .addListener((a, b, current) -> {
                    nodeSelector.onNodeClicked(current.value());
                });

        confList.getSelectionModel().selectedItemProperty()
                .addListener((a, b, current) -> {
                    nodeSelector.onNodeClicked(current.value());
                });

        exitList.getSelectionModel().selectedItemProperty()
                .addListener((a, b, current) -> {
                    nodeSelector.onNodeClicked(current.value());
                });
    }

    private void popDeptList(){
        //region sidebar directory
        ObservableList<LookupResult<NodeData>> deptLocs = FXCollections.observableArrayList();
        DatabaseController dbController = new DatabaseController();
        Set<NodeData> deptNodes = dbController.getAllNodesOfType("DEPT");
        for(NodeData node : deptNodes){
            deptLocs.add(new LookupResult<>(node.getLongName(), node));
        }
        deptList.setItems(deptLocs);
    }
    private void popServList(){
        ObservableList<LookupResult<NodeData>> servLocs = FXCollections.observableArrayList();
        DatabaseController dbController = new DatabaseController();
        Set<NodeData> servNodes = dbController.getAllNodesOfType("SERV");
        for (NodeData node : servNodes){
            servLocs.add(new LookupResult<>(node.getLongName(), node));
        }
        servList.setItems(servLocs);
    }
    private void popLabList(){
        ObservableList<LookupResult<NodeData>> labLocs = FXCollections.observableArrayList();
        DatabaseController dbController = new DatabaseController();
        Set<NodeData> labNodes = dbController.getAllNodesOfType("LABS");
        for (NodeData node : labNodes){
            labLocs.add(new LookupResult<>(node.getLongName(), node));
        }
        labList.setItems(labLocs);
    }
    private void popInfoList(){
        ObservableList<LookupResult<NodeData>> infoLocs = FXCollections.observableArrayList();
        DatabaseController dbController = new DatabaseController();
        Set<NodeData> infoNodes = dbController.getAllNodesOfType("INFO");
        for (NodeData node : infoNodes){
            infoLocs.add(new LookupResult<>(node.getLongName(), node));
        }
        infoList.setItems(infoLocs);
    }
    private void popShopList(){
        ObservableList<LookupResult<NodeData>> shopLocs = FXCollections.observableArrayList();
        DatabaseController dbController = new DatabaseController();
        Set<NodeData> shopNodes = dbController.getAllNodesOfType("RETL");
        for (NodeData node : shopNodes){
            shopLocs.add(new LookupResult<>(node.getLongName(), node));
        }
        shopList.setItems(shopLocs);
    }
    private void popRestRoomList(){
        ObservableList<LookupResult<NodeData>> restRoomLocs = FXCollections.observableArrayList();
        DatabaseController dbController = new DatabaseController();
        Set<NodeData> restRoomNodes = dbController.getAllNodesOfType("REST");
        for (NodeData node : restRoomNodes){
            restRoomLocs.add(new LookupResult<>(node.getLongName(), node));
        }
        restRoomList.setItems(restRoomLocs);
    }
    private void popConfList(){
        ObservableList<LookupResult<NodeData>> confLocs = FXCollections.observableArrayList();
        DatabaseController dbController = new DatabaseController();
        Set<NodeData> confNodes = dbController.getAllNodesOfType("CONF");
        for (NodeData node : confNodes){
            confLocs.add(new LookupResult<>(node.getLongName(), node));
        }
        confList.setItems(confLocs);
    }
    private void popExitList(){
        ObservableList<LookupResult<NodeData>> exitLocs = FXCollections.observableArrayList();
        DatabaseController dbController = new DatabaseController();
        Set<NodeData> exitNodes = dbController.getAllNodesOfType("EXIT");
        for (NodeData node : exitNodes){
            exitLocs.add(new LookupResult<>(node.getLongName(), node));
        }
        exitList.setItems(exitLocs);
    }
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
    @FXML private void onViewThreeD() throws Exception {
        if(nodeSelector.goal().isPresent()) {
        ThreeDimensions view = new ThreeDimensions(renderer.getTDnodes(), location2.getText(), nodeSelector.goal());}
    }

    @FXML private void onFloorClicked6() {
        floorSelector.setCurrent(6);
    }
    @FXML private void onFloorClicked7() {
        floorSelector.setCurrent(7);
    }

    @FXML private void onAboutClicked() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/AboutMe.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            //Parent  root1 = fxmlLoader.getRoot();
            Stage window = new Stage();
           window.initModality(Modality.WINDOW_MODAL);
           window.setFullScreen(false);
            window.setScene(new Scene(root));
            window.setResizable(false);
            Settings.openWindows.add(window);
            //Settings.openWindows.add(this.stage);
            BaseScreen.puggy.register(window.getScene(), Event.ANY);
            window.show();
        } catch (IOException e) {
            System.out.println("Can't load new window");
        }
    }
    @FXML private void onStaffClicked() {
        LoginScreen.showDialog(this.stage);
    }
    @FXML private void onSwapButtonPressed() {
        String temp = location2.getText();
        location2.setText(location1.getText());
        location1.setText(temp);
    }
    @FXML private void onZoomInClicked() {
        zoomer.zoomIn();
        zoomInButton.setDisable(!zoomer.canZoomIn());
        zoomOutButton.setDisable(!zoomer.canZoomOut());
        //BaseScreen.puggy.changeTimeout(15000);
    }
    @FXML private void onZoomOutClicked() {
        //Node content = scrollPane.getContent();
        this.zoomer.zoomOut();
        zoomOutButton.setDisable(!zoomer.canZoomOut());
        zoomInButton.setDisable(!zoomer.canZoomIn());
    }
    @FXML private void onTextClicked(){
        WrittenInstructions wr = new WrittenInstructions(renderer.getTDnodes());
        SendTextDirectionsScreen.showDialog(wr.directions());
    }


    @FXML private void onToTextClicked(){
        this.directoryVBox.setVisible(false);
        this.instructionVBox.setVisible(true);
    }

    @FXML private void onToDirectoryClicked(){
        this.instructionVBox.setVisible(false);
        this.directoryVBox.setVisible(true);
    }

    @FXML private void onDarkModeClicked(){
        if (darkmode){
            Application.setUserAgentStylesheet("modena.css");
            StyleManager.getInstance().addUserAgentStylesheet("default.css");
//            stage.getScene().getStylesheets().add("default.css");
//            stage.getScene().getStylesheets().remove("darkmode.css");
            darkmode = false;
            //set image to dark mode button
            darkModeImage.setImage(Khons);
            System.out.println("returned to light mode");
        }
        else {
            Application.setUserAgentStylesheet("modena.css");
            StyleManager.getInstance().addUserAgentStylesheet("darkmode.css");
//            stage.getScene().getStylesheets().add("darkmode.css");
//            stage.getScene().getStylesheets().remove("default.css");
            darkmode = true;
            //set image to light mode button
            darkModeImage.setImage(Ra);
            System.out.println("changed to dark mode");
        }
    }


//    @FXML
//    void animate() {
//        new Pulse(zoomInButton).play();
//    }

    @FXML
    void animate3D() {
        new Pulse(viewThreeD).play();
    }

    @FXML
    void animateFloor1() {
        new Pulse(floorButton1).play();
    }

    @FXML
    void animateFloor2() {
        new Pulse(floorButton2).play();
    }

    @FXML
    void animateFloor3() {
        new Pulse(floorButton3).play();
    }

    @FXML
    void animateFloor4() {
        new Pulse(floorButton4).play();
    }

    @FXML
    void animateFloor5() {
        new Pulse(floorButton5).play();
    }

    @FXML
    void animateFloor6() {
        new Pulse(floorButton6).play();
    }

    @FXML
    void animateFloor7() {
        new Pulse(floorButton6).play();
    }

    @FXML
    void animateFloorDown() {
        new Pulse(downButton).play();
    }

    @FXML
    void animateFloorUp() {
        new Pulse(upButton).play();
    }

    @FXML
    void animateZoomIn() {
        new Pulse(zoomInButton).play();
    }

    @FXML
    void animateZoomOut() {
        new Pulse(zoomOutButton).play();
    }
    //endregion
}