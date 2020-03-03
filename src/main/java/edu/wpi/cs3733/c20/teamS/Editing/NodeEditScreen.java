package edu.wpi.cs3733.c20.teamS.Editing;

import edu.wpi.cs3733.c20.teamS.BaseScreen;
import edu.wpi.cs3733.c20.teamS.Settings;
import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import edu.wpi.cs3733.c20.teamS.app.DialogEvent;
import edu.wpi.cs3733.c20.teamS.database.NodeData;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import javafx.event.Event;
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
        Settings.openWindows.add(this.stage);
        BaseScreen.puggy.register(scene, Event.ANY);
        stage.show();
    }

    public static Observable<DialogEvent<NodeData>> showDialog(Stage stage) {
        if (stage == null) ThrowHelper.illegalNull("stage");

        PublishSubject<DialogEvent<NodeData>> subject = PublishSubject.create();
        NodeEditScreen screen = new NodeEditScreen(stage);

        screen.ui.okClicked().subscribe(o -> {
            NodeData result = new NodeData();
            result.setNodeType(screen.ui.nodeType());
            result.setShortName(screen.ui.shortName());
            result.setLongName(screen.ui.fullName());
            subject.onNext(DialogEvent.ok(result));
        });
        screen.ui.cancelClicked().subscribe(o -> subject.onNext(DialogEvent.cancel()));
        screen.show();
        return subject;
    }
    public static Observable<DialogEvent<NodeData>> showDialog(
            Stage stage, String nodeType,
            String shortName, String longName
    ) {
        NodeData node = new NodeData();
        node.setNodeType(nodeType);
        node.setShortName(shortName);
        node.setLongName(longName);

        return showDialog(stage, node);
    }
    public static Observable<DialogEvent<NodeData>> showDialog(Stage stage, NodeData node) {
        if (stage == null) ThrowHelper.illegalNull("stage");
        if (node == null) ThrowHelper.illegalNull("node");

        PublishSubject<DialogEvent<NodeData>> subject = PublishSubject.create();
        NodeEditScreen screen = new NodeEditScreen(stage);

        screen.ui.setNodeType(node.getNodeType());
        screen.ui.setShortName(node.getShortName());
        screen.ui.setFullName(node.getLongName());

        screen.ui.okClicked().subscribe(o -> {
            node.setNodeType(screen.ui.nodeType());
            node.setShortName(screen.ui.shortName());
            node.setLongName(screen.ui.fullName());

            subject.onNext(DialogEvent.ok(node));
        });
        screen.ui.cancelClicked().subscribe(o -> subject.onNext(DialogEvent.cancel()));

        screen.show();
        return subject;
    }
}
