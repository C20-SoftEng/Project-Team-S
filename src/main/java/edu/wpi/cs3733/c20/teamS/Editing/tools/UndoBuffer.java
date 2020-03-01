package edu.wpi.cs3733.c20.teamS.Editing.tools;

import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import edu.wpi.cs3733.c20.teamS.utilities.rx.ReactiveProperty;
import io.reactivex.rxjava3.core.Observable;

import java.util.Stack;

public final class UndoBuffer {
    private final Stack<Memento> undoStack = new Stack<>();
    private final Stack<Memento> redoStack = new Stack<>();
    private ReactiveProperty<Boolean> canUndo = new ReactiveProperty<>(false);
    private ReactiveProperty<Boolean> canRedo = new ReactiveProperty<>(false);

    public UndoBuffer() {
        updateProperties();
    }

    /**
     * Undoes the most recently-performed action.
     * @throws IllegalStateException undo buffer is empty.
     */
    public void undo() {
        if (!canUndo.value())
            throw new IllegalStateException("Undo buffer is empty.");

        Memento memento = undoStack.pop();
        memento.undo();
        redoStack.push(memento);

        updateProperties();
    }

    /**
     * Redoes the most-recently undone action.
     * @throws IllegalStateException redo buffer is empty.
     */
    public void redo() {
        if (!canRedo.value())
            throw new IllegalStateException("Redo buffer is empty.");

        Memento memento = redoStack.pop();
        memento.redo();
        undoStack.push(memento);

        updateProperties();
    }

    /**
     * Executes the specified memento, pushing it onto the undo buffer and clearing the redo buffer.
     * @param memento The memento to execute.
     * @throws IllegalArgumentException 'memento' is null.
     */
    public void execute(Memento memento) {
        if (memento == null) ThrowHelper.illegalNull("memento");

        redoStack.clear();
        memento.execute();
        undoStack.push(memento);

        updateProperties();
    }
    public boolean canUndo() {
        return canUndo.value();
    }
    public boolean canRedo() {
        return canRedo.value();
    }
    public Observable<Boolean> canUndoChanged() {
        return canUndo.changed();
    }
    public Observable<Boolean> canRedoChanged() {
        return canRedo.changed();
    }

    private void updateProperties() {
        canUndo.setValue(!undoStack.isEmpty());
        canRedo.setValue(!redoStack.isEmpty());
    }
}
