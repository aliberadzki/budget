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
        assertEquals(0.0, b.monthlyExpenses(new DateRangeImpl("201607")), 0.01);
        assertEquals(0.0, b.monthlyInvestments(new DateRangeImpl("201607")), 0.01);
        assertEquals(0.0, b.monthlyIncomes(new DateRangeImpl("201607")), 0.01);
        assertEquals(0.0, b.plannedMonthlyExpenses(new DateRangeImpl("201607")), 0.01);
        assertEquals(0.0, b.plannedMonthlyInvestments(new DateRangeImpl("201607")), 0.01);
        assertEquals(0.0, b.plannedMonthlyIncomes(new DateRangeImpl("201607")), 0.01);
    }

    @Test
    public void testAddingSingleExpenseCategory() throws Exception {
        BaseBudget b = new BaseBudget();
        String catName = "Jedzenie";
        CashFlowCategory cfc = new ExpenseCategory("1", catName, 123.45, new DateRangeImpl("201607"));
        b.addExpenseCategory(cfc);

        assertEquals(  0.00, b.plannedMonthlyExpenses(new DateRangeImpl("201606")), 0.01);
        assertEquals(123.45, b.plannedMonthlyExpenses(new DateRangeImpl("201607")), 0.01);
        assertEquals(123.45, b.plannedMonthlyExpenses(new DateRangeImpl("201608")), 0.01);
    }

    @Test
    public void testAddingTwoPeriodsToSingleExpenseCategory() throws Exception {
        BaseBudget b = new BaseBudget();
        String catName = "Jedzenie";
        CashFlowCategory cfc = new ExpenseCategory("1", catName, 123.45, new DateRangeImpl("201607"));
        b.addExpenseCategory(cfc);
        cfc.setNewExpectedAmountFrom(new DateRangeImpl("201609"), 234.56);

        assertEquals(  0.00, b.plannedMonthlyExpenses(new DateRangeImpl("201606")), 0.01);
        assertEquals(123.45, b.plannedMonthlyExpenses(new DateRangeImpl("201607")), 0.01);
        assertEquals(123.45, b.plannedMonthlyExpenses(new DateRangeImpl("201608")), 0.01);
        assertEquals(234.56, b.plannedMonthlyExpenses(new DateRangeImpl("201609")), 0.01);
        assertEquals(234.56, b.plannedMonthlyExpenses(new DateRangeImpl("201610")), 0.01);
    }

    @Test
    public void testAddingSingleExpenseCategoryWithOneExceptionalMonth() throws Exception {

        BaseBudget b = new BaseBudget();
        String catName = "Jedzenie";
        CashFlowCategory cfc = new ExpenseCategory("1", catName, 123.45, new DateRangeImpl("201607"));
        b.addExpenseCategory(cfc);
        cfc.setNewExpectedAmountFor(new DateRangeImpl("201609"), 234.56);

        assertEquals(  0.00, b.plannedMonthlyExpenses(new DateRangeImpl("201606")), 0.01);
        assertEquals(123.45, b.plannedMonthlyExpenses(new DateRangeImpl("201607")), 0.01);
        assertEquals(123.45, b.plannedMonthlyExpenses(new DateRangeImpl("201608")), 0.01);
        assertEquals(234.56, b.plannedMonthlyExpenses(new DateRangeImpl("201609")), 0.01);
        assertEquals(123.45, b.plannedMonthlyExpenses(new DateRangeImpl("201610")), 0.01);
    }

    @Test
    public void testAddingTwoPeriodsToSingleExpenseCategoryWithOneExceptionalMonth() throws Exception {
        BaseBudget b = new BaseBudget();
        String catName = "Jedzenie";
        CashFlowCategory cfc = new ExpenseCategory("1", catName, 123.45, new DateRangeImpl("201607"));
        b.addExpenseCategory(cfc);
        cfc.setNewExpectedAmountFrom(new DateRangeImpl("201609"), 234.56);
        cfc.setNewExpectedAmountFor(new DateRangeImpl("201610"), 345.67);

        assertEquals(  0.00, b.plannedMonthlyExpenses(new DateRangeImpl("201606")), 0.01);
        assertEquals(123.45, b.plannedMonthlyExpenses(new DateRangeImpl("201607")), 0.01);
        assertEquals(123.45, b.plannedMonthlyExpenses(new DateRangeImpl("201608")), 0.01);
        assertEquals(234.56, b.plannedMonthlyExpenses(new DateRangeImpl("201609")), 0.01);
        assertEquals(345.67, b.plannedMonthlyExpenses(new DateRangeImpl("201610")), 0.01);
        assertEquals(234.56, b.plannedMonthlyExpenses(new DateRangeImpl("201611")), 0.01);
        assertEquals(234.56, b.plannedMonthlyExpenses(new DateRangeImpl("201612")), 0.01);
        assertEquals(234.56, b.plannedMonthlyExpenses(new DateRangeImpl("201701")), 0.01);
    }

    @Test
    public void testAddingThreePeriodsToSingleExpenseCategory() throws Exception {
        BaseBudget b = new BaseBudget();
        String catName = "Jedzenie";
        CashFlowCategory cfc = new ExpenseCategory("1", catName, 123.45, new DateRangeImpl("201607"));
        b.addExpenseCategory(cfc);
        cfc.setNewExpectedAmountFrom(new DateRangeImpl("201609"), 234.56);
        cfc.setNewExpectedAmountFrom(new DateRangeImpl("201612"), 555.66);

        assertEquals(  0.00, b.plannedMonthlyExpenses(new DateRangeImpl("201606")), 0.01);
        assertEquals(123.45, b.plannedMonthlyExpenses(new DateRangeImpl("201607")), 0.01);
        assertEquals(123.45, b.plannedMonthlyExpenses(new DateRangeImpl("201608")), 0.01);
        assertEquals(234.56, b.plannedMonthlyExpenses(new DateRangeImpl("201609")), 0.01);
        assertEquals(234.56, b.plannedMonthlyExpenses(new DateRangeImpl("201610")), 0.01);
        assertEquals(234.56, b.plannedMonthlyExpenses(new DateRangeImpl("201611")), 0.01);
        assertEquals(555.66, b.plannedMonthlyExpenses(new DateRangeImpl("201612")), 0.01);
        assertEquals(555.66, b.plannedMonthlyExpenses(new DateRangeImpl("201701")), 0.01);
        assertEquals(555.66, b.plannedMonthlyExpenses(new DateRangeImpl("201702")), 0.01);
    }

    @Test
    public void testAddingExpenseCategoryWithSubcategories() throws Exception {
        BaseBudget b = new BaseBudget();
        CashFlowCategory master = new ExpenseCategory("1", "Jedzenie", null, new DateRangeImpl("201607"));
        CashFlowCategory slave1 = new ExpenseCategory("2", "Jedzenie w domu", 1000.0, new DateRangeImpl("201607"));
        CashFlowCategory slave2 = new ExpenseCategory("3", "Jedzenie na miescie", 1000.0, new DateRangeImpl("201607"));
        b.addExpenseCategory(master);
        b.addExpenseCategory(master.getId(), slave1);
        b.addExpenseCategory(master.getId(), slave2);

        assertEquals(2000.0, b.plannedMonthlyExpenses(new DateRangeImpl("201607")), 0.001);
    }

    @Test
    public void testAddingExpenseCategoryWithSubcategoriesWithSubcategories() throws Exception {
        BaseBudget b = new BaseBudget();
        CashFlowCategory master = new ExpenseCategory("1", "Jedzenie", null, new DateRangeImpl("201607"));
        CashFlowCategory slave1 = new ExpenseCategory("2", "Jedzenie w domu", null, new DateRangeImpl("201607"));
        CashFlowCategory slave11 = new ExpenseCategory("3", "Biedronka", 1000.0, new DateRangeImpl("201607"));
        CashFlowCategory slave12 = new ExpenseCategory("4", "Delikatesy", 1000.0, new DateRangeImpl("201607"));
        CashFlowCategory slave2 = new ExpenseCategory("5", "Jedzenie na miescie", null, new DateRangeImpl("201607"));
        CashFlowCategory slave21 = new ExpenseCategory("6", "Knajpy", 1000.0, new DateRangeImpl("201607"));
        CashFlowCategory slave22 = new ExpenseCategory("7", "Pizza na dowoz", 1000.0, new DateRangeImpl("201607"));
        b.addExpenseCategory(master);
        b.addExpenseCategory(master.getId(), slave1);
        b.addExpenseCategory(slave1.getId(), slave11);
        b.addExpenseCategory(slave1.getId(), slave12);
        b.addExpenseCategory(master.getId(), slave2);
        b.addExpenseCategory(slave2.getId(), slave21);
        b.addExpenseCategory(slave2.getId(), slave22);

        assertEquals(4000.0, b.plannedMonthlyExpenses(new DateRangeImpl("201607")), 0.001);
    }

    @Test(expected = Exception.class)
    public void testAddingSubcategoriesWithConflictingNames() throws Exception {
        BaseBudget b = new BaseBudget();
        CashFlowCategory master = new ExpenseCategory("1", "Jedzenie", null, new DateRangeImpl("201607"));
        CashFlowCategory slave1 = new ExpenseCategory("2", "Jedzenie w domu", null, new DateRangeImpl("201607"));
        CashFlowCategory slave11 = new ExpenseCategory("3", "Biedronka", 1000.0, new DateRangeImpl("201607"));
        CashFlowCategory slave12 = new ExpenseCategory("4", "Delikatesy", 1000.0, new DateRangeImpl("201607"));
        CashFlowCategory slave2 = new ExpenseCategory("5", "Jedzenie na miescie", null, new DateRangeImpl("201607"));
        CashFlowCategory slave21 = new ExpenseCategory("6", "Knajpy", 1000.0, new DateRangeImpl("201607"));
        CashFlowCategory slave22 = new ExpenseCategory("4", "Pizza na dowoz", 1000.0, new DateRangeImpl("201607")); //conflicting id!
        b.addExpenseCategory(master);
        b.addExpenseCategory(master.getId(), slave1);
        b.addExpenseCategory(slave1.getId(), slave11);
        b.addExpenseCategory(slave1.getId(), slave12);
        b.addExpenseCategory(master.getId(), slave2);
        b.addExpenseCategory(slave2.getId(), slave21);
        b.addExpenseCategory(slave2.getId(), slave22);//exception expected
    }

    @Test(expected = Exception.class)
    public void testAddingExpenseCategoryWithExistingSubcategoryOnThisLevel() throws Exception {
        BaseBudget b = new BaseBudget();
        CashFlowCategory master = new ExpenseCategory("1", "Jedzenie", null, new DateRangeImpl("201607"));
        CashFlowCategory slave1 = new ExpenseCategory("2", "Jedzenie w domu", 1000.0, new DateRangeImpl("201607"));
        CashFlowCategory slave2 = new ExpenseCategory("3", "Jedzenie w domu", 1200.0, new DateRangeImpl("201607"));
        b.addExpenseCategory(master);
        b.addExpenseCategory(master.getId(), slave1);
        b.addExpenseCategory(master.getId(), slave2); // the same name on the same level, exception
    }

    @Test
    public void testAddingExpenseCategoryWithExistingSubcategoryOnAnotherLevel() throws Exception {
        BaseBudget b = new BaseBudget();
        CashFlowCategory master1 = new ExpenseCategory("1", "Ja", null, new DateRangeImpl("201607"));
        CashFlowCategory slave11 = new ExpenseCategory("2", "Jedzenie w domu", 1000.0, new DateRangeImpl("201607"));
        CashFlowCategory slave12 = new ExpenseCategory("3", "Jedzenie na miescie", 1200.0, new DateRangeImpl("201607"));
        CashFlowCategory master2 = new ExpenseCategory("4", "Rodzice", null, new DateRangeImpl("201607"));
        CashFlowCategory slave21 = new ExpenseCategory("5", "Jedzenie w domu", 1000.0, new DateRangeImpl("201607"));
        CashFlowCategory slave22 = new ExpenseCategory("6", "Jedzenie na miescie", 1200.0, new DateRangeImpl("201607"));
        b.addExpenseCategory(master1);
        b.addExpenseCategory(master2);
        b.addExpenseCategory(master1.getId(), slave11);
        b.addExpenseCategory(master1.getId(), slave12);
        b.addExpenseCategory(master2.getId(), slave21);
        b.addExpenseCategory(master2.getId(), slave22);

        assertEquals(4400.0, b.plannedMonthlyExpenses(new DateRangeImpl("201607")), 0.001);
    }

    @Test(expected = Exception.class)
    public void testAddingSubcategoryToNonExistentExpenseCategory() throws Exception {
        BaseBudget b = new BaseBudget();
        CashFlowCategory master = new ExpenseCategory("1", "Jedzenie", null, new DateRangeImpl("201607"));
        CashFlowCategory slave1 = new ExpenseCategory("2", "Jedzenie w domu", 1000.0, new DateRangeImpl("201607"));

        b.addExpenseCategory(master.getId(), slave1); // exception expected
    }

    @Test
    public void testAddingExpenseCategoryAndExpenses() throws Exception {
        BaseBudget b = new BaseBudget();
        CashFlowCategory cfc = new ExpenseCategory("1", "Jedzenie", 200.00, new DateRangeImpl("201607"));
        b.addExpenseCategory(cfc);
        b.addExpense("1", 100.0, new DateRangeImpl("20160714"), "Parówki");

        assertEquals(0.5, b.expensesSpentPercentage(new DateRangeImpl("201607")), 0.001);
    }

    @Test(expected = Exception.class)
    public void testAddingExpenseExceedingPlannedAmount() throws Exception {
        BaseBudget b = new BaseBudget();
        String catName = "Jedzenie";
        CashFlowCategory cfc = new ExpenseCategory("1", catName, 200.00, new DateRangeImpl("201607"));
        b.addExpenseCategory(cfc);
        b.addExpense(catName, 300.0, new DateRangeImpl("20160714"), "Parówki");

        assertEquals(1.5, b.expensesSpentPercentage(new DateRangeImpl("201607")), 0.001);
    }

    @Test
    public void testForceAddingExpenseExceedingPlannedAmount() throws Exception {
        BaseBudget b = new BaseBudget();
        CashFlowCategory cfc = new ExpenseCategory("1", "Jedzenie", 200.00, new DateRangeImpl("201607"));
        b.addExpenseCategory(cfc);
        b.forceAddExpense("1", 300.0, new DateRangeImpl("20160714"), "Parówki");

        assertEquals(1.5, b.expensesSpentPercentage(new DateRangeImpl("201607")), 0.001);
    }

    @Test(expected = Exception.class)
    public void testAddingExpenseToNonExistentCategory() throws Exception {

        BaseBudget b = new BaseBudget();
        CashFlowCategory cfc = new ExpenseCategory("1", "Jedzenie", 200.00, new DateRangeImpl("201607"));
        b.addExpenseCategory(cfc);
        b.addExpense("2", 100.0, new DateRangeImpl("20160714"), "Parówki");

        assertEquals(0.5, b.expensesSpentPercentage(new DateRangeImpl("201607")), 0.001);
    }

    @Test
    public void testAddingOnceAYearExpense() throws Exception {
        BaseBudget b = new BaseBudget();
        CashFlowCategory cfc = new ExpenseCategory("1", "Samochód", 500.00);
        b.addExpenseCategory(cfc);
        b.addCyclicExpense("1", 1600.00, DateRange.YEARLY, new DateRangeImpl("201607"), "Ubezpieczenie AC/OC");

        assertEquals(1600.00, b.plannedMonthlyExpenses(new DateRangeImpl("201607")), 0.01);
        assertEquals(1600.00, b.plannedMonthlyExpenses(new DateRangeImpl("201707")), 0.01);

    }
}