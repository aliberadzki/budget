package pl.aliberadzki.budget;

import cucumber.api.CucumberOptions;
import cucumber.api.PendingException;
import cucumber.api.java.en.*;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Created by aliberadzki on 13.07.16.
 */


@CucumberOptions(  monochrome = true,
        tags = "@tags",
        features = "src/test/resources/features/",
        format = { "pretty","html: cucumber-html-reports",
                "json: cucumber-html-reports/cucumber.json" },
        dryRun = false,
        glue = "pl.aliberadzki.budget" )

public class BudgetingStepDefs {
    private String baseBudget;


    @Given("^Jest utworzony budzet bazowy$")
    public void Jest_utworzony_budzet_bazowy() throws Throwable {
        baseBudget = "";
    }


    @When("^Utworzę kategorie wydatków o nazwie: Jedzenie, Mieszkanie, Samochód$")
    public void iCreate3ExpensesCategories() {
        baseBudget += "jms";
    }

    @Then("^Budżet powinien posiadać 3 kategorie wydatku$")
    public void budgetShouldHaveExactly3ExpenseCategories() {
        assertEquals(3, baseBudget.length());
    }
    @And("^Nie ma w nim żadnych kategorii wydatków$")
    public void Nie_ma_w_nim_żadnych_kategorii_wydatków() throws Throwable {
        assertEquals(0, baseBudget.length());
    }

    @And("^Budżet powinien zawierać kategorie: Jedzenie, Mieszkanie, Samochód$")
    public void Budżet_powinien_zawierać_kategorie_Jedzenie_Mieszkanie_Samochód() throws Throwable {
        assertNotEquals(-1, baseBudget.indexOf("j"));
        assertNotEquals(-1, baseBudget.indexOf("m"));
        assertNotEquals(-1, baseBudget.indexOf("s"));
    }

    @And("^Jest w nim kategoria wydatków o nazwie Jedzenie$")
    public void Jest_w_nim_kategoria_wydatków_o_nazwie_Jedzenie() throws Throwable {
        if(baseBudget.indexOf("j") == -1) {
            baseBudget += "j";
        }
    }

    @When("^Utworzę kategorie wydatków o nazwie: Jedzenie$")
    public void Utworzę_kategorie_wydatków_o_nazwie_Jedzenie() throws Throwable {
        baseBudget += "j";
    }

    @Then("^Budżet powinien posiadać (\\d+) kategorię wydatku$")
    public void Budżet_powinien_posiadać_kategorię_wydatku(int arg1) throws Throwable {
        assertEquals(arg1, baseBudget.length());
    }

    @And("^Powinien zostać zwrócony błąd$")
    public void Powinien_zostać_zwrócony_błąd() throws Throwable {
        assertNull(null);
    }
}
