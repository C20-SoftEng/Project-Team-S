package edu.wpi.cs3733.c20.teamS.Editing.tools;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UndoBufferTests {
    private static class Indicator extends Memento {
        private int executeCount = 0;
        private int undoCount = 0;
        private int redoCount = 0;

        @Override public void execute() {
            executeCount ++;
        }
        @Override public void undo() {
            undoCount ++;
        }
        @Override public void redo() {
            redoCount ++;
        }

        public int executeCount() {
            return executeCount;
        }
        public int undoCount() {
            return undoCount;
        }
        public int redoCount() {
            return redoCount;
        }
    }

    public static class EmptyBuffer {
        private final UndoBuffer empty = new UndoBuffer();

        @Test
        public void cannotUndo() {
            assertFalse(empty.canUndo());
        }

        @Test
        public void cannotRedo() {
            assertFalse(empty.canRedo());
        }
    }

    public static class SingletonBuffer {
        private final UndoBuffer buffer = new UndoBuffer();
        private final Indicator memento = new Indicator();

        public SingletonBuffer() {
            buffer.execute(memento);
        }

        @Test
        public void canUndo() {
            assertTrue(buffer.canUndo());
        }

        @Test
        public void cannotRedo() {
            assertFalse(buffer.canRedo());
        }

        @Test
        public void calledMementoExecuteMethod() {
            assertEquals(1, memento.executeCount());
        }
    }

    public static class UndoneBuffer {
        private final UndoBuffer buffer = new UndoBuffer();
        private final Indicator memento = new Indicator();

        public UndoneBuffer() {
            buffer.execute(memento);
            buffer.undo();
        }

        @Test
        public void calledUndo() {
            assertEquals(1, memento.undoCount());
        }

        @Test
        public void canRedo() {
            assertTrue(buffer.canRedo());
        }
    }

    public static class RedoneBuffer {
        private final UndoBuffer buffer = new UndoBuffer();
        private final Indicator memento = new Indicator();

        public RedoneBuffer() {
            buffer.execute(memento);
            buffer.undo();
            buffer.redo();
        }

        @Test
        public void calledRedo() {
            assertEquals(1, memento.redoCount());
        }
    }
}
