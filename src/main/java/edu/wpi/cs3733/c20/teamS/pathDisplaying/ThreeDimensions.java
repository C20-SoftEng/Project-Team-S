package edu.wpi.cs3733.c20.teamS.pathDisplaying;

import com.interactivemesh.jfx.importer.obj.ObjModelImporter;
import com.interactivemesh.jfx.importer.stl.StlMeshImporter;
import edu.wpi.cs3733.c20.teamS.database.DatabaseController;
import edu.wpi.cs3733.c20.teamS.database.NodeData;
import edu.wpi.cs3733.c20.teamS.utilities.numerics.Vector2;
import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
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
import java.util.*;

public class ThreeDimensions extends Application {
    private List<NodeData> nodes;
    private Stage primaryStage = new Stage();
    private String goal = "";
    private List<Vector2> goalLine;


    public ThreeDimensions(List<NodeData> nodes, String goal, Optional<PinDrop> goalRoom) throws Exception {
        if(goalRoom.isPresent()) {
        if(nodes != null) {
            goalLine = goalRoom.get().room().vertices();
            this.nodes = nodes;
            this.goal = goal;
            start(primaryStage); }}
    }

    private final float WIDTH = 1422;
    private final float HEIGHT = 800;
    private double oldX, oldY;
    private ArrayList<String> floorAddress = new ArrayList<>();
    private boolean elevToggle = true;
    private boolean foodToggle = true;
    private boolean restToggle = true;
    private boolean retlToggle = true;
    private boolean staiToggle = true;
    private HashMap<Integer, Integer> zplace = new HashMap<Integer, Integer>();
    private final double floorDist = 100;
    private ArrayList<Integer> allFloorsInvolved = new ArrayList<>();
    private final int totalFloors = 7;

    @Override
    public void start(Stage primaryStage) throws Exception {
        for(int i = 0; i < nodes.size(); i++) {
            if(allFloorsInvolved.contains(nodes.get(i).getFloor()) == false) {
                allFloorsInvolved.add(nodes.get(i).getFloor());
            }
        }

        zplace.put(1, 100);
        zplace.put(2, 0);
        zplace.put(3, -100);
        zplace.put(4, -200);
        zplace.put(5, -300);
        zplace.put(6, -400);
        zplace.put(7, -500);

        NodeData begin = nodes.get(0);
        NodeData end = nodes.get(nodes.size() - 1);

        boolean showComfort = false;
        if(goal != null) {
        if(goal.equals("6 South Patient Beds")) {
            showComfort = true;
        }}

        RotateGroup group = new RotateGroup();

        for(int i = 1; i <= totalFloors; i++) {
            boolean trans = !(begin.getFloor() == i || end.getFloor() == i);
            Image floor = new Image("images/ThreeDim/floor" + i + ".png");
            Image transFloor = new Image("images/ThreeDim/transFloor" + i + ".png");
            int z = zplace.get(i);
            group.getChildren().add(createFloor(floor, transFloor, z, trans));
        }

        Camera camera = new PerspectiveCamera();
        camera.setFarClip(10000);
        camera.setTranslateY(-50);

        ImageView destinationCircle = new ImageView(new Image("/images/ThreeDim/pulse.gif"));
        double destScale = 0.1;
        destinationCircle.setScaleX(destScale);
        destinationCircle.setScaleY(destScale);
        destinationCircle.setScaleZ(destScale);
        destinationCircle.setTranslateX(end.getxCoordinate() / 5 - 247 - 250);
        destinationCircle.setTranslateZ(zplace.get(end.getFloor()) - 3);
        destinationCircle.setTranslateY(end.getyCoordinate() / 5 - 148 - 250);

        String pinPath = getClass().getResource("/images/ThreeDim/pin.STL").toURI().toString().substring(5);
        MeshView[] pin = loadMeshViews(pinPath);
        for (int i = 0; i < pin.length; i++) {
            double MODEL_SCALE_FACTOR22 = 0.15;
            pin[i].setScaleX(MODEL_SCALE_FACTOR22);
            pin[i].setScaleY(MODEL_SCALE_FACTOR22);
            pin[i].setScaleZ(MODEL_SCALE_FACTOR22);

            pin[i].setTranslateX(end.getxCoordinate() / 5 - 307);
            pin[i].setTranslateY(end.getyCoordinate() / 5 - 70);
            pin[i].setTranslateZ(zplace.get(end.getFloor()) + 35);

            PhongMaterial material = new PhongMaterial();
            Image texture = new Image(("images/ThreeDim/pinColor.jpg"));
            material.setDiffuseMap(texture);
            pin[i].setMaterial(material);
            pin[i].getTransforms().setAll(new Rotate(0, Rotate.X_AXIS), new Rotate(90, Rotate.X_AXIS));
        }

        String personPath = getClass().getResource("/images/ThreeDim/person.stl").toURI().toString().substring(5);
        MeshView[] person = loadMeshViews(personPath);
        for (int i = 0; i < person.length; i++) {
            double MODEL_SCALE_FACTOR = 3;
            person[i].setScaleX(MODEL_SCALE_FACTOR);
            person[i].setScaleY(MODEL_SCALE_FACTOR);
            person[i].setScaleZ(MODEL_SCALE_FACTOR);
            person[i].setTranslateZ(zplace.get(begin.getFloor()) + 10);

            PhongMaterial material = new PhongMaterial();
            Image texture = new Image(("images/ThreeDim/lavender.png"));
            material.setDiffuseMap(texture);
            person[i].setMaterial(material);
        }

        URL docPath = getClass().getResource("/images/ThreeDim/childrens_toy.obj").toURI().toURL();
        MeshView[] doctor = loadModel(docPath);
        for (int k = 0; k < doctor.length; k++) {
            double MODEL_SCALE_FACTOR = 0.15;
            doctor[k].setScaleX(MODEL_SCALE_FACTOR);
            doctor[k].setScaleY(MODEL_SCALE_FACTOR);
            doctor[k].setScaleZ(MODEL_SCALE_FACTOR);

            doctor[k].setTranslateZ(zplace.get(begin.getFloor()));

            doctor[k].getTransforms().setAll(new Rotate(-90, Rotate.Z_AXIS), new Rotate(90, Rotate.X_AXIS));
        }
        RotateGroup docGroup = new RotateGroup();
        docGroup.getChildren().addAll(doctor);
        group.getChildren().addAll(docGroup);

        URL nursePath = getClass().getResource("/images/ThreeDim/nurse.obj").toURI().toURL();
        MeshView[] nurse = loadModel(nursePath);
        for (int k = 0; k < nurse.length; k++) {
            double MODEL_SCALE_FACTOR = 1;
            nurse[k].setScaleX(MODEL_SCALE_FACTOR);
            nurse[k].setScaleY(MODEL_SCALE_FACTOR);
            nurse[k].setScaleZ(MODEL_SCALE_FACTOR);

            nurse[k].setTranslateZ(zplace.get(6));
            nurse[k].setTranslateX(140);
            nurse[k].setTranslateY(80);

            nurse[k].getTransforms().setAll(new Rotate(-90, Rotate.Z_AXIS), new Rotate(180, Rotate.X_AXIS));
        }
        RotateGroup nurseGroup = new RotateGroup();
        nurseGroup.getChildren().addAll(nurse);
        if(showComfort)
        group.getChildren().addAll(nurseGroup);

        URL pikaPath = getClass().getResource("/images/ThreeDim/fry5poc2kyap.obj").toURI().toURL(); //jar will brake
        MeshView[] pika = loadModel(pikaPath);
        for (int k = 0; k < nurse.length; k++) {
            double MODEL_SCALE_FACTOR = 1;
            pika[k].setScaleX(MODEL_SCALE_FACTOR);
            pika[k].setScaleY(MODEL_SCALE_FACTOR);
            pika[k].setScaleZ(MODEL_SCALE_FACTOR);
        }
        RotateGroup pikaGroup = new RotateGroup();
        pikaGroup.getChildren().addAll(pika);
        pikaGroup.setTranslateZ(zplace.get(6));
        pikaGroup.setTranslateX(180);
        pikaGroup.setTranslateY(90);
        pikaGroup.getTransforms().setAll(new Rotate(90, Rotate.Z_AXIS), new Rotate(90, Rotate.X_AXIS));
        if(showComfort)
        group.getChildren().addAll(pikaGroup);

        URL dogPath = getClass().getResource("/images/ThreeDim/12228_Dog_v1_L2.obj").toURI().toURL();
        MeshView[] dog = loadModel(dogPath);
        for (int k = 0; k < dog.length; k++) {
            double MODEL_SCALE_FACTOR = 0.5;
            dog[k].setScaleX(MODEL_SCALE_FACTOR);
            dog[k].setScaleY(MODEL_SCALE_FACTOR);
            dog[k].setScaleZ(MODEL_SCALE_FACTOR);

            dog[k].setTranslateZ(zplace.get(6));
            dog[k].setTranslateX(140);
            dog[k].setTranslateY(110);

            dog[k].getTransforms().setAll(new Rotate(-90, Rotate.Z_AXIS), new Rotate(0, Rotate.X_AXIS));
        }
        RotateGroup dogGroup = new RotateGroup();
        nurseGroup.getChildren().addAll(dog);
        if(showComfort)
        group.getChildren().addAll(dogGroup);

        URL patientPath = getClass().getResource("/images/ThreeDim/childrens_toy.obj").toURI().toURL();
        MeshView[] patient = loadModel(patientPath);
        for (int k = 0; k < dog.length; k++) {
            double MODEL_SCALE_FACTOR = 0.1;
            patient[k].setScaleX(MODEL_SCALE_FACTOR);
            patient[k].setScaleY(MODEL_SCALE_FACTOR);
            patient[k].setScaleZ(MODEL_SCALE_FACTOR);

            patient[k].setTranslateZ(zplace.get(6) - 20);
            patient[k].setTranslateX(150);
            patient[k].setTranslateY(242);
        }
        RotateGroup patientGroup = new RotateGroup();
        nurseGroup.getChildren().addAll(patient);
        if(showComfort)
        group.getChildren().addAll(patientGroup);


        URL bedPath = getClass().getResource("/images/ThreeDim/hospital_bed.obj").toURI().toURL(); //jar will brake
        MeshView[] bed = loadModel(bedPath);
        for (int k = 0; k < bed.length; k++) {
            double MODEL_SCALE_FACTOR = 1;
            bed[k].setScaleX(MODEL_SCALE_FACTOR);
            bed[k].setScaleY(MODEL_SCALE_FACTOR);
            bed[k].setScaleZ(MODEL_SCALE_FACTOR);

            bed[k].setTranslateX(160);
            bed[k].setTranslateY(90);
            bed[k].setTranslateZ(zplace.get(6));

            bed[k].getTransforms().setAll(new Rotate(-90, Rotate.Z_AXIS), new Rotate(90, Rotate.X_AXIS));
        }
        RotateGroup bedGroup = new RotateGroup();
        bedGroup.getChildren().addAll(bed);
        if(showComfort)
        group.getChildren().addAll(bedGroup);

        RotateGroup numberGroup = new RotateGroup();
        for(int i = 1; i <= totalFloors; i++) {
            boolean selected = (begin.getFloor() == i || end.getFloor() == i);
            Image number = new Image("images/ThreeDim/" + i + ".png");
            Image brightNumber = new Image("images/ThreeDim/" + i + "H.png");
            int z = zplace.get(i);
            numberGroup.getChildren().add(floorNumbers(number, brightNumber, z, selected));
        }

        List<Group> elevatorGroup = new ArrayList<>();
        List<Integer> invalidELEV = new ArrayList<>();
        int elevCount = 0;

        for(int i = 0; i < nodes.size() - 1; i++) {
            NodeData startNode = nodes.get(i);
            NodeData endNode = nodes.get(i + 1);

            for(int j = 1; j <= totalFloors; j++) {
                if(startNode.getFloor() == j) {
                    int radius = 2;
                    if(startNode.getNodeType().equals("ELEV")) {
                        URL elevPath = getClass().getResource("/images/ThreeDim/boneless.obj").toURI().toURL(); //jar will brake
                        MeshView[] elevator = loadModel(elevPath);
                        for (int k = 0; k < elevator.length; k++) {
                            double MODEL_SCALE_FACTOR = 30;
                            elevator[k].setScaleX(MODEL_SCALE_FACTOR);
                            elevator[k].setScaleY(MODEL_SCALE_FACTOR);
                            elevator[k].setScaleZ(MODEL_SCALE_FACTOR);

                            elevator[k].setTranslateX(startNode.getxCoordinate() / 5 - 244 - 45);
                            elevator[k].setTranslateY(startNode.getyCoordinate() / 5 - 148 - 9);
                            elevator[k].setTranslateZ(zplace.get(startNode.getFloor()) - 60);

                            elevator[k].getTransforms().setAll(new Rotate(-90, Rotate.Z_AXIS), new Rotate(90, Rotate.X_AXIS));
                            invalidELEV.add(i+1);
                        }
                        if(!invalidELEV.contains(i)) {
                            elevatorGroup.add(new Group(elevator));
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
        group.getChildren().add(pinGroup);
        group.getChildren().addAll(elevatorGroup);
        group.getChildren().add(destinationCircle);
        group.getChildren().add(new AmbientLight(Color.WHITE));

        SequentialTransition st = new SequentialTransition();
        ArrayList<NodeData> floorPath = new ArrayList<>();
        for(int i = 0; i < nodes.size() - 1; i++) {
            NodeData n1 = nodes.get(i);
            NodeData n2 = nodes.get(i+1);
            boolean elev = !(n1.getFloor() == n2.getFloor());
            if(elev) {
                st.getChildren().add(getFloorPath(docGroup, floorPath));
                floorPath.clear();
                double offsetX = 0;
                double offsetY = 0;
                if(n1.getNodeID().substring(7,8).equals("X")) {
                    offsetX = 0;
                    offsetY = 150;
                }
                else if(n1.getNodeID().substring(7,8).equals("Z")) {
                    offsetX = -35;
                    offsetY = 5;
                }
                TranslateTransition tt = new TranslateTransition(Duration.seconds(Math.abs(n1.getFloor() - n2.getFloor())), docGroup);
                tt.setFromZ((zplace.get(n1.getFloor())) - ((2-begin.getFloor()) * floorDist));
                tt.setFromX(n1.getxCoordinate() / 5 - 247 - 20);
                tt.setFromY(n1.getyCoordinate() / 5 - 24);
                tt.setToZ(zplace.get(n2.getFloor()) - ((2-begin.getFloor()) * floorDist));
                tt.setToX(n1.getxCoordinate() / 5 - 247 - 20);
                tt.setToY(n1.getyCoordinate() / 5 - 24);
                tt.setCycleCount(1);

                TranslateTransition ttELEV = new TranslateTransition(Duration.seconds(Math.abs(n1.getFloor() - n2.getFloor())), elevatorGroup.get(elevCount));
                ttELEV.setFromZ(zplace.get(n1.getFloor()) - ((2-n1.getFloor()) * floorDist));
                ttELEV.setFromX(n1.getxCoordinate() / 5 - 244 + offsetX);
                ttELEV.setFromY(n1.getyCoordinate() / 5 - 240 + offsetY);
                ttELEV.setToZ(zplace.get(n2.getFloor()) - ((2-n1.getFloor()) * floorDist));
                ttELEV.setToX(n1.getxCoordinate() / 5 - 244 + offsetX);
                ttELEV.setToY(n1.getyCoordinate() / 5 - 240 + offsetY);
                ttELEV.setCycleCount(1);

                ParallelTransition pllt = new ParallelTransition(tt, ttELEV);
                st.getChildren().addAll(pllt);
                elevCount++;
            }
            else {
                floorPath.add(n1); floorPath.add(n2);
            }

            if(i == nodes.size() - 2) {
                st.getChildren().add(getFloorPath(docGroup, floorPath));
            }
        }
        st.setCycleCount(Timeline.INDEFINITE);
        st.play();

        Group elevIcons = getElevIcons();
        group.getChildren().add(elevIcons);

        Group foodIcons = getFoodIcons();
        group.getChildren().add(foodIcons);

        Group retlIcons = getRETLIcons();
        group.getChildren().add(retlIcons);

        Group stairIcons = getSTAIcons();
        group.getChildren().add(stairIcons);

        Group restIcons = getRESTIcons();
        group.getChildren().add(restIcons);

        group.rotateByX(-63);
        group.translateXProperty().set((WIDTH - 192 - 49)/2 - 24);
        group.translateYProperty().set(632/2);
        group.translateZProperty().set(zplace.get(begin.getFloor()) - 700);
        group.translateZProperty().set(group.getTranslateZ() + 100);

        Group root = new Group();
        ImageView imageView = getOverlay();
        root.getChildren().add(imageView);

        Label dest = new Label();
        if(goal != null) {
        dest.setText(goal);}
        dest.setScaleX(3);
        dest.setScaleY(3);
        dest.setScaleZ(3);
        dest.relocate(568, 18);
        dest.setTextFill(Color.web("#ffffff"));

        root.getChildren().add(dest);

        Button elevatorButton = new Button();
        elevatorButton.relocate(97,57);
        elevatorButton.setPrefSize(180,50);
        elevatorButton.setStyle("-fx-background-color: TRANSPARENT");
        elevatorButton.setOnAction(e -> onElevClicked(elevIcons));
        root.getChildren().add(elevatorButton);

        Button foodButton = new Button();
        foodButton.relocate(340,56);
        foodButton.setPrefSize(140,50);
        foodButton.setStyle("-fx-background-color: TRANSPARENT");
        root.getChildren().add(foodButton);
        foodButton.setOnAction(e -> onFoodClicked(foodIcons));

        Button bathroomButton = new Button();
        bathroomButton.relocate(538,56);
        bathroomButton.setPrefSize(206,50);
        bathroomButton.setStyle("-fx-background-color: TRANSPARENT");
        root.getChildren().add(bathroomButton);
        bathroomButton.setOnAction(e -> onRestClicked(restIcons));


        Button retailButton = new Button();
        retailButton.relocate(770,56);
        retailButton.setPrefSize(150,50);
        retailButton.setStyle("-fx-background-color: TRANSPARENT");
        root.getChildren().add(retailButton);
        retailButton.setOnAction(e -> onRetailClicked(retlIcons));


        Button stairsButton = new Button();
        stairsButton.relocate(982,56);
        stairsButton.setPrefSize(160,50);
        stairsButton.setStyle("-fx-background-color: TRANSPARENT");
        root.getChildren().add(stairsButton);
        stairsButton.setOnAction(e -> onStairsClicked(stairIcons));

        camera.setTranslateZ(zplace.get(begin.getFloor()) - 20);
        dest.setTranslateZ(dest.getTranslateZ() + zplace.get(begin.getFloor()));

        AnchorPane globalRoot = new AnchorPane();
        imageView.setScaleX(imageView.getScaleX() - 0.01);
        imageView.setScaleY(imageView.getScaleY() - 0.01);
        imageView.setScaleZ(imageView.getScaleZ() - 0.01);
        imageView.setTranslateX(imageView.getTranslateX() + 1);
        imageView.setTranslateY(imageView.getTranslateY() + 48);
        globalRoot.getChildren().add(root);
        Scene scene = new Scene(globalRoot, WIDTH - 192, HEIGHT, true);
        globalRoot.setStyle("-fx-background-color: #8f8f8f");

        SubScene sub = new SubScene
                (group, WIDTH - 192 - 49, 632, true, SceneAntialiasing.BALANCED);
        sub.setCamera(camera);
        sub.setFill(Color.web("#8f8f8f"));
        mouseControl(group, sub, primaryStage, numberGroup);
        globalRoot.getChildren().add(sub);
        sub.setTranslateX(sub.getTranslateX() + 25);
        sub.setTranslateY(sub.getTranslateY() + 106);

        primaryStage.setTitle("MAP");

        root.getChildren().get(0).toBack();

        group.setTranslateZ(group.getTranslateZ() + 0.5*(-zplace.get(begin.getFloor())));

        if(allFloorsInvolved.size() > 1) {
            int max = Collections.max(allFloorsInvolved);
            int min = Collections.min(allFloorsInvolved);
            group.setTranslateY((group.getTranslateY() - (3-max) * floorDist) - (group.getTranslateY() - (3-min) * floorDist));
            group.setTranslateZ(group.getTranslateZ() + 125*(max-min));
        }
        else {
            group.setTranslateY(group.getTranslateY() - (3-begin.getFloor()) * floorDist);
        }

        elevatorButton.fire();
        foodButton.fire();
        retailButton.fire();
        bathroomButton.fire();
        stairsButton.fire();

        primaryStage.setScene(scene);
        primaryStage.resizableProperty().set(false);
        primaryStage.sizeToScene();
        primaryStage.show();


        AnimationTimer timer = new AnimationTimer() {
            private long lastUpdate = 0 ;
            @Override
            public void handle(long now) {
                if (now - lastUpdate >= 300_000_000) {
                    onFloorMove(group, docGroup, numberGroup, begin);
                    lastUpdate = now ;
                }
            }
        };
        timer.start();
    }

    private MeshView[] loadModel(URL url) {
        ObjModelImporter importer = new ObjModelImporter();
        importer.read(url);

        return importer.getImport();
    }

    static MeshView[] loadMeshViews(String filename) {
        try {
            String steeel = filename.substring(filename.lastIndexOf("/"));
            filename = filename.substring(0, filename.indexOf("/libs"));
            filename += "/resources/main/images/ThreeDim" + steeel;
            filename = filename.replace("!", "");
            filename = filename.substring(5);
            File file = new File(filename);

            StlMeshImporter importer = new StlMeshImporter();
            importer.read(file);
            Mesh mesh = importer.getImport();

            return new MeshView[]{new MeshView(mesh)};
        }
        catch (Exception e) {
            File file = new File(filename);

            StlMeshImporter importer = new StlMeshImporter();
            importer.read(file);
            Mesh mesh = importer.getImport();

            return new MeshView[]{new MeshView(mesh)};
        }
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
        material.setDiffuseMap(number);
        floorAddress.add(material.getDiffuseMap().toString());

        Box floorNum = new Box(40, 0, 70);
        floorNum.setTranslateZ(z);
        floorNum.setTranslateX(250);
        floorNum.setTranslateY(100);
        floorNum.setMaterial(material);

        return floorNum;
    }

    private void mouseControl(RotateGroup group, SubScene scene, Stage stage, RotateGroup numberGroup) {
        stage.addEventFilter(MouseEvent.MOUSE_PRESSED, (final MouseEvent mouseEvent) -> {
            oldX = mouseEvent.getX();
            oldY = mouseEvent.getY();
        });

        stage.addEventFilter(MouseEvent.MOUSE_DRAGGED, (final MouseEvent event) -> {
            if(event.isPrimaryButtonDown() && !event.isControlDown()) {
                if(event.getSceneY() > oldY) { group.setTranslateY(group.getTranslateY() + 4); }
                else {group.setTranslateY(group.getTranslateY() - 4);}
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
            if(group.getTranslateZ() < 324 && delta > 0) {
                group.translateZProperty().set(group.getTranslateZ() + delta);
            }
            else if (group.getTranslateZ() > -938 && delta < 0) {
                group.translateZProperty().set(group.getTranslateZ() + delta);
            }
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

        DatabaseController dbc = new DatabaseController();
        RotateGroup elevICON = new RotateGroup();
        Set<NodeData> nd = dbc.getAllNodesOfType("ELEV");
        for(NodeData data : nd) {
            if(!nodes.contains(data) && allFloorsInvolved.contains(data.getFloor())) {
                Image image = new Image("/images/ThreeDim/elevICON.png");
                ImageView imageView = new ImageView(image);
                imageView.setPreserveRatio(true);
                imageView.setTranslateX(data.getxCoordinate() / 5 - 500);
                imageView.setTranslateY(data.getyCoordinate() / 5 - 390);
                imageView.setTranslateZ(zplace.get(data.getFloor()) - 15);
                double scale = 0.02;
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

        ArrayList<String> validFood = new ArrayList<>();
        validFood.add("Atrium Cafe");
        validFood.add("Starbucks");
        validFood.add("Food Services");
        validFood.add("Outdoor Dining Terrace");
        RotateGroup foodICON = new RotateGroup();
        DatabaseController dbc = new DatabaseController();
        Set<NodeData> nd = dbc.getAllNodesOfType("RETL");
        for(NodeData data : nd) {
            if(nodes.get(nodes.size()-1).getNodeID() != data.getNodeID() && allFloorsInvolved.contains(data.getFloor())) {
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


        ArrayList<String> validRetail = new ArrayList<>();
        validRetail.add("Phatmacy");
        validRetail.add("Giftshop Hall");

        RotateGroup retailICON = new RotateGroup();
        DatabaseController dbc = new DatabaseController();
        Set<NodeData> nd = dbc.getAllNodesOfType("RETL");
        for(NodeData data : nd) {
            if(nodes.get(nodes.size()-1).getNodeID() != data.getNodeID() && allFloorsInvolved.contains(data.getFloor())) {
                if (!validRetail.contains(data.getLongName())) {
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

        RotateGroup staiICON = new RotateGroup();
        DatabaseController dbc = new DatabaseController();
        Set<NodeData> nd = dbc.getAllNodesOfType("STAI");
        for(NodeData data : nd) {
            if (nodes.get(nodes.size() - 1).getNodeID() != data.getNodeID() && allFloorsInvolved.contains(data.getFloor())) {
                Image image = new Image("/images/ThreeDim/stairsICON.png");
                ImageView imageView = new ImageView(image);
                imageView.setPreserveRatio(true);
                imageView.setTranslateX(data.getxCoordinate() / 5 - 1190);
                imageView.setTranslateY(data.getyCoordinate() / 5 - 985);
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

        RotateGroup restICON = new RotateGroup();
        DatabaseController dbc = new DatabaseController();
        Set<NodeData> nd = dbc.getAllNodesOfType("REST");
        for(NodeData data : nd) {
            if (nodes.get(nodes.size() - 1).getNodeID() != data.getNodeID() && allFloorsInvolved.contains(data.getFloor())) {
                Image image = new Image("/images/ThreeDim/restICON.png");
                ImageView imageView = new ImageView(image);
                imageView.setPreserveRatio(true);
                imageView.setTranslateX(data.getxCoordinate() / 5 - 905);
                imageView.setTranslateY(data.getyCoordinate() / 5 - 645);
                imageView.setTranslateZ(zplace.get(data.getFloor()) - 20);
                double scale = 0.015;
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

    private void onElevClicked(Group elevIcons) {
        if(elevToggle) {
            elevIcons.getChildren().forEach(node -> {node.setVisible(false);});
            elevToggle = false;
        }
        else {
            elevIcons.getChildren().forEach(node -> {node.setVisible(true);});
            elevToggle = true;
        }
    }

    private void onFoodClicked(Group foodIcons) {
        if(foodToggle) { ;
            foodIcons.getChildren().forEach(node -> {node.setVisible(false);});
            foodToggle = false;
        }
        else {
            foodIcons.getChildren().forEach(node -> {node.setVisible(true);});
            foodToggle = true;
        }
    }

    private void onRestClicked(Group restIcons) {
        if(restToggle) {
            restIcons.getChildren().forEach(node -> {node.setVisible(false);});
            restToggle = false;
        }
        else {
            restIcons.getChildren().forEach(node -> {node.setVisible(true);});
            restToggle = true;
        }
    }

    private void onRetailClicked(Group retlIcons) {
        if(retlToggle) {
            retlIcons.getChildren().forEach(node -> {node.setVisible(false);});
            retlToggle = false;
        }
        else {
            retlIcons.getChildren().forEach(node -> {node.setVisible(true);});
            retlToggle = true;
        }
    }

    private void onStairsClicked(Group stairIcons) {
        if(staiToggle) {
            stairIcons.getChildren().forEach(node -> {node.setVisible(false);});
            staiToggle = false;
        }
        else {
            stairIcons.getChildren().forEach(node -> {node.setVisible(true);});
            staiToggle = true;
        }
    }

    private void onFloorMove(RotateGroup group, RotateGroup personGroup, RotateGroup numberGroup, NodeData begin) {
        for(int i = 1; i <= 7; i++) {
            if(Math.abs((personGroup.getTranslateZ() + floorDist * (2-begin.getFloor())) - (zplace.get(i))) <= 49) {
                int finalI = i;
                numberGroup.getChildren().stream().filter(node -> (node instanceof Box))
                        .forEach(
                                node -> {
                                    if(floorAddress.get(finalI - 1).equals(((PhongMaterial)(((Box) node).getMaterial())).getDiffuseMap().toString())) {
                                        PhongMaterial material = new PhongMaterial();
                                        material.setDiffuseMap(new Image("images/ThreeDim/" + finalI + "H.png"));
                                        floorAddress.set(finalI - 1,material.getDiffuseMap().toString());
                                        ((Box) node).setMaterial(material);
                                    }
                                });
            }

            if(Math.abs((personGroup.getTranslateZ() + floorDist * (2-begin.getFloor())) - (zplace.get(i))) > 49) {
                int finalI1 = i;
                numberGroup.getChildren().stream().filter(node -> (node instanceof Box))
                        .forEach(
                                node -> {
                                    if(floorAddress.get(finalI1 -1).equals(((PhongMaterial)(((Box) node).getMaterial())).getDiffuseMap().toString())) {
                                        PhongMaterial material = new PhongMaterial();
                                        material.setDiffuseMap(new Image("images/ThreeDim/" + finalI1 + ".png"));
                                        floorAddress.set(finalI1 -1,material.getDiffuseMap().toString());
                                        ((Box) node).setMaterial(material);
                                    }
                                });
            }
        }
    }
}