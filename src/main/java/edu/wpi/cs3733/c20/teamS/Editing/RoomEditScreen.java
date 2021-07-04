package edu.wpi.cs3733.c20.teamS.Editing;

import edu.wpi.cs3733.c20.teamS.BaseScreen;
import edu.wpi.cs3733.c20.teamS.Settings;
import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import edu.wpi.cs3733.c20.teamS.app.DialogEvent;
import edu.wpi.cs3733.c20.teamS.collisionMasks.Room;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public final class RoomEditScreen {
    private final Stage stage;
    private final Scene scene;
    private RoomEditController ui;

    private RoomEditScreen(Stage stage) {
        if (stage == null) ThrowHelper.illegalNull("stage");

        this.stage = stage;
        this.stage.setAlwaysOnTop(true);
        this.stage.initStyle(StageStyle.UNDECORATED);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/RoomEditScreen.fxml"));
        loader.setControllerFactory(c -> ui = new RoomEditController());
        try {
            Parent root = loader.load();
            scene = new Scene(root);
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void show() {
        stage.setScene(scene);
        Settings.openWindows.add(stage);
        BaseScreen.puggy.register(scene, Event.ANY);
        stage.show();
    }

    public static Observable<DialogEvent<Room>> showDialog(Stage stage, Room room) {
        if (room == null) ThrowHelper.illegalNull("room");

        PublishSubject<DialogEvent<Room>> subject = PublishSubject.create();
        RoomEditScreen screen = new RoomEditScreen(stage);
        screen.ui.setName(room.name());
        screen.ui.setDescription(room.description());
        screen.ui.setIconPath(room.icon());
        screen.ui.okClicked()
                .subscribe(o -> {
                    room.setName(screen.ui.name());
                    room.setDescription(screen.ui.description());
                    room.setIcon(screen.ui.iconPath());
                    stage.close();
                    subject.onNext(DialogEvent.ok(room));
                });
        screen.ui.cancelClicked().subscribe(o -> {
            stage.close();
            subject.onNext(DialogEvent.cancel());
        });
        screen.show();

        return subject;
    }
}
