package edu.wpi.cs3733.c20.teamS.pathDisplaying;

import com.interactivemesh.jfx.importer.obj.ObjModelImporter;
import com.interactivemesh.jfx.importer.stl.StlMeshImporter;
import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.c20.teamS.database.DatabaseController;
import edu.wpi.cs3733.c20.teamS.database.NodeData;
import javafx.animation.*;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class ThreeDimensions extends Application {
    private List<NodeData> nodes;
    private Stage primaryStage = new Stage();

    public ThreeDimensions(List<NodeData> nodes) throws Exception {
        if(nodes != null) {
            this.nodes = nodes;
            start(primaryStage); }
    }

    private final float WIDTH = 1422;
    private final float HEIGHT = 800;
    private double oldX, oldY;

    @Override
    public void start(Stage primaryStage) throws Exception {
        HashMap<Integer, Integer> zplace = new HashMap<Integer, Integer>();
        zplace.put(1, 100);
        zplace.put(2, 0);
        zplace.put(3, -100);
        zplace.put(4, -200);
        zplace.put(5, -300);

        NodeData begin = nodes.get(0);
        NodeData end = nodes.get(nodes.size() - 1);

        RotateGroup group = new RotateGroup();

        for(int i = 1; i <= 5; i++) {
            boolean trans = !(begin.getFloor() == i || end.getFloor() == i);
            Image floor = new Image("images/ThreeDim/floor" + i + ".png");
            Image transFloor = new Image("images/ThreeDim/transFloor" + i + ".png");
            int z = zplace.get(i);
            group.getChildren().add(createFloor(floor, transFloor, z, trans));
        }

        Camera camera = new PerspectiveCamera();
        camera.setFarClip(10000);
        camera.setNearClip(0);
        camera.setTranslateY(-50);

        Shape destinationCircle = new Circle(10);
        destinationCircle.setStrokeWidth(3);
        destinationCircle.setStrokeLineCap(StrokeLineCap.ROUND);
        destinationCircle.setStroke(Color.AQUA);
        destinationCircle.setFill(Color.LIGHTYELLOW);
        destinationCircle.setTranslateX(end.getxCoordinate() / 5 - 247);
        destinationCircle.setTranslateZ(zplace.get(end.getFloor()) - 3);
        destinationCircle.setTranslateY(end.getyCoordinate() / 5 - 148);

        String pinPath = getClass().getResource("/images/ThreeDim/pin.STL").toURI().getPath();
        MeshView[] pin = loadMeshViews(pinPath);
        for (int i = 0; i < pin.length; i++) {
            double MODEL_SCALE_FACTOR22 = 0.15;
            pin[i].setScaleX(MODEL_SCALE_FACTOR22);
            pin[i].setScaleY(MODEL_SCALE_FACTOR22);
            pin[i].setScaleZ(MODEL_SCALE_FACTOR22);

            pin[i].setTranslateX(end.getxCoordinate() / 5 - 307);
            pin[i].setTranslateY(end.getyCoordinate() / 5 - 70);
            pin[i].setTranslateZ(zplace.get(end.getFloor()) + 45);

            PhongMaterial material = new PhongMaterial();
            Image texture = new Image(("images/ThreeDim/pinColor.jpg"));
            material.setDiffuseMap(texture);
            pin[i].setMaterial(material);
            pin[i].getTransforms().setAll(new Rotate(0, Rotate.X_AXIS), new Rotate(90, Rotate.X_AXIS));
        }

        String personPath = getClass().getResource("/images/ThreeDim/person.stl").toURI().getPath();
        MeshView[] person = loadMeshViews(personPath);
        for (int i = 0; i < person.length; i++) {
            double MODEL_SCALE_FACTOR = 3;
            person[i].setScaleX(MODEL_SCALE_FACTOR);
            person[i].setScaleY(MODEL_SCALE_FACTOR);
            person[i].setScaleZ(MODEL_SCALE_FACTOR);
            person[i].setTranslateZ(zplace.get(begin.getFloor()) + 10);

            PhongMaterial material = new PhongMaterial();
            Image texture = new Image(("images/ThreeDim/yellow.jpg"));
            material.setDiffuseMap(texture);
            person[i].setMaterial(material);
        }

        RotateGroup numberGroup = new RotateGroup();
        for(int i = 1; i <= 5; i++) {
            boolean selected = (begin.getFloor() == i || end.getFloor() == i);
            Image number = new Image("images/ThreeDim/" + i + ".png");
            Image brightNumber = new Image("images/ThreeDim/" + i + "H.png");
            int z = zplace.get(i);
            numberGroup.getChildren().add(floorNumbers(number, brightNumber, z, selected));
        }

        RotateGroup elevatorGroup = new RotateGroup();
        List<NodeData> invalidELEV = new ArrayList<>();

        for(int i = 0; i < nodes.size() - 1; i++) {
            NodeData startNode = nodes.get(i);
            NodeData endNode = nodes.get(i + 1);

            for(int j = 1; j <= 5; j++) {
                if(startNode.getFloor() == j) {
                    int radius = 2;
                    if(startNode.getNodeType().equals("ELEV")) {
                        URL elevPath = getClass().getResource("/images/ThreeDim/boneless.obj").toURI().toURL();
                        MeshView[] elevator = loadModel(elevPath);
                        for (int k = 0; k < elevator.length; k++) {
                            double MODEL_SCALE_FACTOR = 30;
                            elevator[k].setScaleX(MODEL_SCALE_FACTOR);
                            elevator[k].setScaleY(MODEL_SCALE_FACTOR);
                            elevator[k].setScaleZ(MODEL_SCALE_FACTOR);

                            elevator[k].setTranslateX(startNode.getxCoordinate() / 5 - 244);
                            elevator[k].setTranslateY(startNode.getyCoordinate() / 5 - 148);
                            elevator[k].setTranslateZ(zplace.get(startNode.getFloor()) - 60);

                            elevator[k].getTransforms().setAll(new Rotate(0, Rotate.Z_AXIS), new Rotate(90, Rotate.X_AXIS));
                            invalidELEV.add(endNode);
                        }
                        if(!invalidELEV.contains(startNode)) {
                            elevatorGroup.getChildren().addAll(elevator);
                        }
                    }
                    else {
                        Point3D startPoint = new Point3D(startNode.getxCoordinate() / 5 - 247, startNode.getyCoordinate() / 5 - 148, zplace.get(startNode.getFloor()));
                        Point3D endPoint = new Point3D(endNode.getxCoordinate() / 5 - 247, endNode.getyCoordinate() / 5 - 148, zplace.get(endNode.getFloor()));
                        group.getChildren().addAll(drawCylinder(startPoint, endPoint, radius));
                    }
                }
            }
        }

        RotateGroup personGroup = new RotateGroup();
        personGroup.getChildren().addAll(person);
        Group pinGroup = new Group(pin);
        group.getChildren().add(numberGroup);
        group.getChildren().add(personGroup);
        group.getChildren().add(pinGroup);
        group.getChildren().add(elevatorGroup);
        group.getChildren().add(destinationCircle);
        group.getChildren().add(new AmbientLight(Color.WHITE));

        personGroup.setTranslateZ(personGroup.getTranslateZ() - 27);
        personGroup.rotateByZ(-90);

        SequentialTransition st = new SequentialTransition();
        ArrayList<NodeData> floorPath = new ArrayList<>();
        for(int i = 0; i < nodes.size() - 1; i++) {
            NodeData n1 = nodes.get(i);
            NodeData n2 = nodes.get(i+1);
            boolean elev = !(n1.getFloor() == n2.getFloor());

            if(elev) {
                st.getChildren().add(getFloorPath(personGroup, floorPath));
                floorPath.clear();
                TranslateTransition tt = new TranslateTransition(Duration.seconds(Math.abs(n1.getFloor() - n2.getFloor())), personGroup);
                tt.setFromZ((zplace.get(n1.getFloor())) - 25);
                tt.setFromX(n1.getxCoordinate() / 5 - 247);
                tt.setFromY(n1.getyCoordinate() / 5 - 148);
                tt.setToZ(zplace.get(n2.getFloor()) - 25);
                tt.setToX(n1.getxCoordinate() / 5 - 247);
                tt.setToY(n1.getyCoordinate() / 5 - 148);
                tt.setCycleCount(1);

                TranslateTransition ttELEV = new TranslateTransition(Duration.seconds(Math.abs(n1.getFloor() - n2.getFloor())), elevatorGroup);
                ttELEV.setFromZ(zplace.get(n1.getFloor()));
                ttELEV.setFromX(n1.getxCoordinate() / 5 - 244);
                ttELEV.setFromY(n1.getyCoordinate() / 5 - 230);
                ttELEV.setToZ(zplace.get(n2.getFloor()));
                ttELEV.setToX(n1.getxCoordinate() / 5 - 244);
                ttELEV.setToY(n1.getyCoordinate() / 5 - 230);
                ttELEV.setCycleCount(1);

                ParallelTransition pllt = new ParallelTransition(tt, ttELEV);
                st.getChildren().addAll(pllt);
            }
            else {
                floorPath.add(n1); floorPath.add(n2);
            }

            if(i == nodes.size() - 2) {
                st.getChildren().add(getFloorPath(personGroup, floorPath));
            }
        }
        st.setCycleCount(Timeline.INDEFINITE);
        st.play();

        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            switch (event.getCode()) {
                case W:
                    group.translateZProperty().set(group.getTranslateZ() + 100);
                    break;
                case S:
                    group.translateZProperty().set(group.getTranslateZ() - 100);
                    break;
                case Q:
                    numberGroup.rotateByX(1);
                    break;
                case E:
                    numberGroup.rotateByX(-1);
                    break;
                case A:
                    group.rotateByY(10);
                    break;
                case D:
                    group.rotateByY(-10);
                    break;
                case T:
                    group.rotateByZ(-10);
                    break;
                case Y:
                    group.rotateByZ(10);
                    break;
                case G:
                    group.setTranslateY(group.getTranslateY() - 100);
                    break;
                case H:
                    group.setTranslateY(group.getTranslateY() + 100);
                    break;
            }
        });

        Group elevIcons = getElevIcons();
        group.getChildren().add(elevIcons);
        //group.getChildren().add(getFoodIcons());
        //group.getChildren().add(getRETLIcons());
        //group.getChildren().add(getSTAIcons());
        //group.getChildren().add(getRESTIcons());

        group.rotateByX(-67);
        group.translateXProperty().set(WIDTH / 2 - 50);
        group.translateYProperty().set(HEIGHT / 2);
        group.translateZProperty().set(zplace.get(begin.getFloor()) - 700);
        group.setTranslateY(group.getTranslateY() - (3-begin.getFloor()) * 100);
        group.rotateByZ(-120);
        numberGroup.rotateByZ(120);
        group.translateZProperty().set(group.getTranslateZ() + 30);

        Group root = new Group();
        root.getChildren().add(group);
        ImageView imageView = getOverlay();
        root.getChildren().add(imageView);

        Scene scene = new Scene(root, WIDTH - 192, HEIGHT, true);
        scene.setFill(Color.web("#8f8f8f"));
        scene.setCamera(camera);
        camera.setTranslateZ(zplace.get(begin.getFloor()) - 20);

        mouseControl(group, scene, primaryStage, numberGroup);

        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            switch (event.getCode()) {
                case Z:
                    imageView.setScaleX(imageView.getScaleX() - 0.005);
                    imageView.setScaleY(imageView.getScaleY() - 0.005);
                    imageView.setScaleZ(imageView.getScaleZ() - 0.005);
                    break;
                case X:
                    imageView.setScaleX(imageView.getScaleX() + 0.005);
                    imageView.setScaleY(imageView.getScaleY() + 0.005);
                    imageView.setScaleZ(imageView.getScaleZ() + 0.005);
                    break;
                case J:
                    imageView.setTranslateX(imageView.getTranslateX() - 2);
                    break;
                case K:
                    imageView.setTranslateX(imageView.getTranslateX() + 2);
                    break;
                case Y:
                    imageView.setTranslateY(imageView.getTranslateY() - 2);
                    break;
                case U:
                    imageView.setTranslateY(imageView.getTranslateY() + 2);
                    break;
            }
        });

        primaryStage.setTitle("MAP");
        primaryStage.setScene(scene);
        primaryStage.resizableProperty().set(false);
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    private MeshView[] loadModel(URL url) {
        Group modelRoot = new Group();

        ObjModelImporter importer = new ObjModelImporter();
        importer.read(url);

        return importer.getImport();
    }

    static MeshView[] loadMeshViews(String filename) {
        File file = new File(filename);
        StlMeshImporter importer = new StlMeshImporter();
        importer.read(file);
        Mesh mesh = importer.getImport();

        return new MeshView[]{new MeshView(mesh)};
    }

    public Cylinder drawCylinder(Point3D startPoint, Point3D endPoint, int radius) {
        Point3D yAxis = new Point3D(0, 1, 0);
        Point3D diff = endPoint.subtract(startPoint);
        double height = diff.magnitude();
        Point3D mid = endPoint.midpoint(startPoint);
        Translate moveToMidpoint = new Translate(mid.getX(), mid.getY(), mid.getZ());
        Point3D axisOfRotation = diff.crossProduct(yAxis);
        double angle = Math.acos(diff.normalize().dotProduct(yAxis));
        Rotate rotateAroundCenter = new Rotate(-Math.toDegrees(angle), axisOfRotation);

        Cylinder cylinder = new Cylinder(radius, height);

        Image texture = new Image(("images/ThreeDim/edgeTexture.jpg"));
        if(radius >= 5) { texture = new Image(("images/ThreeDim/elevatorTexture.png")); }

        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(texture);
        cylinder.setMaterial(material);

        cylinder.getTransforms().addAll(moveToMidpoint, rotateAroundCenter);

        return cylinder;
    }

    private Node createFloor(Image floor, Image transFloor, int z, boolean trans) {
        PhongMaterial material = new PhongMaterial();
        Box floorPlane = new Box(495, 297, 0);

        if(!trans) {material.setDiffuseMap(floor);}
        else {material.setDiffuseMap(transFloor);}

        floorPlane.setTranslateZ(z);
        floorPlane.setMaterial(material);

        return floorPlane;
    }

    class RotateGroup extends Group {
        Rotate r;
        Transform t = new Rotate();

        void rotateByX(int ang) {
            r = new Rotate(ang, Rotate.X_AXIS);
            t = t.createConcatenation(r);
            this.getTransforms().clear();
            this.getTransforms().addAll(t);
        }

        void rotateByY(int ang) {
            r = new Rotate(ang, Rotate.Y_AXIS);
            t = t.createConcatenation(r);
            this.getTransforms().clear();
            this.getTransforms().addAll(t);
        }

        void rotateByZ(int ang) {
            r = new Rotate(ang, Rotate.Z_AXIS);
            t = t.createConcatenation(r);
            this.getTransforms().clear();
            this.getTransforms().addAll(t);
        }
    }

    private Box floorNumbers(Image number, Image brightNumber, int z, boolean selected) {
        PhongMaterial material = new PhongMaterial();
        if(selected) {material.setDiffuseMap(brightNumber);}
        else {material.setDiffuseMap(number);}

        Box floorNum = new Box(40, 0, 70);
        floorNum.setTranslateZ(z);
        floorNum.setTranslateX(250);
        floorNum.setTranslateY(100);
        floorNum.setMaterial(material);

        return floorNum;
    }

    private void mouseControl(RotateGroup group, Scene scene, Stage stage, RotateGroup numberGroup) {
        scene.addEventFilter(MouseEvent.MOUSE_PRESSED, (final MouseEvent mouseEvent) -> {
            oldX = mouseEvent.getX();
            oldY = mouseEvent.getY();
        });

        scene.setOnMouseDragged(event -> {
            if(event.isPrimaryButtonDown() && !event.isControlDown()) {
                if(event.getSceneY() > oldY) { group.setTranslateY(group.getTranslateY() + 2);}
                else {group.setTranslateY(group.getTranslateY() - 2);}
                oldY = event.getSceneY();
            }
            if(event.isPrimaryButtonDown() && event.isControlDown()) {
                if(event.getSceneX() > oldX) { group.rotateByZ(-1);  numberGroup.rotateByZ(1);}
                else {group.rotateByZ(1); numberGroup.rotateByZ(-1);}
                oldX = event.getSceneX();
            }
            if(event.isSecondaryButtonDown()) {
                group.setTranslateX(group.getTranslateX() + (event.getX() - oldX));
                oldX = event.getX();
            }
        });

        stage.addEventHandler(ScrollEvent.SCROLL, event -> {
            double delta = event.getDeltaY();
            group.translateZProperty().set(group.getTranslateZ() + delta);
        });
    }

    private PathTransition getFloorPath(RotateGroup personGroup, ArrayList<NodeData> currentFloorPath) {
        Path personPath = new Path();
        personPath.getElements().add(new MoveTo(currentFloorPath.get(0).getxCoordinate() / 5 - 247, currentFloorPath.get(0).getyCoordinate() / 5 - 148));
        double length = 0;
        double prevX = currentFloorPath.get(0).getxCoordinate();
        double prevY = currentFloorPath.get(0).getyCoordinate();

        for (NodeData node : currentFloorPath) {
            personPath.getElements().add(new LineTo(node.getxCoordinate() / 5 - 247, node.getyCoordinate() / 5 - 148));
            length += Math.sqrt(Math.pow((Math.abs(prevX - node.getxCoordinate())), 2) + Math.pow((Math.abs(prevY - node.getyCoordinate())),2));
            prevX = node.getxCoordinate();
            prevY = node.getyCoordinate();
        }

        PathTransition pt = new PathTransition();
        pt.setNode(personGroup);
        pt.setDuration(Duration.seconds(length/200));
        pt.setPath(personPath);
        pt.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pt.setCycleCount(1);
        return pt;
    }

    private RotateGroup getElevIcons() {
        HashMap<Integer, Integer> zplace = new HashMap<Integer, Integer>();
        zplace.put(1, 100);
        zplace.put(2, 0);
        zplace.put(3, -100);
        zplace.put(4, -200);
        zplace.put(5, -300);

        RotateGroup elevICON = new RotateGroup();
        DatabaseController dbc = new DatabaseController();
        Set<NodeData> nd = dbc.getAllNodesOfType("ELEV");
        for(NodeData data : nd) {
            if(!nodes.contains(data)) {
                Image image = new Image("/images/ThreeDim/elevICON.png");
                ImageView imageView = new ImageView(image);
                imageView.setPreserveRatio(true);
                imageView.setTranslateX(data.getxCoordinate() / 5 - 500);
                imageView.setTranslateY(data.getyCoordinate() / 5 - 390);
                imageView.setTranslateZ(zplace.get(data.getFloor()) - 25);
                double scale = 0.04;
                imageView.setScaleX(scale);
                imageView.setScaleY(scale);
                imageView.setScaleZ(scale);
                imageView.getTransforms().add(new Rotate(90, Rotate.X_AXIS));
                elevICON.getChildren().add(imageView);
            }
        }
        return elevICON;
    }

    private RotateGroup getFoodIcons() {
        HashMap<Integer, Integer> zplace = new HashMap<Integer, Integer>();
        zplace.put(1, 100);
        zplace.put(2, 0);
        zplace.put(3, -100);
        zplace.put(4, -200);
        zplace.put(5, -300);

        ArrayList<String> validFood = new ArrayList<>();
        validFood.add("Atrium Cafe");
        validFood.add("Starbucks");
        validFood.add("Food Services");
        RotateGroup foodICON = new RotateGroup();
        DatabaseController dbc = new DatabaseController();
        Set<NodeData> nd = dbc.getAllNodesOfType("RETL");
        for(NodeData data : nd) {
            if(nodes.get(nodes.size()-1).getNodeID() != data.getNodeID()) {
                if (validFood.contains(data.getLongName())) {
                    Image image = new Image("/images/ThreeDim/foodICON.png");
                    ImageView imageView = new ImageView(image);
                    imageView.setPreserveRatio(true);
                    imageView.setTranslateX(data.getxCoordinate() / 5 - 1070);
                    imageView.setTranslateY(data.getyCoordinate() / 5 - 910);
                    imageView.setTranslateZ(zplace.get(data.getFloor()) - 25);
                    double scale = 0.01;
                    imageView.setScaleX(scale);
                    imageView.setScaleY(scale);
                    imageView.setScaleZ(scale);
                    imageView.getTransforms().add(new Rotate(90, Rotate.X_AXIS));
                    foodICON.getChildren().add(imageView);
                }
            }
        }
        return foodICON;
    }

    private RotateGroup getRETLIcons() {
        HashMap<Integer, Integer> zplace = new HashMap<Integer, Integer>();
        zplace.put(1, 100);
        zplace.put(2, 0);
        zplace.put(3, -100);
        zplace.put(4, -200);
        zplace.put(5, -300);

        ArrayList<String> validFood = new ArrayList<>();
        validFood.add("Atrium Cafe");
        validFood.add("Starbucks");
        validFood.add("Food Services");

        RotateGroup retailICON = new RotateGroup();
        DatabaseController dbc = new DatabaseController();
        Set<NodeData> nd = dbc.getAllNodesOfType("RETL");
        for(NodeData data : nd) {
            if(nodes.get(nodes.size()-1).getNodeID() != data.getNodeID()) {
                if (!validFood.contains(data.getLongName())) {
                    Image image = new Image("/images/ThreeDim/retailICON.png");
                    ImageView imageView = new ImageView(image);
                    imageView.setPreserveRatio(true);
                    imageView.setTranslateX(data.getxCoordinate() / 5 - 500);
                    imageView.setTranslateY(data.getyCoordinate() / 5 - 390);
                    imageView.setTranslateZ(zplace.get(data.getFloor()) - 25);
                    double scale = 0.04;
                    imageView.setScaleX(scale);
                    imageView.setScaleY(scale);
                    imageView.setScaleZ(scale);
                    imageView.getTransforms().add(new Rotate(90, Rotate.X_AXIS));
                    retailICON.getChildren().add(imageView);
                }
            }
        }
        return retailICON;
    }

    private RotateGroup getSTAIcons() {
        HashMap<Integer, Integer> zplace = new HashMap<Integer, Integer>();
        zplace.put(1, 100);
        zplace.put(2, 0);
        zplace.put(3, -100);
        zplace.put(4, -200);
        zplace.put(5, -300);

        RotateGroup staiICON = new RotateGroup();
        DatabaseController dbc = new DatabaseController();
        Set<NodeData> nd = dbc.getAllNodesOfType("STAI");
        for(NodeData data : nd) {
            if (nodes.get(nodes.size() - 1).getNodeID() != data.getNodeID()) {
                Image image = new Image("/images/ThreeDim/stairsICON.png");
                ImageView imageView = new ImageView(image);
                imageView.setPreserveRatio(true);
                imageView.setTranslateX(data.getxCoordinate() / 5 - 1000);
                imageView.setTranslateY(data.getyCoordinate() / 5 - 930);
                imageView.setTranslateZ(zplace.get(data.getFloor()) - 20);
                double scale = 0.01;
                imageView.setScaleX(scale);
                imageView.setScaleY(scale);
                imageView.setScaleZ(scale);
                imageView.getTransforms().add(new Rotate(90, Rotate.X_AXIS));
                staiICON.getChildren().add(imageView);
            }
        }
        return staiICON;
    }

    private RotateGroup getRESTIcons() {
        HashMap<Integer, Integer> zplace = new HashMap<Integer, Integer>();
        zplace.put(1, 100);
        zplace.put(2, 0);
        zplace.put(3, -100);
        zplace.put(4, -200);
        zplace.put(5, -300);

        RotateGroup restICON = new RotateGroup();
        DatabaseController dbc = new DatabaseController();
        Set<NodeData> nd = dbc.getAllNodesOfType("REST");
        for(NodeData data : nd) {
            if (nodes.get(nodes.size() - 1).getNodeID() != data.getNodeID()) {
                Image image = new Image("/images/ThreeDim/restICON.png");
                ImageView imageView = new ImageView(image);
                imageView.setPreserveRatio(true);
                imageView.setTranslateX(data.getxCoordinate() / 5 - 900);
                imageView.setTranslateY(data.getyCoordinate() / 5 - 650);
                imageView.setTranslateZ(zplace.get(data.getFloor()) - 20);
                double scale = 0.02;
                imageView.setScaleX(scale);
                imageView.setScaleY(scale);
                imageView.setScaleZ(scale);
                imageView.getTransforms().add(new Rotate(90, Rotate.X_AXIS));
                restICON.getChildren().add(imageView);
            }
        }
        return restICON;
    }

    private ImageView getOverlay() {
        Image image = new Image("/images/ThreeDim/overlay1.png");
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setTranslateX(-442);
        imageView.setTranslateY(-542);
        imageView.setTranslateZ(0);
        double scale = 0.67;
        imageView.setScaleX(scale);
        imageView.setScaleY(scale);
        imageView.setScaleZ(scale);


        return imageView;
    }
}