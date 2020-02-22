package edu.wpi.cs3733.c20.teamS.Editing;

import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import edu.wpi.cs3733.c20.teamS.app.DialogEvent;
import edu.wpi.cs3733.c20.teamS.database.NodeData;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class NodeEditScreen {
    private NodeEditController ui;
    private final Stage stage;
    private final Scene scene;

    private NodeEditScreen(Stage stage) {
        this.stage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/NodeEditScreen.fxml"));
        loader.setControllerFactory(c -> ui = new NodeEditController());
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
        stage.show();
    }

    public static Observable<DialogEvent<NodeData>> showDialog(Stage stage) {
        if (stage == null) ThrowHelper.illegalNull("stage");

        PublishSubject<DialogEvent<NodeData>> subject = PublishSubject.create();
        NodeEditScreen screen = new NodeEditScreen(stage);

        screen.ui.okClicked().subscribe(o -> {
            NodeData result = new NodeData();
            result.setNodeType(screen.ui.nodeType());
            result.setShortName(screen.ui.shortNodeName());
            result.setLongName(screen.ui.fullNodeName());
            subject.onNext(DialogEvent.ok(result));
        });
        screen.ui.cancelClicked().subscribe(o -> subject.onNext(DialogEvent.cancel()));
        screen.show();
        return subject;
    }
}
