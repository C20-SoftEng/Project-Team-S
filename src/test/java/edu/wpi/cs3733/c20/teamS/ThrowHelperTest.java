package edu.wpi.cs3733.c20.teamS;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ThrowHelperTest {

    @Test
    public void illegalArgumentExceptionTest() throws IllegalArgumentException{
       // assertThrows(IllegalArgumentException.class, () -> ThrowHelper.illegalNull("IDK what Im doing"));
        throw new IllegalArgumentException("Test");
    }

    @Test
    public void illegalStateExceptionTest(){
        assertThrows(IllegalStateException.class, () -> ThrowHelper.iteratorOffTheEnd());

    }

}
