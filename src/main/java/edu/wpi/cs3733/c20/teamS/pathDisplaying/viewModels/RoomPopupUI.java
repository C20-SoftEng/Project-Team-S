package edu.wpi.cs3733.c20.teamS.pathDisplaying.viewModels;

import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import edu.wpi.cs3733.c20.teamS.collisionMasks.Room;
import edu.wpi.cs3733.c20.teamS.utilities.rx.RxAdaptors;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * Class responsible for rendering the popup information on the main pathfinding
 * screen when you hover over a room.
 */
class RoomPopupUI extends Parent {
    private final Room room;
    private final VBox vbox = new VBox();
    private final ImageView iconView = new ImageView();
    private final Label nameLabel = new Label();
    private final HBox hbox = new HBox();
    private final TextArea descriptionTextArea = new TextArea();

    public RoomPopupUI(Room room) {
        if (room == null) ThrowHelper.illegalNull("room");

        this.room = room;
        tryInitImage(iconView);
        iconView.setFitWidth(75);
        iconView.setFitHeight(75);
        nameLabel.setFont(new Font(nameLabel.getFont().getFamily(), 22));
        hbox.getChildren().addAll(iconView, nameLabel);

        vbox.getChildren().addAll(hbox, descriptionTextArea);
        getChildren().add(vbox);

        updateUI();

        room.nameChanged().map(n -> RxAdaptors.UNIT)
                .mergeWith(room.descriptionChanged())
                .mergeWith(room.iconChanged())
                .subscribe(u -> updateUI());

        RxAdaptors.propertyStream(nameLabel.widthProperty())
                .subscribe(descriptionTextArea::setPrefWidth);
    }

    private void tryInitImage(ImageView imageView) {
        if (room.icon() == null || room.icon().isEmpty())
            return;

        try {
            Image image = new Image(room.icon());
            imageView.setImage(image);
        }
        catch (Exception ex) {
            System.err.println("Failed to load icon " + room.icon() + " for room " + room.name());
        }
    }
    private void updateUI() {
        nameLabel.setText(room.name());
        descriptionTextArea.setText(room.description());
        tryInitImage(iconView);

        boolean eitherNullOrEmpty = isNullOrEmptyWhenTrimmed(room.name()) ||
                isNullOrEmptyWhenTrimmed(room.description());
        nameLabel.setVisible(!eitherNullOrEmpty);
        descriptionTextArea.setVisible(!eitherNullOrEmpty);
        iconView.setVisible(!eitherNullOrEmpty);
    }

    private static boolean isNullOrEmptyWhenTrimmed(String string) {
        return string == null || string.trim().isEmpty();
    }
}
