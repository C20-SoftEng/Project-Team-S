package edu.wpi.cs3733.c20.teamS.Editing;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.c20.teamS.BaseScreen;
import edu.wpi.cs3733.c20.teamS.Editing.tools.*;
import edu.wpi.cs3733.c20.teamS.MainToLoginScreen;
import edu.wpi.cs3733.c20.teamS.Settings;
import edu.wpi.cs3733.c20.teamS.app.EmployeeEditor.EmployeeEditingScreen;
import edu.wpi.cs3733.c20.teamS.app.serviceRequests.ActiveServiceRequestScreen;
import edu.wpi.cs3733.c20.teamS.collisionMasks.HitboxRepository;
import edu.wpi.cs3733.c20.teamS.collisionMasks.ResourceFolderHitboxRepository;
import edu.wpi.cs3733.c20.teamS.collisionMasks.Room;
import edu.wpi.cs3733.c20.teamS.database.DatabaseController;
import edu.wpi.cs3733.c20.teamS.database.EdgeData;
import edu.wpi.cs3733.c20.teamS.pathDisplaying.Floor;
import edu.wpi.cs3733.c20.teamS.pathDisplaying.FloorSelector;
import edu.wpi.cs3733.c20.teamS.serviceRequests.AccessLevel;
import edu.wpi.cs3733.c20.teamS.serviceRequests.Employee;
import edu.wpi.cs3733.c20.teamS.serviceRequests.SelectServiceScreen;
import edu.wpi.cs3733.c20.teamS.utilities.rx.DisposableSelector;
import edu.wpi.cs3733.c20.teamS.utilities.rx.RxAdaptors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class EditScreenController extends BaseScreen implements Initializable {
    //region fields
    private Stage stage;
    private Employee loggedIn;
    private FloorSelector floorSelector;
    private ObservableGraph graph;
    private EditableMap editableMap;
    private DisposableSelector<EditingTool> toolSelector;
    private final UndoBuffer undoBuffer = new UndoBuffer();

    private final DatabaseController database = new DatabaseController();
    private final HitboxRepository hitboxRepo = new ResourceFolderHitboxRepository();
    private final Set<Room> rooms = new HashSet<>();

    //endregion

    public EditScreenController() {
        this.stage  = Settings.primaryStage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fakeInitialize();
    }

    public void fakeInitialize(){
        loggedInUserLabel.setText("Welcome " + Settings.loggedIn.name() + "!");
        editPrivilegeBox.setVisible(Settings.loggedIn.accessLevel() == AccessLevel.ADMIN);

        floorSelector = createFloorSelector();
        floorSelector.setCurrent(2);
        if (hitboxRepo.canLoad())
            rooms.addAll(hitboxRepo.load());
        editableMap = new EditableMap(
                database.loadGraph(),
                floorSelector, rooms,
                scrollPane, mapImage);
        graph = editableMap.graph();
        toolSelector = new DisposableSelector<>();
        toolSelector.setCurrent(new AddEditRemoveNodeTool(undoBuffer::execute, editableMap));
        createPathfindingAlgorithmSelector();
        initEventHandlers();
        ExportToDirectoryController exportController = new ExportToDirectoryController(
                directoryPathTextField, exportButton,
                () -> editableMap.rooms()
        );
        initUndoHotkeys();
    }
    private void initUndoHotkeys() {
        KeyCombination undoCombo = new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN);
        KeyCombination redoCombo = new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN);
        Runnable undo = () -> {
            if (undoBuffer.canUndo())
                undoBuffer.undo();
        };
        Runnable redo = () -> {
            if (undoBuffer.canRedo())
                undoBuffer.redo();
        };
        RxAdaptors.propertyStream(floorButton2.sceneProperty())
                .subscribe(scene -> {
                   System.out.println("Scene changed");
                   scene.getAccelerators().put(undoCombo, undo);
                   scene.getAccelerators().put(redoCombo, redo);
                });
    }
    private void initEventHandlers() {
        graph.nodeAdded().subscribe(database::addNode);
        graph.nodeRemoved().subscribe(node -> database.removeNode(node.getNodeID()));
        graph.edgeAdded().subscribe(edge -> database.addEdge(edge.nodeU(), edge.nodeV()));
        graph.edgeRemoved().subscribe(edge -> database.removeEdge(new EdgeData(edge.nodeU(), edge.nodeV()).getEdgeID()));
    }
    private FloorSelector createFloorSelector() {
        return new FloorSelector(
                upButton, downButton,
                new Floor(floorButton1, "images/Floors/HospitalFloor1.png"),
                new Floor(floorButton2, "images/Floors/HospitalFloor2.png"),
                new Floor(floorButton3, "images/Floors/HospitalFloor3.png"),
                new Floor(floorButton4, "images/Floors/HospitalFloor4.png"),
                new Floor(floorButton5, "images/Floors/HospitalFloor5.png"),
                new Floor(floorButton6, "images/Floors/HospitalFloor6.png"),
                new Floor(floorButton7, "images/Floors/HospitalFloor7.png")
        );
    }
    private PathfindingAlgorithmSelector createPathfindingAlgorithmSelector() {
        return new PathfindingAlgorithmSelector(
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
    @FXML private JFXButton floorButton6;
    @FXML private JFXButton floorButton7;
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

    @FXML private void onFloorClicked6() {
        floorSelector.setCurrent(6);
    }
    @FXML private void onFloorClicked7() {
        floorSelector.setCurrent(7);
    }

    @FXML private void onEditButtonPressed() {
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
        editableMap.zoomIn();
        zoomInButton.setDisable(!editableMap.canZoomIn());
        zoomOutButton.setDisable(!editableMap.canZoomOut());
    }
    @FXML private void onZoomOutClicked() {
        editableMap.zoomOut();
        zoomInButton.setDisable(!editableMap.canZoomIn());
        zoomOutButton.setDisable(!editableMap.canZoomOut());
    }

    @FXML private void onNewServiceClicked() {
        SelectServiceScreen.showDialog(Settings.loggedIn);
    }
    @FXML private void onActiveServiceClicked() {
//        ObservableList<ServiceData> setOfActives = FXCollections.observableArrayList();
//        DatabaseController dbc = new DatabaseController();
//        Set<ServiceData> dbData = dbc.getAllServiceRequestData();
//        for(ServiceData sd : dbData){
//            if(!(sd.getStatus().equals("COMPLETE"))){
//                setOfActives.add(sd);
//                System.out.println(sd.toString());
//            }
//        }
//        ActiveServiceRequestScreen.showDialog(setOfActives);
        ActiveServiceRequestScreen.showDialog();
    }

    @FXML private void onAddRemoveNodeClicked() {
        toolSelector.setCurrent(new AddEditRemoveNodeTool(undoBuffer::execute, editableMap));
    }
    @FXML private void onAddRemoveEdgeClicked() {
        toolSelector.setCurrent(new AddRemoveEdgeTool(undoBuffer::execute, editableMap));
    }
    @FXML private void onAddRemoveHitboxClicked() {
        toolSelector.setCurrent(new AddRemoveRoomTool(undoBuffer::execute, editableMap));
    }
    @FXML private void onMoveNodeClicked() {
        toolSelector.setCurrent(new MoveNodeTool(undoBuffer::execute, editableMap));
    }
    @FXML private void onEditRoomEntrancesClicked() {
        toolSelector.setCurrent(new EditRoomTool(undoBuffer::execute, editableMap));
    }
    @FXML private void onConfirmEditClicked() {
        if (hitboxRepo.canSave())
            hitboxRepo.save(rooms);
    }
    @FXML private void onCancelEditClicked() {
        if (hitboxRepo.canLoad()) {
            rooms.clear();
            rooms.addAll(hitboxRepo.load());
        }
    }
    //endregion

    public void onLogOut() {
        Settings.loggedIn = null;
        new MainToLoginScreen();
    }

    @FXML private JFXTextField timeOut;

    @FXML private JFXButton saveTimeOut;

    @FXML void onConfirmSaveTimeOut(ActionEvent event) {
        BaseScreen.puggy.changeTimeout(Integer.parseInt(timeOut.getText()) * 1000);
        System.out.println("Changed Timeout to: " + Integer.parseInt(timeOut.getText()) * 1000);
    }

}
