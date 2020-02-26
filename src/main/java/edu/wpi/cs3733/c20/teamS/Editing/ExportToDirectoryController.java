package edu.wpi.cs3733.c20.teamS.Editing;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.c20.teamS.Editing.tools.ObservableGraph;
import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import edu.wpi.cs3733.c20.teamS.collisionMasks.FileSystemHitboxRepository;
import edu.wpi.cs3733.c20.teamS.collisionMasks.Hitbox;
import edu.wpi.cs3733.c20.teamS.collisionMasks.HitboxRepository;
import edu.wpi.cs3733.c20.teamS.widgets.AutoComplete;
import io.reactivex.rxjava3.core.Observable;

import java.io.File;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.function.Supplier;

public class ExportToDirectoryController {
    private static final String HITBOX_FILE_NAME = "hitboxes.txt";

    private final JFXTextField directoryTextField;
    private final Observable<String> directoryPathChanged;
    private final JFXButton exportButton;
    private final Supplier<Collection<Hitbox>> hitboxSupplier;

    public ExportToDirectoryController(
            JFXTextField directoryTextField,
            JFXButton exportButton,
            Supplier<Collection<Hitbox>> hitboxSupplier) {

        if (directoryTextField == null) ThrowHelper.illegalNull("directoryTextField");
        if (exportButton == null) ThrowHelper.illegalNull("exportButton");
        if (hitboxSupplier == null) ThrowHelper.illegalNull("hitboxSupplier");

        this.directoryTextField = directoryTextField;
        this.exportButton = exportButton;
        this.hitboxSupplier = hitboxSupplier;
        directoryPathChanged = AutoComplete.propertyStream(directoryTextField.textProperty());

        initEventHandlers();
        exportButton.setDisable(true);
    }
    private void initEventHandlers() {
        directoryPathChanged()
                .map(File::new)
                .map(File::isDirectory)
                .subscribe(isDirectory -> {
                    exportButton.setDisable(!isDirectory);
                });
        exportButton.setOnAction(e -> exportAll());
    }

    public String directoryPath() {
        return directoryTextField.getText();
    }
    public void setDirectoryPath(String value) {
        directoryTextField.setText(value);
    }
    public Observable<String> directoryPathChanged() {
        return directoryPathChanged;
    }



    private void exportAll() {
        String hitboxPath = Paths.get(directoryPath(), HITBOX_FILE_NAME).toString();
        HitboxRepository repo = new FileSystemHitboxRepository(hitboxPath);
        repo.save(hitboxSupplier.get());
    }
}
