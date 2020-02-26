package edu.wpi.cs3733.c20.teamS.pathDisplaying;

import com.jfoenix.controls.JFXButton;
import javafx.scene.image.Image;

class Floor {
    public final Image image;
    public final JFXButton button;

    public Floor(JFXButton button, Image image) {
        this.image = image;
        this.button = button;
    }
    public Floor(JFXButton button, String imagePath) {
        this(button, new Image(imagePath));
    }
}
