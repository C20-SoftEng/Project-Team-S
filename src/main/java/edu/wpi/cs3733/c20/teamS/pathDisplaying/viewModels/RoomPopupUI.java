package edu.wpi.cs3733.c20.teamS.pathDisplaying.viewModels;

import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import edu.wpi.cs3733.c20.teamS.collisionMasks.Room;
import edu.wpi.cs3733.c20.teamS.utilities.rx.RxAdaptors;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * Class responsible for rendering the popup information on the main pathfinding
 * screen when you hover over a room.
 */
class RoomPopupUI extends Parent {
    private final Room room;
    private final VBox vbox = new VBox();
    private final Label nameLabel = new Label();
    private final TextArea descriptionTextArea = new TextArea();

    public RoomPopupUI(Room room) {
        if (room == null) ThrowHelper.illegalNull("room");

        this.room = room;
        nameLabel.setFont(new Font(nameLabel.getFont().getFamily(), 22));
        vbox.getChildren().addAll(nameLabel, descriptionTextArea);
        getChildren().add(vbox);

        updateUI();

        room.nameChanged().map(n -> RxAdaptors.UNIT)
                .mergeWith(room.descriptionChanged())
                .mergeWith(room.iconChanged())
                .subscribe(u -> updateUI());
        RxAdaptors.propertyStream(nameLabel.widthProperty())
                .subscribe(width -> {
                    descriptionTextArea.setPrefWidth(width);
                });
    }

    private void updateUI() {
        nameLabel.setText(room.name());
        descriptionTextArea.setText(room.description());
    }
}
