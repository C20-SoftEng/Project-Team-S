package edu.wpi.cs3733.c20.teamS.Editing.tools;

import edu.wpi.cs3733.c20.teamS.ThrowHelper;

public abstract class Memento {
    public abstract void execute();
    public abstract void undo();
    public void redo() {
        execute();
    }

    public static Memento create(Runnable execute, Runnable undo, Runnable redo) {
        if (execute == null) ThrowHelper.illegalNull("execute");
        if (undo == null) ThrowHelper.illegalNull("undo");
        if (redo == null) ThrowHelper.illegalNull("redo");

        return new Memento() {
            @Override public void execute() {
                execute.run();
            }
            @Override public void undo() {
                undo.run();
            }
            @Override public void redo() {
                redo.run();
            }
        };
    }
    public static Memento create(Runnable execute, Runnable undo) {
        if (execute == null) ThrowHelper.illegalNull("execute");
        if (undo == null) ThrowHelper.illegalNull("undo");

        return new Memento() {
            @Override
            public void execute() {
                execute.run();
            }

            @Override
            public void undo() {
                undo.run();
            }
        };
    }
}
