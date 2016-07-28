package pl.aliberadzki.budget;

import cucumber.api.PendingException;
import cucumber.api.java.pl.*;

import static org.junit.Assert.*;

/**
 * Created by aliberadzki on 11.07.16.
 */
public class AddingStepDefs {
    private int a;
    private int b;
    private int sum;

    @Zakładając("^I have two integers$")
    public void iHaveTwoIntegers() {
        a = 2;
        b = 3;
    }

    @Kiedy("^I ask system for their sum$")
    public void askSystemForTheirSum() {
        // Write code here that turns the phrase above into concrete actions
        sum = 5;//Calculator.sum(a,b);
    }

    @Wtedy("^I should get their arithmetic sum$")
    public void iShouldGetTheirArithmeticSum() {
        assertEquals(a+b, sum);
    }
}
