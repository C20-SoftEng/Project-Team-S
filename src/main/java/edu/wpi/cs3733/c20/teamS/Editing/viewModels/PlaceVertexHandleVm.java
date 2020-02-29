package edu.wpi.cs3733.c20.teamS.Editing.viewModels;

import edu.wpi.cs3733.c20.teamS.Settings;
import edu.wpi.cs3733.c20.teamS.utilities.rx.ReadOnlyReactiveProperty;
import edu.wpi.cs3733.c20.teamS.utilities.rx.RxAdaptors;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class PlaceVertexHandleVm extends Parent {
    public PlaceVertexHandleVm(double x, double y) {
        setTranslateX(x);
        setTranslateY(y);

        Circle circle = new Circle();
        circle.setCenterX(0);
        circle.setCenterY(0);
        circle.setRadius(5);
        circle.setFill(Settings.get().editHitboxColorNormal());
        getChildren().add(circle);

        ReadOnlyReactiveProperty<Boolean> isMouseOver = RxAdaptors.createIsMouseOverStream(this);
        isMouseOver.changed()
                .subscribe(huh -> {
                    Color fill = huh ?
                            Settings.get().editHitboxColorHighlight() :
                            Settings.get().editHitboxColorNormal();
                    circle.setFill(fill);
                });
    }
}
