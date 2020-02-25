package edu.wpi.cs3733.c20.teamS.pathDisplaying;

import com.interactivemesh.jfx.importer.stl.StlMeshImporter;
import edu.wpi.cs3733.c20.teamS.database.DatabaseController;
import edu.wpi.cs3733.c20.teamS.database.EdgeData;
import edu.wpi.cs3733.c20.teamS.database.NodeData;
import edu.wpi.cs3733.c20.teamS.pathDisplaying.PathRenderer;
import edu.wpi.cs3733.c20.teamS.utilities.Board;
import javafx.animation.*;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.effect.Bloom;
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
import javafx.scene.shape.Path;
import javafx.util.Duration;
import org.apache.derby.impl.sql.catalog.SYSROUTINEPERMSRowFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ThreeDimensions extends Application {
    private static final String MESH_FILENAME =
            "C:\\Users\\mcapo\\Desktop\\Reloaded\\src\\main\\resources\\images\\squidward.stl";
    private static final String STARTICON =
            "C:\\Users\\mcapo\\Desktop\\Reloaded\\src\\main\\resources\\images\\start.stl";
    private static final String FINISHICON =
            "C:\\Users\\mcapo\\Desktop\\Reloaded\\src\\main\\resources\\images\\finish.stl";

    private static final String clarinet =
            "C:\\Users\\mcapo\\Desktop\\Reloaded\\src\\main\\resources\\images\\Clarinet.stl";

    private static final String hat = "C:\\Users\\mcapo\\Desktop\\Reloaded\\src\\main\\resources\\images\\hat.stl";
    private PathTransition pathTrans;

    private List<NodeData> nodes;
    private Stage stage;
    private double cost;

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
    public void start(Stage primaryStage) throws Exception {
        ArrayList<Integer> anglez = new ArrayList<Integer>();
        for(int i = 0; i < nodes.size() - 2; i++) {
            Point3D one = new Point3D(nodes.get(i).getxCoordinate(), nodes.get(i).getyCoordinate(), 0);
            Point3D two = new Point3D(nodes.get(i + 1).getxCoordinate(), nodes.get(i + 1).getyCoordinate(), 0);
            Point3D three = new Point3D(nodes.get(i + 2).getxCoordinate(), nodes.get(i + 2).getyCoordinate(), 0);
            double angle = calculateAngle(one.getX(), one.getY(), two.getX(), two.getY(), three.getX(), three.getY());
            anglez.add((int)angle);
        }
        System.out.println(nodes.size());
        System.out.println(anglez.size());

        Box box = prepareBox();

        SmartGroup group = new SmartGroup();
        if(nodes.get(0).getFloor() == 2 || nodes.get(nodes.size()-1).getFloor() == 2) {
            group.getChildren().add(box); //second
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
        Point3D point1 = new Point3D(0, 0, 0);
        Point3D point2 = new Point3D(0, 0, 0);

        Camera camera = new PerspectiveCamera();
        camera.setTranslateY(-50);


        DatabaseController dbc = new DatabaseController();
        //dbc.importStartUpData();
        Set<NodeData> nd = dbc.getAllNodes();

        Shape s = new Polygon(0, 0, -10, -10, 10, 0, -10, 10);
        s.setStrokeWidth(3);
        s.setStrokeLineCap(StrokeLineCap.ROUND);
        s.setStroke(Color.AQUA);
        // s.setEffect(new Bloom());
        s.setTranslateZ(-70);

        Box wongBox = new Box();
        wongBox.setHeight(40);
        wongBox.setWidth(40);
        wongBox.setDepth(40);
        PhongMaterial material = new PhongMaterial();
        Image image = new Image(("images/wwong2.jpg"));
        material.setDiffuseMap(image);
        wongBox.setMaterial(material);
        wongBox.setTranslateZ(-70);

//        Sphere sphere = new Sphere(20);
//        PhongMaterial material = new PhongMaterial();
//        Image image = new Image(("images/wwong2.jpg"));
//        material.setDiffuseMap(image);
//        sphere.setMaterial(material);
//        sphere.setTranslateZ(-300);
        double total  =  0;
//        SequentialTransition sequentialTransition = new SequentialTransition();
//        for(int i = 0; i < nodes.size() - 1; i++) {
//            HashMap<Integer, Integer> zplace = new HashMap<Integer, Integer>();
//            zplace.put(1, 100);
//            zplace.put(2, 0);
//            zplace.put(3, -100);
//            zplace.put(4, -200);
//            zplace.put(5, -300);
//            NodeData startNode = nodes.get(i);
//            NodeData endNode = nodes.get(i + 1);
//            int floor1 = startNode.getFloor();
//            int floor2 = endNode.getFloor();
//            boolean sameFloor = (floor1 == floor2);
//            TranslateTransition translateTransition = new TranslateTransition(Duration.millis(1000), wongBox); //can change mph
//            translateTransition.setFromZ(zplace.get(startNode.getFloor()) - 50 - 30);
//            translateTransition.setFromX(startNode.getxCoordinate() / 5 - 247);
//            translateTransition.setFromY(startNode.getyCoordinate() / 5 - 148);
//            translateTransition.setToZ(zplace.get(endNode.getFloor()) - 50 - 30);
//            translateTransition.setToX(endNode.getxCoordinate() / 5 - 247);
//            translateTransition.setToY(endNode.getyCoordinate() / 5 - 148);
//            translateTransition.setCycleCount(1);
//            total += 1000;
//            //translateTransition.setAutoReverse(true);
//            //sequentialTransition.getChildren().add(createTransition(animated_path, wongBox));
//            sequentialTransition.getChildren().addAll(translateTransition);
//
//            if(i+1 < anglez.size()) {
//                RotateTransition rt = new RotateTransition();
//                rt.setDuration(Duration.millis(500));
//                rt.setNode(wongBox);
//                rt.setAxis(Rotate.Z_AXIS);
//                rt.setCycleCount(1);
//                rt.setToAngle(-anglez.get(i));
//                rt.getNode().getTransforms().setAll(new Rotate(-anglez.get(i+1), Rotate.Z_AXIS), new Rotate(0, Rotate.X_AXIS));
//
//                sequentialTransition.getChildren().add(rt);
//            }
//        }
//        sequentialTransition.setCycleCount(Timeline.INDEFINITE);
//        //sequentialTransition.setAutoReverse(true);
//        sequentialTransition.play();

        double MODEL_SCALE_FACTOR = 10;

        MeshView[] meshViews = loadMeshViews(MESH_FILENAME);
        for (int i = 0; i < meshViews.length; i++) {
//            meshViews[i].setTranslateX(0);
//            meshViews[i].setTranslateY(0);
//            meshViews[i].setTranslateZ(0);
            meshViews[i].setScaleX(MODEL_SCALE_FACTOR);
            meshViews[i].setScaleY(MODEL_SCALE_FACTOR);
            meshViews[i].setScaleZ(MODEL_SCALE_FACTOR);

            PhongMaterial sample = new PhongMaterial();
            Image image2 = new Image(("images/gold.jpg"));
            sample.setDiffuseMap(image2);
            //sample.setSpecularColor(Color.BEIGE);
            //sample.setSpecularPower(16);
            meshViews[i].setMaterial(sample);

            meshViews[i].getTransforms().setAll(new Rotate(90, Rotate.X_AXIS));
        }

        int MODEL_SCALE_FACTOR2 = 3;
        MeshView[] meshViews2 = loadMeshViews(STARTICON);
        for (int i = 0; i < meshViews2.length; i++) {
            HashMap<Integer, Integer> zplace = new HashMap<Integer, Integer>();
            zplace.put(1, 100);
            zplace.put(2, 0);
            zplace.put(3, -100);
            zplace.put(4, -200);
            zplace.put(5, -300);
            meshViews2[i].setTranslateX((nodes.get(0).getxCoordinate()/ 5 - 247) - 100);
            meshViews2[i].setTranslateY(nodes.get(0).getyCoordinate()/ 5 - 148);
            meshViews2[i].setTranslateZ(zplace.get(nodes.get(0).getFloor()));
            meshViews2[i].setScaleX(MODEL_SCALE_FACTOR2);
            meshViews2[i].setScaleY(MODEL_SCALE_FACTOR2);
            meshViews2[i].setScaleZ(MODEL_SCALE_FACTOR2);

            PhongMaterial sample = new PhongMaterial();
            Image image2 = new Image(("images/geometric.jpg"));
            sample.setDiffuseMap(image2);
            //sample.setSpecularColor(Color.BEIGE);
            //sample.setSpecularPower(16);
            meshViews2[i].setMaterial(sample);

            meshViews2[i].getTransforms().setAll(new Rotate(0, Rotate.Z_AXIS), new Rotate(90, Rotate.X_AXIS));
        }

        MeshView[] meshViews3 = loadMeshViews(FINISHICON);
        for (int i = 0; i < meshViews3.length; i++) {
            HashMap<Integer, Integer> zplace = new HashMap<Integer, Integer>();
            zplace.put(1, 100);
            zplace.put(2, 0);
            zplace.put(3, -100);
            zplace.put(4, -200);
            zplace.put(5, -300);
            meshViews3[i].setTranslateX(nodes.get(nodes.size()-1).getxCoordinate()/ 5 - 347);
            meshViews3[i].setTranslateY(nodes.get(nodes.size()-1).getyCoordinate()/ 5 - 148);
            meshViews3[i].setTranslateZ(zplace.get(nodes.get(nodes.size()-1).getFloor()));
            meshViews3[i].setScaleX(MODEL_SCALE_FACTOR2);
            meshViews3[i].setScaleY(MODEL_SCALE_FACTOR2);
            meshViews3[i].setScaleZ(MODEL_SCALE_FACTOR2);

            PhongMaterial sample = new PhongMaterial();
            Image image2 = new Image(("images/geometric.jpg"));
            sample.setDiffuseMap(image2);
            //sample.setSpecularColor(Color.BEIGE);
            //sample.setSpecularPower(16);
            meshViews3[i].setMaterial(sample);

            meshViews3[i].getTransforms().setAll(new Rotate(0, Rotate.X_AXIS), new Rotate(90, Rotate.X_AXIS));
        }

        SequentialTransition st = new SequentialTransition();
        for(int i = 0; i < nodes.size() - 1; i++) {
            HashMap<Integer, Integer> zplace = new HashMap<Integer, Integer>();
            zplace.put(1, 100);
            zplace.put(2, 0);
            zplace.put(3, -100);
            zplace.put(4, -200);
            zplace.put(5, -300);
            NodeData startNode = nodes.get(i);
            NodeData endNode = nodes.get(i + 1);
            int floor1 = startNode.getFloor();
            int floor2 = endNode.getFloor();
            boolean sameFloor = (floor1 == floor2);

            for(int j = 0; j < meshViews.length; j++) {
                TranslateTransition tt = new TranslateTransition(Duration.millis(1000), meshViews[j]); //can change mph
                if (!sameFloor) {
                    tt.setDuration(Duration.millis(500));
                }
                tt.setFromZ(zplace.get(startNode.getFloor()));
                tt.setFromX(startNode.getxCoordinate() / 5 - 247);
                tt.setFromY(startNode.getyCoordinate() / 5 - 148 -47);
                tt.setToZ(zplace.get(endNode.getFloor()));
                tt.setToX(endNode.getxCoordinate() / 5 - 247);
                tt.setToY(endNode.getyCoordinate() / 5 - 148 -47);
                tt.setCycleCount(1);
                st.getChildren().add(tt);
                //tt.setAutoReverse(true);

            }
        }
        st.setCycleCount(Timeline.INDEFINITE);
        // st.setAutoReverse(true);
        st.play();

        MeshView[] clari = loadMeshViews(clarinet);
        double MODEL_SCALE_FACTOR7 = 0.7;
        for (int i = 0; i < clari.length; i++) {
//            meshViews[i].setTranslateX(0);
//            meshViews[i].setTranslateY(0);
//            meshViews[i].setTranslateZ(0);
            clari[i].setScaleX(MODEL_SCALE_FACTOR7);
            clari[i].setScaleY(MODEL_SCALE_FACTOR7);
            clari[i].setScaleZ(MODEL_SCALE_FACTOR7);

            PhongMaterial sample = new PhongMaterial();
            Image image2 = new Image(("images/gold.jpg"));
            sample.setDiffuseMap(image2);
            //sample.setSpecularColor(Color.BEIGE);
            //sample.setSpecularPower(16);
            clari[i].setMaterial(sample);

            clari[i].getTransforms().setAll(new Rotate(0, Rotate.Z_AXIS), new Rotate(180, Rotate.X_AXIS));
        }
        SequentialTransition st2 = new SequentialTransition();
        for(int i = 0; i < nodes.size() - 1; i++) {
            HashMap<Integer, Integer> zplace = new HashMap<Integer, Integer>();
            zplace.put(1, 100);
            zplace.put(2, 0);
            zplace.put(3, -100);
            zplace.put(4, -200);
            zplace.put(5, -300);
            NodeData startNode = nodes.get(i);
            NodeData endNode = nodes.get(i + 1);
            int floor1 = startNode.getFloor();
            int floor2 = endNode.getFloor();
            boolean sameFloor = (floor1 == floor2);

            for(int j = 0; j < clari.length; j++) {
                TranslateTransition tt = new TranslateTransition(Duration.millis(1000), clari[j]); //can change mph
                if (!sameFloor) {
                    tt.setDuration(Duration.millis(500));
                }
                tt.setFromZ(zplace.get(startNode.getFloor()) - 80);
                tt.setFromX(startNode.getxCoordinate() / 5 - 247);
                tt.setFromY(startNode.getyCoordinate() / 5 - 148 -47 + 110);
                tt.setToZ(zplace.get(endNode.getFloor()) - 80);
                tt.setToX(endNode.getxCoordinate() / 5 - 247);
                tt.setToY(endNode.getyCoordinate() / 5 - 148 -47 + 110);
                tt.setCycleCount(1);
                //tt.setAutoReverse(true);
                st2.getChildren().add(tt);
            }
        }
        st2.setCycleCount(Timeline.INDEFINITE);
        // st.setAutoReverse(true);
        st2.play();

        MeshView[] hati = loadMeshViews(hat);
        double MODEL_SCALE_FACTOR8 = 0.2;
        for (int i = 0; i < hati.length; i++) {
//            meshViews[i].setTranslateX(0);
//            meshViews[i].setTranslateY(0);
//            meshViews[i].setTranslateZ(0);
            hati[i].setScaleX(MODEL_SCALE_FACTOR8);
            hati[i].setScaleY(MODEL_SCALE_FACTOR8);
            hati[i].setScaleZ(MODEL_SCALE_FACTOR8);

            PhongMaterial sample = new PhongMaterial();
            Image image2 = new Image(("images/gold.jpg"));
            sample.setDiffuseMap(image2);
            //sample.setSpecularColor(Color.BEIGE);
            //sample.setSpecularPower(16);
            hati[i].setMaterial(sample);

            hati[i].getTransforms().setAll(new Rotate(0, Rotate.Z_AXIS), new Rotate(90, Rotate.X_AXIS));
        }
        SequentialTransition st3 = new SequentialTransition();
        for(int i = 0; i < nodes.size() - 1; i++) {
            HashMap<Integer, Integer> zplace = new HashMap<Integer, Integer>();
            zplace.put(1, 100);
            zplace.put(2, 0);
            zplace.put(3, -100);
            zplace.put(4, -200);
            zplace.put(5, -300);
            NodeData startNode = nodes.get(i);
            NodeData endNode = nodes.get(i + 1);
            int floor1 = startNode.getFloor();
            int floor2 = endNode.getFloor();
            boolean sameFloor = (floor1 == floor2);

            for(int j = 0; j < clari.length; j++) {
                TranslateTransition tt = new TranslateTransition(Duration.millis(1000), hati[j]); //can change mph
                if (!sameFloor) {
                    tt.setDuration(Duration.millis(500));
                }
                tt.setFromZ(zplace.get(startNode.getFloor()) - 80);
                tt.setFromX(startNode.getxCoordinate() / 5 - 247);
                tt.setFromY(startNode.getyCoordinate() / 5 - 148 -47 + 150);
                tt.setToZ(zplace.get(endNode.getFloor()) - 80);
                tt.setToX(endNode.getxCoordinate() / 5 - 247);
                tt.setToY(endNode.getyCoordinate() / 5 - 148 -47 + 150);
                tt.setCycleCount(1);
                //tt.setAutoReverse(true);
                st3.getChildren().add(tt);
            }
        }
        st3.setCycleCount(Timeline.INDEFINITE);
        // st.setAutoReverse(true);
        st3.play();

        Group root = new Group(meshViews);
        Group root10 = new Group(meshViews2);
        Group root6 = new Group(meshViews3);
        Group root9 = new Group(clari);
        Group root80 = new Group(hati);
        group.getChildren().add(root);
        group.getChildren().add(root10);
        group.getChildren().add(root6);
        group.getChildren().add(root9);
        group.getChildren().add(root80);


//        Path animated_path = new Path();
//        animated_path.getElements().add(new MoveTo(nodes.get(0).getxCoordinate() / 5 - 247, nodes.get(0).getyCoordinate() / 5 - 148));
//        for (NodeData node_itrat : nodes) {
//                animated_path.getElements().add(new LineTo(node_itrat.getxCoordinate() / 5 - 247, node_itrat.getyCoordinate() / 5 - 148));
//        }
//        PathTransition pathTransition = new PathTransition();
//        pathTransition.setDuration(Duration.seconds(0.5));
//        pathTransition.setPath(animated_path);
//        pathTransition.setNode(sphere);
//        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
//        pathTransition.setCycleCount(Timeline.INDEFINITE);
////pathTransition.setAutoReverse(true);
//        pathTransition.play();



        group.getChildren().add(wongBox);

       /* for(NodeData data : nodes) {
            if(data.getFloor() == 2 || data.getFloor() == 3) {
                Sphere sphere = new Sphere(5);
                sphere.setTranslateX(data.getxCoordinate() / 5 - 247);
                sphere.setTranslateY(data.getyCoordinate() / 5 - 148);
                if(data.getFloor() == 3) {
                    sphere.setTranslateZ(-100);
                }
                PhongMaterial material = new PhongMaterial();
                Image image = new Image(("images/geometric.jpg"));
                material.setDiffuseMap(image);
                sphere.setMaterial(material);
                group.getChildren().add(sphere);
            }
        }

        Set<EdgeData> ed = dbc.getAllEdges();*/

        /*for(EdgeData data : ed) {
            if(dbc.getNode(data.getStartNode()).getFloor() == 2 || dbc.getNode(data.getStartNode()).getFloor() == 3) {
                NodeData startNode = dbc.getNode(data.getStartNode());
                NodeData endNode = dbc.getNode(data.getEndNode());
                if(dbc.getNode(data.getStartNode()).getFloor() == 2) {
                    Point3D point3 = new Point3D(startNode.getxCoordinate() / 5 - 247, startNode.getyCoordinate() / 5 - 148, 0);
                    Point3D point4 = new Point3D(endNode.getxCoordinate() / 5 - 247, endNode.getyCoordinate() / 5 - 148, 0);
                    group.getChildren().add(createConnection(point3, point4, 2));
                }
                if(dbc.getNode(data.getStartNode()).getFloor() == 3) {
                    Point3D point3 = new Point3D(startNode.getxCoordinate() / 5 - 247, startNode.getyCoordinate() / 5 - 148, -100);
                    Point3D point4 = new Point3D(endNode.getxCoordinate() / 5 - 247, endNode.getyCoordinate() / 5 - 148, -100);
                    group.getChildren().add(createConnection(point3, point4, 2));
                }
            }
        }*/
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
             /*   if(startNode.getNodeID().equals("SELEV00X02")) {
                    NodeData elevr1 = dbc.getNode("SELEV00X02");
                    NodeData elevr2 = dbc.getNode("SELEV00X03");
                    Point3D elev1 = new Point3D(elevr1.getxCoordinate() / 5 - 247, elevr1.getyCoordinate() / 5 - 148, 0);
                    Point3D elev2 = new Point3D(elevr2.getxCoordinate() / 5 - 247, elevr2.getyCoordinate() / 5 - 148, -100);
                    group.getChildren().add(createConnection(elev1, elev2, 5));
                }
            if(startNode.getNodeID().equals("SELEV00Y02")) {
                NodeData elevr1 = dbc.getNode("SELEV00Y02");
                NodeData elevr2 = dbc.getNode("SELEV00Y03");
                Point3D elev1 = new Point3D(elevr1.getxCoordinate() / 5 - 247, elevr1.getyCoordinate() / 5 - 148, 0);
                Point3D elev2 = new Point3D(elevr2.getxCoordinate() / 5 - 247, elevr2.getyCoordinate() / 5 - 148, -100);
                group.getChildren().add(createConnection(elev1, elev2, 5));
            }
            if(startNode.getNodeID().equals("SELEV00Z02")) {
                NodeData elevr1 = dbc.getNode("SELEV00Z02");
                NodeData elevr2 = dbc.getNode("SELEV00Z03");
                Point3D elev1 = new Point3D(elevr1.getxCoordinate() / 5 - 247, elevr1.getyCoordinate() / 5 - 148, 0);
                Point3D elev2 = new Point3D(elevr2.getxCoordinate() / 5 - 247, elevr2.getyCoordinate() / 5 - 148, -100);
                group.getChildren().add(createConnection(elev1, elev2, 5));
            }*/
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

        primaryStage.setTitle("lol ez");
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
        double scale = 1;
//        imageView.setScaleX(scale);
//        imageView.setScaleY(scale);
//        imageView.setScaleZ(scale);
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

