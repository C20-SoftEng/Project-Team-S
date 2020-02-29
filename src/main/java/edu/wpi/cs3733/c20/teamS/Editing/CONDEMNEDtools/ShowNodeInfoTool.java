package edu.wpi.cs3733.c20.teamS.Editing.CONDEMNEDtools;

import edu.wpi.cs3733.c20.teamS.Editing.NodeEditScreen;
import edu.wpi.cs3733.c20.teamS.database.NodeData;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ShowNodeInfoTool implements IEditingTool {

    @Override
    public void onNodeClicked(NodeData node, MouseEvent event) {
        Stage stage = new Stage();
        NodeEditScreen.showDialog(stage, node)
                .subscribe(e -> {
                    stage.close();
                });
    }
}
