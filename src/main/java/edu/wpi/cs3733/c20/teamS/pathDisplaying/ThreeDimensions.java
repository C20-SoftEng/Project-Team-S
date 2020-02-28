package edu.wpi.cs3733.c20.teamS.pathDisplaying;

import com.interactivemesh.jfx.importer.stl.StlMeshImporter;
import edu.wpi.cs3733.c20.teamS.database.NodeData;
import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
import java.util.HashMap;
import java.util.List;

public class ThreeDimensions extends Application {
    private List<NodeData> nodes;
    private Stage primaryStage = new Stage();

    public ThreeDimensions(List<NodeData> nodes) throws Exception {
        if(nodes != null) {
        this.nodes = nodes;
        start(primaryStage);}
    }

    public static final float WIDTH = 1400;
    public static final float HEIGHT = 800;

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

        group.getChildren().add(new AmbientLight(Color.WHITE));

        Camera camera = new PerspectiveCamera();
        camera.setFarClip(1000);
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
            double MODEL_SCALE_FACTOR = 4.3;
            person[i].setScaleX(MODEL_SCALE_FACTOR);
            person[i].setScaleY(MODEL_SCALE_FACTOR);
            person[i].setScaleZ(MODEL_SCALE_FACTOR);

            PhongMaterial material = new PhongMaterial();
            Image texture = new Image(("images/ThreeDim/yellow.jpg"));
            material.setDiffuseMap(texture);
            person[i].setMaterial(material);
        }

        SequentialTransition st = new SequentialTransition();
        for (int i = 0; i < nodes.size() - 1; i++) {
            NodeData startNode = nodes.get(i);
            NodeData endNode = nodes.get(i + 1);
            boolean sameFloor = (startNode.getFloor() == endNode.getFloor());

            for (int j = 0; j < person.length; j++) {
                double length = Math.sqrt(Math.pow(Math.abs(endNode.getxCoordinate() - startNode.getxCoordinate()), 2) + Math.pow(Math.abs((endNode.getyCoordinate() - startNode.getyCoordinate())), 2));
                TranslateTransition tt = new TranslateTransition(Duration.seconds(length/200), person[j]);
                tt.setInterpolator(Interpolator.LINEAR);
                if (!sameFloor) {
                    length = Math.abs(endNode.getFloor() - startNode.getFloor());
                    tt.setDuration(Duration.seconds(length));
                }
                tt.setFromX(startNode.getxCoordinate() / 5 - 247);
                tt.setFromY(startNode.getyCoordinate() / 5 - 153);
                tt.setFromZ(zplace.get(startNode.getFloor()) - 27);
                tt.setToX(endNode.getxCoordinate() / 5 - 247);
                tt.setToY(endNode.getyCoordinate() / 5 - 153);
                tt.setToZ(zplace.get(endNode.getFloor()) - 27);
                tt.setCycleCount(1);
                st.getChildren().add(tt);
            }
        }
        st.setCycleCount(Timeline.INDEFINITE);
        st.play();

        Group personGroup = new Group(person);
        Group pinGroup = new Group(pin);
        group.getChildren().add(personGroup);
        group.getChildren().add(pinGroup);
        group.getChildren().add(destinationCircle);

        for(int i = 1; i <= 5; i++) {
            boolean selected = (begin.getFloor() == i || end.getFloor() == i);
            Image number = new Image("images/ThreeDim/number" + i + ".png");
            Image brightNumber = new Image("images/ThreeDim/bright" + i + ".png");
            int z = zplace.get(i);
            group.getChildren().add(floorNumbers(number, brightNumber, z, selected));
        }

        for(int i = 0; i < nodes.size() - 1; i++) {
            NodeData startNode = nodes.get(i);
            NodeData endNode = nodes.get(i + 1);

            for(int j = 1; j <= 5; j++) {
                if(startNode.getFloor() == j) {
                    int radius = 2;
                    if(startNode.getNodeType().equals("ELEV")) {radius = 5;}
                    Point3D startPoint = new Point3D(startNode.getxCoordinate() / 5 - 247, startNode.getyCoordinate() / 5 - 148, zplace.get(startNode.getFloor()));
                    Point3D endPoint = new Point3D(endNode.getxCoordinate() / 5 - 247, endNode.getyCoordinate() / 5 - 148, zplace.get(endNode.getFloor()));
                    group.getChildren().add(drawCylinder(startPoint, endPoint, radius));
                }
            }
        }

        group.rotateByX(-70);
        group.rotateByZ(-10);
        group.setTranslateY(group.getTranslateY() + 70);
        group.translateXProperty().set(WIDTH / 2);
        group.translateYProperty().set(HEIGHT / 2);
        group.translateZProperty().set(zplace.get(nodes.get(0).getFloor()) - 700);

        Scene scene = new Scene(group, WIDTH, HEIGHT, true);
        scene.setFill(Color.DIMGRAY);
        scene.setCamera(camera);

        primaryStage.addEventHandler(ScrollEvent.SCROLL, event -> {
            double delta = event.getDeltaY();
            group.translateZProperty().set(group.getTranslateZ() + delta);
        });

        primaryStage.setTitle("3D View");
        primaryStage.setScene(scene);
        primaryStage.show();
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
        if(radius >= 5) {texture = new Image(("images/ThreeDim/elevatorTexture.png"));}

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
}