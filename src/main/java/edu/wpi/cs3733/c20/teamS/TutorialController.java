package edu.wpi.cs3733.c20.teamS;


import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;
import java.net.MalformedURLException;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class TutorialController {

    @FXML
    private AnchorPane rootvid;

    @FXML
    private JFXButton back;

    @FXML
    void BackClicked(MouseEvent event) {
        try {
            addMediaView();
        }catch(MalformedURLException e){
            System.out.println("Malformed URL");
        }

    }

    public TutorialController() {

    }

    public void addMediaView() throws MalformedURLException {
        String path = getClass().getResource("/videos/tutorial.mp4").toString();
        Media media = new Media(path);
        MediaPlayer mediaPlayer = new MediaPlayer(media);

        mediaPlayer.setAutoPlay(true);
        MediaView mediaView = new MediaView(mediaPlayer);
        mediaView.fitWidthProperty().bind(rootvid.widthProperty());
        mediaView.fitHeightProperty().bind(rootvid.heightProperty());

        rootvid.getChildren().add(mediaView);
    }

}







