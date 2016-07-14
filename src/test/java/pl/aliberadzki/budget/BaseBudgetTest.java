package pl.aliberadzki.budget;

import org.junit.Test;


import static org.junit.Assert.*;

/**
 * Created by aliberadzki on 2016-07-14.
 */
public class BaseBudgetTest {

    @Test
    public void testCreatingEmptyBudget() throws Exception {
        BaseBudget b = new BaseBudget();
        assertNotNull(b);
        assertEquals(0.0, b.monthlyExpenses("201607"), 0.01);
        assertEquals(0.0, b.monthlyInvestments("201607"), 0.01);
        assertEquals(0.0, b.monthlyIncomes("201607"), 0.01);
        assertEquals(0.0, b.plannedMonthlyExpenses("201607"), 0.01);
        assertEquals(0.0, b.plannedMonthlyInvestments("201607"), 0.01);
        assertEquals(0.0, b.plannedMonthlyIncomes("201607"), 0.01);
    }

    @Test
    public void testAddingSingleExpenseCategory() throws Exception {
        BaseBudget b = new BaseBudget();
        String catName = "Jedzenie";
        CashFlowCategory cfc = new ExpenseCategory(catName, 123.45, "201607");
        b.addExpenseCategory(cfc);

        assertEquals(  0.00, b.plannedMonthlyExpenses("201606"), 0.01);
        assertEquals(123.45, b.plannedMonthlyExpenses("201607"), 0.01);
        assertEquals(123.45, b.plannedMonthlyExpenses("201608"), 0.01);
    }

    @Test
    public void testAddingTwoPeriodsToSingleExpenseCategory() throws Exception {
        BaseBudget b = new BaseBudget();
        String catName = "Jedzenie";
        CashFlowCategory cfc = new ExpenseCategory(catName, 123.45, "201607");
        b.addExpenseCategory(cfc);
        cfc.setNewExpectedAmountFrom("201609", 234.56);

        assertEquals(  0.00, b.plannedMonthlyExpenses("201606"), 0.01);
        assertEquals(123.45, b.plannedMonthlyExpenses("201607"), 0.01);
        assertEquals(123.45, b.plannedMonthlyExpenses("201608"), 0.01);
        assertEquals(234.56, b.plannedMonthlyExpenses("201609"), 0.01);
        assertEquals(234.56, b.plannedMonthlyExpenses("201610"), 0.01);
    }

    @Test
    public void testAddingSingleExpenseWithOneExceptionalMonth() throws Exception {

        BaseBudget b = new BaseBudget();
        String catName = "Jedzenie";
        CashFlowCategory cfc = new ExpenseCategory(catName, 123.45, "201607");
        b.addExpenseCategory(cfc);
        cfc.setNewExpectedAmountFor("201609", 234.56);

        assertEquals(  0.00, b.plannedMonthlyExpenses("201606"), 0.01);
        assertEquals(123.45, b.plannedMonthlyExpenses("201607"), 0.01);
        assertEquals(123.45, b.plannedMonthlyExpenses("201608"), 0.01);
        assertEquals(234.56, b.plannedMonthlyExpenses("201609"), 0.01);
        assertEquals(123.45, b.plannedMonthlyExpenses("201610"), 0.01);
    }

    @Test
    public void testAddingTwoPeriodsToSingleExpenseCategoryWithOneExceptionalMonth() throws Exception {
        BaseBudget b = new BaseBudget();
        String catName = "Jedzenie";
        CashFlowCategory cfc = new ExpenseCategory(catName, 123.45, "201607");
        b.addExpenseCategory(cfc);
        cfc.setNewExpectedAmountFrom("201609", 234.56);
        cfc.setNewExpectedAmountFor("201610", 345.67);

        assertEquals(  0.00, b.plannedMonthlyExpenses("201606"), 0.01);
        assertEquals(123.45, b.plannedMonthlyExpenses("201607"), 0.01);
        assertEquals(123.45, b.plannedMonthlyExpenses("201608"), 0.01);
        assertEquals(234.56, b.plannedMonthlyExpenses("201609"), 0.01);
        assertEquals(345.67, b.plannedMonthlyExpenses("201610"), 0.01);
        assertEquals(234.56, b.plannedMonthlyExpenses("201611"), 0.01);
        assertEquals(234.56, b.plannedMonthlyExpenses("201612"), 0.01);
        assertEquals(234.56, b.plannedMonthlyExpenses("201701"), 0.01);
    }

    @Test
    public void testAddingThreePeriodsToSingleExpenseCategory() throws Exception {
        BaseBudget b = new BaseBudget();
        String catName = "Jedzenie";
        CashFlowCategory cfc = new ExpenseCategory(catName, 123.45, "201607");
        b.addExpenseCategory(cfc);
        cfc.setNewExpectedAmountFrom("201609", 234.56);
        cfc.setNewExpectedAmountFrom("201612", 555.66);

        assertEquals(  0.00, b.plannedMonthlyExpenses("201606"), 0.01);
        assertEquals(123.45, b.plannedMonthlyExpenses("201607"), 0.01);
        assertEquals(123.45, b.plannedMonthlyExpenses("201608"), 0.01);
        assertEquals(234.56, b.plannedMonthlyExpenses("201609"), 0.01);
        assertEquals(234.56, b.plannedMonthlyExpenses("201610"), 0.01);
        assertEquals(234.56, b.plannedMonthlyExpenses("201611"), 0.01);
        assertEquals(555.66, b.plannedMonthlyExpenses("201612"), 0.01);
        assertEquals(555.66, b.plannedMonthlyExpenses("201701"), 0.01);
        assertEquals(555.66, b.plannedMonthlyExpenses("201702"), 0.01);
    }

    @Test
    public void testAddingExpenseCategoryAndExpenses() throws Exception {
        BaseBudget b = new BaseBudget();
        String catName = "Jedzenie";
        CashFlowCategory cfc = new ExpenseCategory(catName, 200.00, "201607");
        b.addExpenseCategory(cfc);
        b.addExpense(catName, 100.0, "20160714", "Parówki");

        assertEquals(0.5, b.expensesSpentPercentage("201607"), 0.001);

    }
}