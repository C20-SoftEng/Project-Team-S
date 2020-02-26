package edu.wpi.cs3733.c20.teamS;

import edu.wpi.cs3733.c20.teamS.pathDisplaying.MainScreenController;
import edu.wpi.cs3733.c20.teamS.pathfinding.AStar;
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
import javafx.scene.control.TextField;
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


import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.Timer;

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
    JFXTextField weatherField;
    @FXML
    JFXTextArea weatherSummary;
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
    JFXTextArea timeField;
    @FXML
    ImageView imageID;
    @FXML
    StackPane startScreenTap;


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        timeBox.setStyle("-fx-background-color: rgba(255, 255, 255, 255);");

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(" HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();


        timeField.setText("    " + dtf.format(now));
        timeField.setStyle("-fx-font-size: 40px");

        WeatherBox weatherBox1 = new WeatherBox();

        weatherField.setText((String.valueOf("                   " + weatherBox1.getTemp())) + " Degrees");
        weatherField.setStyle("-fx-font-size: 16px");
        weatherSummary.setText("         " + weatherBox1.summary());
        weatherSummary.setStyle("-fx-font-size: 25px");
        //weatherSummary.setStyle("-fx-padding: 0 10 0 10");


        String icon = weatherBox1.icon();

        if (icon.contains("clear") && icon.contains("day")) {
            icon = "weatherIcons/SunImage.jpeg";
        } else if (icon.contains("clear") && icon.contains("night")) {
            icon = "weatherIcons/MoonImage.jpeg";
        } else if (icon.contains("rain") || icon.contains("sleet")) {
            icon = "weatherIcons/rain.png";
        } else if (icon.contains("partly") && icon.contains("day")) {
            icon = "weatherIcons/PartlyCloudy.png";
        } else if (icon.contains("partly") && icon.contains("night")) {
            icon = "weatherIcons/PartlyCloudyNightImage.png";
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
        imageID.setFitWidth(289);
        imageID.setPreserveRatio(false);
        Tweetbox tweetbox = new Tweetbox();

        firstTweet.setText(tweetbox.getTweets("@FaulknerHosp").get(1));
        firstTweet.setStyle("-fx-text-fill: white");
        secondTweet.setText(tweetbox.getTweets("@FaulknerHosp").get(2));
        secondTweet.setStyle("-fx-text-fill: white");
        ThirdTweet.setText(tweetbox.getTweets("@FaulknerHosp").get(3));
        ThirdTweet.setStyle("-fx-text-fill: white");
        fourthTweet.setText(tweetbox.getTweets("@FaulknerHosp").get(4));
        fourthTweet.setStyle("-fx-text-fill: white");
        fifthTweet.setText(tweetbox.getTweets("@FaulknerHosp").get(5));
        fifthTweet.setStyle("-fx-text-fill: white");

        ImageView tutorialView = new ImageView();
        startScreenTap.getChildren().add(tutorialView);


        tutorialView.setImage(new Image(this.getClass().getResource("/images/PugLickingScreen.gif").toExternalForm()));
        tutorialView.setPreserveRatio(true);

        tutorialView.fitWidthProperty().bind(startScreenTap.widthProperty());
    }

    MainStartScreenController() {

    }

    @FXML
    private void onScreenClicked(ActionEvent event) {
        MainToLoginScreen maintolog = new MainToLoginScreen((Stage) (startScreenTap.getScene().getWindow()));
        maintolog.show();

    }
}
