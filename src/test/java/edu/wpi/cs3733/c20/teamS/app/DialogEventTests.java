package edu.wpi.cs3733.c20.teamS.app;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

public class DialogEventTests {

    public static class OKEvent {
        private final String value = "Blaah";
        private final DialogEvent<String> ok = DialogEvent.ok(value);
        
        @Test
        public void hasOKResult() {
            assertEquals(DialogResult.OK, ok.result());
        }
        @Test
        public void hasSpecifiedValue() {
            assertEquals(value, ok.value());
        }
        @Test
        public void throwsWhenValueNull() {
            assertThrows(IllegalArgumentException.class, () -> DialogEvent.ok(null));
        }
    }

    public static class CancelEvent {
        private final DialogEvent<String> cancel = DialogEvent.cancel();

        @Test
        public void hasCancelResult() {
            assertEquals(DialogResult.CANCEL, cancel.result());
        }
        @Test
        public void hasNullValue() {
            assertNull(cancel.value());
        }
    }
}
