package edu.wpi.cs3733.c20.teamS.pathDisplaying;

import com.interactivemesh.jfx.importer.stl.StlMeshImporter;
import edu.wpi.cs3733.c20.teamS.database.NodeData;
import javafx.animation.*;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ThreeDimensions extends Application {
    private List<NodeData> nodes;
    Stage stage = new Stage();

    public ThreeDimensions(List<NodeData> nodes) throws Exception {
        this.nodes = nodes;
        start(stage);
    }

    public static final float WIDTH = 1400;
    public static final float HEIGHT = 800;

    private double anchorX, anchorY;
    private double anchorAngleX = 0;
    private double anchorAngleY = 0;
    private final DoubleProperty angleX = new SimpleDoubleProperty(0);
    private final DoubleProperty angleY = new SimpleDoubleProperty(0);

    @Override
    public void start(Stage primaryStage) {

        SmartGroup group = new SmartGroup();
        if(nodes.get(0).getFloor() == 2 || nodes.get(nodes.size()-1).getFloor() == 2) {
            group.getChildren().add(prepareBox()); //second
        }
        if(nodes.get(0).getFloor() == 3 || nodes.get(nodes.size()-1).getFloor() == 3) {
            group.getChildren().add(prepareSecondBox()); //third
        }
        if(nodes.get(0).getFloor() == 1 || nodes.get(nodes.size()-1).getFloor() == 1) {
            group.getChildren().add(prepareThirdBox()); //first
        }
        if(nodes.get(0).getFloor() == 4 || nodes.get(nodes.size()-1).getFloor() == 4) {
            group.getChildren().add(prepareFourthBox()); //fourth
        }
        if(nodes.get(0).getFloor() == 5 || nodes.get(nodes.size()-1).getFloor() == 5) {
            group.getChildren().add(prepareFifthBox()); //fifth
        }
        group.getChildren().add(new AmbientLight(Color.WHITE));

        Camera camera = new PerspectiveCamera();
        camera.setTranslateY(-50);

        Shape s = new Polygon(0, 0, -10, -10, 10, 0, -10, 10);
        s.setStrokeWidth(3);
        s.setStrokeLineCap(StrokeLineCap.ROUND);
        s.setStroke(Color.AQUA);
        s.setTranslateZ(-70);

        Group root2 = new Group();
        root2.getChildren().add(group);
        root2.getChildren().add(prepareImageView());

        SequentialTransition str = new SequentialTransition();
        Ellipse ellipseEarth = new Ellipse();
        ellipseEarth.setRadiusX(100);
        ellipseEarth.setCenterY(100);
        group.getChildren().add(ellipseEarth);

        group.getChildren().add(s);


        for (int i = 0; i < nodes.size() - 1; i++) {
            HashMap<Integer, Integer> zplace = new HashMap<Integer, Integer>();
            zplace.put(1, 100);
            zplace.put(2, 0);
            zplace.put(3, -100);
            zplace.put(4, -200);
            zplace.put(5, -300);
            NodeData startNode = nodes.get(i);
            NodeData endNode = nodes.get(i + 1);
            if (startNode.getFloor() == 2) {
                Point3D point3 = new Point3D(startNode.getxCoordinate() / 5 - 247, startNode.getyCoordinate() / 5 - 148, 0);
                Point3D point4 = new Point3D(endNode.getxCoordinate() / 5 - 247, endNode.getyCoordinate() / 5 - 148, 0);
                group.getChildren().add(createConnection(point3, point4, 2));
                magical(startNode, endNode, str, s, group, point3, point4);
            }
            if (startNode.getFloor() == 3) {
                Point3D point3 = new Point3D(startNode.getxCoordinate() / 5 - 247, startNode.getyCoordinate() / 5 - 148, -100);
                Point3D point4 = new Point3D(endNode.getxCoordinate() / 5 - 247, endNode.getyCoordinate() / 5 - 148, -100);
                group.getChildren().add(createConnection(point3, point4, 2));
                magical(startNode, endNode, str, s, group, point3, point4);
            }
            if (startNode.getFloor() == 1) {
                Point3D point3 = new Point3D(startNode.getxCoordinate() / 5 - 247, startNode.getyCoordinate() / 5 - 148, +100);
                Point3D point4 = new Point3D(endNode.getxCoordinate() / 5 - 247, endNode.getyCoordinate() / 5 - 148, +100);
                group.getChildren().add(createConnection(point3, point4, 2));
                magical(startNode, endNode, str, s, group, point3, point4);
            }
            if (startNode.getFloor() == 4) {
                Point3D point3 = new Point3D(startNode.getxCoordinate() / 5 - 247, startNode.getyCoordinate() / 5 - 148, -200);
                Point3D point4 = new Point3D(endNode.getxCoordinate() / 5 - 247, endNode.getyCoordinate() / 5 - 148, -200);
                group.getChildren().add(createConnection(point3, point4, 2));
                magical(startNode, endNode, str, s, group, point3, point4);
            }
            if (startNode.getFloor() == 5) {
                Point3D point3 = new Point3D(startNode.getxCoordinate() / 5 - 247, startNode.getyCoordinate() / 5 - 148, -300);
                Point3D point4 = new Point3D(endNode.getxCoordinate() / 5 - 247, endNode.getyCoordinate() / 5 - 148, -300);
                group.getChildren().add(createConnection(point3, point4, 2));
                magical(startNode, endNode, str, s, group, point3, point4);
            }

            if (startNode.getNodeType().equals("ELEV")) {
                int floor1 = startNode.getFloor();
                int floor2 = endNode.getFloor();
                //String xyz = spec1.getNodeID().substring(7,8);
                Point3D elev1 = new Point3D(startNode.getxCoordinate() / 5 - 247, startNode.getyCoordinate() / 5 - 148, zplace.get(floor1));
                Point3D elev2 = new Point3D(endNode.getxCoordinate() / 5 - 247, endNode.getyCoordinate() / 5 - 148, zplace.get(floor2));
                group.getChildren().add(createConnection(elev1, elev2, 5));
            }
        }

        str.setCycleCount(Timeline.INDEFINITE);
        str.play();
        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            switch (event.getCode()) {
                case W:
                    group.translateZProperty().set(group.getTranslateZ() + 100);
                    break;
                case S:
                    group.translateZProperty().set(group.getTranslateZ() - 100);
                    break;
                case Q:
                    group.rotateByX(10);
                    break;
                case E:
                    group.rotateByX(-10);
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

        for(int i = 0; i < 7;i++){
            KeyEvent press = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.E, false, false, false, false);
            primaryStage.fireEvent(press);}
        for(int i = 0; i < 12;i++){
            KeyEvent press = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.T, false, false, false, false);
            primaryStage.fireEvent(press);}
        group.setTranslateY(group.getTranslateY() + 70);

        group.translateXProperty().set(WIDTH / 2);
        group.translateYProperty().set(HEIGHT / 2);
        group.translateZProperty().set(-600);
        Scene scene = new Scene(root2, WIDTH, HEIGHT, true);
        scene.setFill(Color.DIMGRAY);
        scene.setCamera(camera);
        initMouseControl(group, scene, primaryStage);

        primaryStage.setTitle("Three Dimensions");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    static MeshView[] loadMeshViews(String filename) {
        File file = new File(filename);
        StlMeshImporter importer = new StlMeshImporter();
        importer.read(file);
        Mesh mesh = importer.getImport();

        return new MeshView[] { new MeshView(mesh) };
    }

    private ImageView prepareImageView() {
        Image image = new Image("images/space.jpg");
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.getTransforms().add(new Translate(0,-200,0));
        return imageView;
    }

    public double distance3D(Point3D start, Point3D end) {
        double result = Math.sqrt(Math.pow((end.getX() - start.getX()), 2) + Math.pow((end.getX() - start.getX()), 2) + Math.pow((end.getX() - start.getX()), 2));
        return result;
    }

    public Cylinder createConnection(Point3D origin, Point3D target, int radius) {
        Point3D yAxis = new Point3D(0, 1, 0);
        Point3D diff = target.subtract(origin);
        double height = diff.magnitude();

        Point3D mid = target.midpoint(origin);
        Translate moveToMidpoint = new Translate(mid.getX(), mid.getY(), mid.getZ());

        Point3D axisOfRotation = diff.crossProduct(yAxis);
        double angle = Math.acos(diff.normalize().dotProduct(yAxis));
        Rotate rotateAroundCenter = new Rotate(-Math.toDegrees(angle), axisOfRotation);

        Cylinder line = new Cylinder(radius, height);

        if (radius >= 5) {
            PhongMaterial material = new PhongMaterial();
            Image image = new Image(("images/gif.gif"));
            material.setDiffuseMap(image);

            line.setMaterial(material);
        }

        if (radius < 5) {
            PhongMaterial material = new PhongMaterial();
            Image image = new Image(("images/geometric.jpg"));
            material.setDiffuseMap(image);

            line.setMaterial(material);
        }

        line.getTransforms().addAll(moveToMidpoint, rotateAroundCenter);

        return line;
    }

    double calculateAngle(double P1X, double P1Y, double P2X, double P2Y,
                          double P3X, double P3Y){

        double numerator = P2Y*(P1X-P3X) + P1Y*(P3X-P2X) + P3Y*(P2X-P1X);
        double denominator = (P2X-P1X)*(P1X-P3X) + (P2Y-P1Y)*(P1Y-P3Y);
        double ratio = numerator/denominator;

        double angleRad = Math.atan(ratio);
        double angleDeg = (angleRad*180)/Math.PI;

        if(angleDeg<0){
            angleDeg = 180+angleDeg;
        }

        return angleDeg;
    }

    private Node prepareSecondBox() {
        PhongMaterial material = new PhongMaterial();

        Image image = new Image(("images/greif3.png"));
        material.setDiffuseMap(image);
        Box box = new Box(495, 297, 0);
        box.setTranslateZ(-100);
        box.setMaterial(material);
        box.setOpacity(0.1);
        return box;
    }

    private Node prepareThirdBox() {
        PhongMaterial material = new PhongMaterial();

        Image image = new Image(("images/grief1.png"));
        material.setDiffuseMap(image);
        Box box = new Box(495, 297, 0);
        box.setTranslateZ(+100);
        box.setMaterial(material);
        box.setOpacity(0.1);
        return box;
    }

    private Node prepareFourthBox() {
        PhongMaterial material = new PhongMaterial();

        Image image = new Image(("images/grief4.png"));
        material.setDiffuseMap(image);
        Box box = new Box(495, 297, 0);
        box.setTranslateZ(-200);
        box.setMaterial(material);
        box.setOpacity(0.1);
        return box;
    }

    private Node prepareFifthBox() {
        PhongMaterial material = new PhongMaterial();

        Image image = new Image(("images/grief5.png"));
        material.setDiffuseMap(image);
        Box box = new Box(495, 297, 0);
        box.setTranslateZ(-300);
        box.setMaterial(material);
        box.setOpacity(0.1);
        return box;
    }

    private Box prepareBox() {
        PhongMaterial material = new PhongMaterial();

        Image image = new Image(("images/grief2.png"));
        material.setDiffuseMap(image);
        Box box = new Box(495, 297, 0);
        box.setMaterial(material);
        box.setOpacity(0.1);
        return box;
    }

    private void initMouseControl(SmartGroup group, Scene scene, Stage stage) {
        Rotate xRotate;
        Rotate yRotate;
        group.getTransforms().addAll(
                xRotate = new Rotate(0, Rotate.X_AXIS),
                yRotate = new Rotate(0, Rotate.Y_AXIS)
        );
        xRotate.angleProperty().bind(angleX);
        yRotate.angleProperty().bind(angleY);

        scene.setOnMousePressed(event -> {
            anchorX = event.getSceneX();
            anchorY = event.getSceneY();
            anchorAngleX = angleX.get();
            anchorAngleY = angleY.get();
        });

        scene.setOnMouseDragged(event -> {
            angleX.set(anchorAngleX - (anchorY - event.getSceneY()));
            angleY.set(anchorAngleY + anchorX - event.getSceneX());
        });

        stage.addEventHandler(ScrollEvent.SCROLL, event -> {
            double delta = event.getDeltaY();
            group.translateZProperty().set(group.getTranslateZ() + delta);
        });
    }


    public static void main(String[] args) {
        launch(args);
    }

    class SmartGroup extends Group {
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

    private void magical(NodeData startNode, NodeData endNode, SequentialTransition str, Shape s, SmartGroup group, Point3D point3, Point3D point4) {
        HashMap<Integer, Integer> zplace = new HashMap<Integer, Integer>();
        zplace.put(1, 100);
        zplace.put(2, 0);
        zplace.put(3, -100);
        zplace.put(4, -200);
        zplace.put(5, -300);
        if(endNode.getFloor() == nodes.get(0).getFloor()) {
            PathTransition transitionEarth = new PathTransition();
            Line liner = new Line(point3.getX(), point3.getY(), point4.getX(), point4.getY());
            group.getChildren().add(liner);
            transitionEarth.setPath(liner);
            transitionEarth.setNode(s);
            transitionEarth.setInterpolator(Interpolator.LINEAR);
            transitionEarth.setDuration(Duration.seconds(1));
            transitionEarth.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
            transitionEarth.setCycleCount(1);

            str.getChildren().add(transitionEarth);}
        else if(endNode.getNodeType().equals("ELEV")) {
            TranslateTransition translateTransition = new TranslateTransition(Duration.millis(1000), s); //can change mph
            translateTransition.setFromZ(zplace.get(startNode.getFloor()) - 50 - 30);
            translateTransition.setFromX(startNode.getxCoordinate() / 5 - 247);
            translateTransition.setFromY(startNode.getyCoordinate() / 5 - 148);
            translateTransition.setToZ(zplace.get(endNode.getFloor()) - 50 - 30);
            translateTransition.setToX(endNode.getxCoordinate() / 5 - 247);
            translateTransition.setToY(endNode.getyCoordinate() / 5 - 148);
            translateTransition.setCycleCount(1);
            str.getChildren().add(translateTransition);
        }
        else if(endNode.getFloor() == nodes.get(nodes.size()-1).getFloor()) {
            PathTransition transitionEarth = new PathTransition();
            Line liner = new Line(point3.getX(), point3.getY(), point4.getX(), point4.getY());
            group.getChildren().add(liner);
            transitionEarth.setPath(liner);
            transitionEarth.setNode(s);
            transitionEarth.setInterpolator(Interpolator.LINEAR);
            transitionEarth.setDuration(Duration.seconds(1));
            transitionEarth.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
            transitionEarth.setCycleCount(1);
            str.getChildren().add(transitionEarth);
        }
    }
}

