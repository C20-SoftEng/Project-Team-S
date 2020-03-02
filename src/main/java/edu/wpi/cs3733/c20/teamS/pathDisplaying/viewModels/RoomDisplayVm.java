package edu.wpi.cs3733.c20.teamS.pathDisplaying.viewModels;

import edu.wpi.cs3733.c20.teamS.Settings;
import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import edu.wpi.cs3733.c20.teamS.collisionMasks.Room;
import edu.wpi.cs3733.c20.teamS.utilities.numerics.Vector2;
import edu.wpi.cs3733.c20.teamS.utilities.rx.ReactiveProperty;
import edu.wpi.cs3733.c20.teamS.utilities.rx.ReadOnlyReactiveProperty;
import edu.wpi.cs3733.c20.teamS.utilities.rx.RxAdaptors;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class RoomDisplayVm extends Parent {
    private static final Object UNIT = new Object();
    private final Room room;
    private final Polygon mask;
    private final ReadOnlyReactiveProperty<Boolean> isMouseOver;
    private final ReactiveProperty<Color> normalFillColor = new ReactiveProperty<>(Color.TRANSPARENT);
    private final ReactiveProperty<Color> highlightFillColor = new ReactiveProperty<>(
            Settings.get().editRoomColorHighlight());

    public RoomDisplayVm(Room room) {
        if (room == null) ThrowHelper.illegalNull("room");

        this.room = room;
        Vector2 centroid = room.centroid();
        setTranslateX(centroid.x());
        setTranslateY(centroid.y());

        mask = new Polygon();
        room.vertices().stream()
                .map(vertex -> vertex.subtract(centroid))
                .forEach(vertex -> mask.getPoints().addAll(vertex.x(), vertex.y()));
        getChildren().add(mask);

        isMouseOver = RxAdaptors.createMouseOverStream(this);
        isMouseOver.changed().map(huh -> UNIT)
                .mergeWith(normalFillColor.changed())
                .mergeWith(highlightFillColor.changed())
                .subscribe(u -> updateHighlightState());
    }

    private void updateHighlightState() {
        Color fill = isMouseOver.value() ?
                highlightFillColor.value() :
                normalFillColor.value();
        mask.setFill(fill);
    }
}
