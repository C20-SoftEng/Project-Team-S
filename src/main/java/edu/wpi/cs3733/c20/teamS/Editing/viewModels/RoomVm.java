package edu.wpi.cs3733.c20.teamS.Editing.viewModels;

import edu.wpi.cs3733.c20.teamS.Settings;
import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import edu.wpi.cs3733.c20.teamS.collisionMasks.Room;
import edu.wpi.cs3733.c20.teamS.utilities.numerics.Vector2;
import edu.wpi.cs3733.c20.teamS.utilities.rx.ReactiveProperty;
import edu.wpi.cs3733.c20.teamS.utilities.rx.ReadOnlyReactiveProperty;
import edu.wpi.cs3733.c20.teamS.utilities.rx.RxAdaptors;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;

public class RoomVm extends Parent {
    private final Polygon mask;
    private final Label nameLabel;
    private final ReactiveProperty<Boolean> highlightOnMouseOver = new ReactiveProperty<>(true);
    private final ReadOnlyReactiveProperty<Boolean> isMouseOver = RxAdaptors.createIsMouseOverStream(this);

    public RoomVm(Room room) {
        if (room == null) ThrowHelper.illegalNull("room");

        mask = createPolygon(room);
        nameLabel = createNameLabel(room);

        getChildren().add(mask);
        getChildren().add(nameLabel);

        Vector2 centroid = room.centroid();
        setTranslateX(centroid.x());
        setTranslateY(centroid.y());

        isMouseOver.changed()
                .mergeWith(highlightOnMouseOver.changed())
                .map(e -> highlightOnMouseOver.value() && isMouseOver.value())
                .subscribe(huh -> {
                    Color fill = huh ?
                            Settings.get().editHitboxColorHighlight() :
                            Settings.get().editHitboxColorNormal();
                    mask.setFill(fill);
                });

        setNameFontSize(24);
        setNameVisible(false);
    }

    public boolean highlightOnMouseOver() {
        return highlightOnMouseOver.value();
    }
    public void setHighlightOnMouseOver(boolean value) {
        highlightOnMouseOver.setValue(value);
    }
    public void setNameFontSize(double value) {
        Font font = new Font(nameLabel.getFont().getFamily(), value);
        nameLabel.setFont(font);
    }
    public double nameFontSize() {
        return nameLabel.getFont().getSize();
    }
    public boolean isNameVisible() {
        return nameLabel.isVisible();
    }
    public void setNameVisible(boolean value) {
        nameLabel.setVisible(value);
    }

    private static Polygon createPolygon(Room room) {
        Polygon result = new Polygon();
        Vector2 centroid = room.centroid();
        room.vertices().stream()
                .map(vertex -> vertex.subtract(centroid))
                .forEach(local -> result.getPoints().addAll(local.x(), local.y()));
        result.setFill(Settings.get().editHitboxColorNormal());
        return result;
    }

    private Label createNameLabel(Room room) {
        Label result = new Label();
        result.setText(room.name());
        room.nameChanged().subscribe(result::setText);
        result.setMouseTransparent(true);
        RxAdaptors.propertyStream(result.boundsInParentProperty())
                .subscribe(bounds -> {
                   result.setTranslateX(-bounds.getWidth() / 2);
                   result.setTranslateY(-bounds.getHeight() / 2);
                });
        return result;
    }
}
