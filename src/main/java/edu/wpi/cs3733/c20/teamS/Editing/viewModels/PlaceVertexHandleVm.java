package edu.wpi.cs3733.c20.teamS.Editing.viewModels;

import edu.wpi.cs3733.c20.teamS.Settings;
import javafx.scene.Parent;

/**
 * The clickable "handle" that is displayed when placing the vertex points of a Room in the
 * map editor.
 */
public class PlaceVertexHandleVm extends Parent {
    public PlaceVertexHandleVm(double x, double y) {
        setTranslateX(x);
        setTranslateY(y);
        HighlightCircle mask = new HighlightCircle(5, 8);
        mask.setNormalFillColor(Settings.get().editRoomColorNormal());
        mask.setHighlightFillColor(Settings.get().editRoomColorHighlight());
        getChildren().add(mask);
//        Circle circle = new Circle();
//        circle.setCenterX(0);
//        circle.setCenterY(0);
//        circle.setRadius(Settings.get().editRoomVertexRadius());
//        circle.setFill(Settings.get().editRoomColorNormal());
//        getChildren().add(circle);
//
//        ReadOnlyReactiveProperty<Boolean> isMouseOver = RxAdaptors.createIsMouseOverStream(this);
//        isMouseOver.changed()
//                .subscribe(huh -> {
//                    Color fill = huh ?
//                            Settings.get().editRoomColorHighlight() :
//                            Settings.get().editRoomColorNormal();
//                    circle.setFill(fill);
//                });
    }
}
