package edu.wpi.cs3733.c20.teamS.Editing.tools;

public final class ToolSelector {
    private EditingTool current;

    public EditingTool current() {
        return current;
    }
    public void setCurrent(EditingTool value) {
        EditingTool previous = current;
        current = value;
        if (previous != null)
            previous.dispose();
    }
}
