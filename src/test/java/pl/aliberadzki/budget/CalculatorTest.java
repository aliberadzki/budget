package pl.aliberadzki.budget;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by aliberadzki on 11.07.16.
 */
public class CalculatorTest {

    @Test
    public void testSum() throws Exception {
        assertEquals(4, Calculator.sum(1,3));

    }
}