package edu.wpi.cs3733.c20.teamS;

import com.sun.javafx.css.StyleManager;
import edu.wpi.cs3733.c20.teamS.pathDisplaying.MainScreenController;
import edu.wpi.cs3733.c20.teamS.pathfinding.AStar;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.c20.teamS.pathfinding.IPathfinder;
import edu.wpi.cs3733.c20.teamS.utilities.Tweetbox;
import edu.wpi.cs3733.c20.teamS.utilities.WeatherBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import animatefx.animation.*;



import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class MainStartScreenController implements Initializable {
    private MainStartScreen splash;
    private Scene scene;
    private Stage stage;

    @FXML
    AnchorPane startScreen;
    @FXML
    VBox weatherBox;
    @FXML
    VBox timeBox;
    @FXML
    JFXButton screenButton;
    @FXML
    Label weatherField;
@FXML
    JFXTextArea firstTweet;
    @FXML
    JFXTextArea secondTweet;
    @FXML
    JFXTextArea ThirdTweet;
    @FXML
    JFXTextArea fourthTweet;
    @FXML
    JFXTextArea fifthTweet;
    @FXML
    Label timeField;
    @FXML
    ImageView imageID;
    @FXML
    StackPane startScreenTap;

    ImageView tutorialView;

    LinkedList<Image> imageList = new LinkedList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Application.setUserAgentStylesheet("modena.css");
        StyleManager.getInstance().addUserAgentStylesheet("default.css");
        //timeField.setText("    " + dtf.format(now));
        timeField.setStyle("-fx-font-size: 70px");
        initClock();

        WeatherBox weatherBox1 = new WeatherBox();

        weatherField.setText((String.valueOf(weatherBox1.getTemp())) + "\u00B0");
        weatherField.setStyle("-fx-font-size: 55px");

        //HashSet<Image> imageList = new HashSet<>();
        Image image0 = new Image(String.valueOf(getClass().getResource("/images/TutorialPhotos/image1.png")));
        imageList.add(image0);
        Image image1 = new Image(String.valueOf(getClass().getResource("/images/TutorialPhotos/image1.png")));
        imageList.add(image1);
        Image image2 = new Image(String.valueOf(getClass().getResource("/images/TutorialPhotos/image2.png")));
        imageList.add(image2);
        Image image3 = new Image(String.valueOf(getClass().getResource("/images/TutorialPhotos/image3.png")));
        imageList.add(image3);



        String icon = weatherBox1.icon();

        if (icon.contains("clear") && icon.contains("day")) {
            icon = "weatherIcons/SunImage.png";
        } else if (icon.contains("clear") && icon.contains("night")) {
            icon = "weatherIcons/MoonImage.png";
        } else if (icon.contains("rain") || icon.contains("sleet")) {
            icon = "weatherIcons/rain.png";
        } else if (icon.contains("partly") && icon.contains("day")) {
            icon = "weatherIcons/PartlyCloudy.png";
        } else if (icon.contains("partly") && icon.contains("night")) {
            icon = "weatherIcons/PartlyCloudyNight.png";
        } else if (icon.contains("cloudy")) {
            icon = "weatherIcons/Cloudy.png";
        } else if (icon.contains("fog")) {
            icon = "weatherIcons/Foggy.png";
        } else if (icon.contains("snow")) {
            icon = "weatherIcons/Snow.png";
        } else if (icon.contains("wind")) {
            icon = "weatherIcons/wind.png";
        } else {
            icon = "weatherIcons/ThunderStorm.png";
        }

        Image image = new Image(String.valueOf(getClass().getResource("/images/" + icon)));

        imageID.setImage(image);
        imageID.setFitHeight(171);
        imageID.setFitWidth(171);
        imageID.setPreserveRatio(false);
        Tweetbox tweetbox = new Tweetbox();

        firstTweet.setText(tweetbox.getTweets("@FaulknerHosp").get(0));
        firstTweet.setStyle("-fx-text-fill: white");
        secondTweet.setText(tweetbox.getTweets("@FaulknerHosp").get(1));
        secondTweet.setStyle("-fx-text-fill: white");
        ThirdTweet.setText(tweetbox.getTweets("@FaulknerHosp").get(2));
        ThirdTweet.setStyle("-fx-text-fill: white");
        fourthTweet.setText(tweetbox.getTweets("@FaulknerHosp").get(3));
        fourthTweet.setStyle("-fx-text-fill: white");
        fifthTweet.setText(tweetbox.getTweets("@FaulknerHosp").get(4));
        fifthTweet.setStyle("-fx-text-fill: white");

        tutorialView = new ImageView();
        startScreenTap.getChildren().add(tutorialView);


//        tutorialView.setImage(new Image(this.getClass().getResource("/images/PugLickingScreen.gif").toExternalForm()));
        tutorialView.setPreserveRatio(true);
        //tutorialView.s

        tutorialView.fitWidthProperty().bind(startScreenTap.widthProperty());
        //tutorialView.applyCss("-fx-bordercolor");
        ImageSelector is = new ImageSelector();
        imageChangeTimer(is);
    }

    public MainStartScreenController() {

    }

    @FXML
    private void onScreenClicked(ActionEvent event) {
        //MainToLoginScreen maintolog = new MainToLoginScreen((Stage) (startScreenTap.getScene().getWindow()));
        MainToLoginScreen.showDialog();
    }


    private void initClock() {

        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a");
            timeField.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    private void imageChangeTimer(ImageSelector is){
        Timeline imageTimer = new Timeline(new KeyFrame(Duration.seconds(4),e->{
            setTutorial(is.getImage());
        }));
        imageTimer.setCycleCount(Animation.INDEFINITE);
        imageTimer.play();
    }

    void setTutorial(int image){
        ///Image tutImage = new Image(String.valueOf(getClass().getResource("/images/TutorialPhotos/image" + image + ".png")));
        //new FadeIn(tutorialView).setResetOnFinished(true).play();
        new FadeOut(tutorialView).play();
        tutorialView.setOpacity(0);
        tutorialView.setImage(imageList.get(image));
        tutorialView.setPreserveRatio(true);
        tutorialView.setOpacity(0);
        new FadeIn(tutorialView).play();
        //tutorialView.setOpacity(1);

    }

    static class ImageSelector{
        private static int image;
        ImageSelector(){
            image = 1;
        }
        private static void nextImage(){
            if(image == 3){
                image = 1;
            }else{
                image++;
            }
        }

        static int getImage(){
            int val = image;
            nextImage();
            //System.out.println("Image is "+ val);
            return val;
        }
    }


    private void fireAlarm(){

    }

}

