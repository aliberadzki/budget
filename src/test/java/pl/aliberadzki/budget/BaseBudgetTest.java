package pl.aliberadzki.budget;

import org.junit.After;
import org.junit.Test;


import static org.junit.Assert.*;

/**
 * Created by aliberadzki on 2016-07-14.
 */
public class BaseBudgetTest {

    @After
    public void tearDown() throws Exception {
        DummyDatabase.instance().clear();

    }

    @Test
    public void testCreatingEmptyBudget() throws Exception {
        int budgetId = 1;
        String budgetName = "Budżet Adama";
        Transaction t = new CreateNewBudgetTransaction(budgetId, budgetName);
        t.execute();

        Budget b = DummyDatabase.instance().getBudget(budgetId);

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
        int budgetId = 1;
        String budgetName = "Budżet Adama";
        Transaction t = new CreateNewBudgetTransaction(budgetId, budgetName);
        t.execute();

        Budget b = DummyDatabase.instance().getBudget(budgetId);

        int expenseCategoryId = 11;
        String expenseCategoryName = "Jedzenie";
        Double plannedExpenses = 123.45;
        DateRange expenseCategoryStartingFromDate = new DateRangeImpl("201607");

        t = new AddExpenseCategoryTransaction(budgetId, expenseCategoryId, expenseCategoryName, plannedExpenses, expenseCategoryStartingFromDate);
        t.execute();

        assertEquals(  0.00, b.plannedMonthlyExpenses(new DateRangeImpl("201606")), 0.01);
        assertEquals(123.45, b.plannedMonthlyExpenses(new DateRangeImpl("201607")), 0.01);
        assertEquals(123.45, b.plannedMonthlyExpenses(new DateRangeImpl("201608")), 0.01);
    }

    @Test
    public void testAddingTwoPeriodsToSingleExpenseCategory() throws Exception {
        int budgetId = 1;
        String budgetName = "Budżet Adama";
        Transaction t = new CreateNewBudgetTransaction(budgetId, budgetName);
        t.execute();

        Budget b = DummyDatabase.instance().getBudget(budgetId);

        int expenseCategoryId = 11;
        String expenseCategoryName = "Jedzenie";
        Double plannedExpenses = 123.45;
        DateRange expenseCategoryStartingFromDate = new DateRangeImpl("201607");

        t = new AddExpenseCategoryTransaction(budgetId, expenseCategoryId, expenseCategoryName, plannedExpenses, expenseCategoryStartingFromDate);
        t.execute();

        t = new SetNewExpectedAmountFromDateTransaction(budgetId, expenseCategoryId, new DateRangeImpl("201609"), 234.56);
        t.execute();

        //cfc.setNewExpectedAmountFrom(new DateRangeImpl("201609"), 234.56);

        assertEquals(  0.00, b.plannedMonthlyExpenses(new DateRangeImpl("201606")), 0.01);
        assertEquals(123.45, b.plannedMonthlyExpenses(new DateRangeImpl("201607")), 0.01);
        assertEquals(123.45, b.plannedMonthlyExpenses(new DateRangeImpl("201608")), 0.01);
        assertEquals(234.56, b.plannedMonthlyExpenses(new DateRangeImpl("201609")), 0.01);
        assertEquals(234.56, b.plannedMonthlyExpenses(new DateRangeImpl("201610")), 0.01);
    }

    @Test
    public void testAddingSingleExpenseCategoryWithOneExceptionalMonth() throws Exception {
        int budgetId = 1;
        String budgetName = "Budżet Adama";
        Transaction t = new CreateNewBudgetTransaction(budgetId, budgetName);
        t.execute();

        Budget b = DummyDatabase.instance().getBudget(budgetId);

        int expenseCategoryId = 11;
        String expenseCategoryName = "Jedzenie";
        Double plannedExpenses = 123.45;
        DateRange expenseCategoryStartingFromDate = new DateRangeImpl("201607");

        t = new AddExpenseCategoryTransaction(budgetId, expenseCategoryId, expenseCategoryName, plannedExpenses, expenseCategoryStartingFromDate);
        t.execute();

        t = new SetNewExpectedAmountForDateTransaction(budgetId, expenseCategoryId, new DateRangeImpl("201609"), 234.56);
        t.execute();

        assertEquals(  0.00, b.plannedMonthlyExpenses(new DateRangeImpl("201606")), 0.01);
        assertEquals(123.45, b.plannedMonthlyExpenses(new DateRangeImpl("201607")), 0.01);
        assertEquals(123.45, b.plannedMonthlyExpenses(new DateRangeImpl("201608")), 0.01);
        assertEquals(234.56, b.plannedMonthlyExpenses(new DateRangeImpl("201609")), 0.01);
        assertEquals(123.45, b.plannedMonthlyExpenses(new DateRangeImpl("201610")), 0.01);
    }

    @Test
    public void testAddingTwoPeriodsToSingleExpenseCategoryWithOneExceptionalMonth() throws Exception {
        int budgetId = 1;
        String budgetName = "Budżet Adama";
        Transaction t = new CreateNewBudgetTransaction(budgetId, budgetName);
        t.execute();

        Budget b = DummyDatabase.instance().getBudget(budgetId);

        int expenseCategoryId = 11;
        String expenseCategoryName = "Jedzenie";
        Double plannedExpenses = 123.45;
        DateRange expenseCategoryStartingFromDate = new DateRangeImpl("201607");

        t = new AddExpenseCategoryTransaction(budgetId, expenseCategoryId, expenseCategoryName, plannedExpenses, expenseCategoryStartingFromDate);
        t.execute();

        t = new SetNewExpectedAmountFromDateTransaction(budgetId, expenseCategoryId, new DateRangeImpl("201609"), 234.56);
        t.execute();

        t = new SetNewExpectedAmountForDateTransaction(budgetId, expenseCategoryId, new DateRangeImpl("201610"), 345.67);
        t.execute();

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
        int budgetId = 1;
        String budgetName = "Budżet Adama";
        Transaction t = new CreateNewBudgetTransaction(budgetId, budgetName);
        t.execute();

        Budget b = DummyDatabase.instance().getBudget(budgetId);

        int expenseCategoryId = 11;
        String expenseCategoryName = "Jedzenie";
        Double plannedExpenses = 123.45;
        DateRange expenseCategoryStartingFromDate = new DateRangeImpl("201607");

        t = new AddExpenseCategoryTransaction(budgetId, expenseCategoryId, expenseCategoryName, plannedExpenses, expenseCategoryStartingFromDate);
        t.execute();

        t = new SetNewExpectedAmountFromDateTransaction(budgetId, expenseCategoryId, new DateRangeImpl("201609"), 234.56);
        t.execute();

        t = new SetNewExpectedAmountFromDateTransaction(budgetId, expenseCategoryId, new DateRangeImpl("201612"), 555.66);
        t.execute();

        assertEquals(0.00, b.plannedMonthlyExpenses(new DateRangeImpl("201606")), 0.01);
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
        int budgetId = 1;
        String budgetName = "Budżet Adama";
        Transaction t = new CreateNewBudgetTransaction(budgetId, budgetName);
        t.execute();

        Budget b = DummyDatabase.instance().getBudget(budgetId);


        int expenseCategoryId = 11;
        String expenseCategoryName = "Jedzenie";
        Double plannedExpenses = null;
        DateRange expenseCategoryStartingFromDate = new DateRangeImpl("201607");

        t = new AddExpenseCategoryTransaction(budgetId, expenseCategoryId, expenseCategoryName, plannedExpenses, expenseCategoryStartingFromDate);
        t.execute();

        int firstsubcategoryId = 22;
        int secondsubcategoryId = 33;

        t = new AddExpenseSubcategoryTransaction(budgetId, firstsubcategoryId, expenseCategoryId, "Jedzenie na mieście", 1000.00, expenseCategoryStartingFromDate);
        t.execute();

        t = new AddExpenseSubcategoryTransaction(budgetId, secondsubcategoryId, expenseCategoryId, "Jedzenie w domu", 1000.00, expenseCategoryStartingFromDate);
        t.execute();

        assertEquals(2000.0, b.plannedMonthlyExpenses(new DateRangeImpl("201607")), 0.001);
    }

    @Test
    public void testAddingExpenseCategoryWithSubcategoriesWithSubcategories() throws Exception {
        int budgetId = 1;
        String budgetName = "Budżet Adama";
        Transaction t = new CreateNewBudgetTransaction(budgetId, budgetName);
        t.execute();

        Budget b = DummyDatabase.instance().getBudget(budgetId);

        int expenseCategoryId = 1;
        DateRange expenseCategoryStartingFromDate = new DateRangeImpl("201607");

        t = new AddExpenseCategoryTransaction(budgetId, expenseCategoryId, "Jedzenie", null, expenseCategoryStartingFromDate);
        t.execute();

        t = new AddExpenseSubcategoryTransaction(budgetId, 12, expenseCategoryId, "Jedzenie w domu", null, expenseCategoryStartingFromDate);
        t.execute();

        t = new AddExpenseSubcategoryTransaction(budgetId, 121, 12, "biedronka", 1000.00, expenseCategoryStartingFromDate);
        t.execute();

        t = new AddExpenseSubcategoryTransaction(budgetId, 122, 12, "delikatesy", 1000.00, expenseCategoryStartingFromDate);
        t.execute();

        t = new AddExpenseSubcategoryTransaction(budgetId, 11, expenseCategoryId, "Jedzenie na mieście", null, expenseCategoryStartingFromDate);
        t.execute();

        t = new AddExpenseSubcategoryTransaction(budgetId, 111, 11, "knajpy", 1000.00, expenseCategoryStartingFromDate);
        t.execute();

        t = new AddExpenseSubcategoryTransaction(budgetId, 112, 11, "pizza na dowóz", 1000.00, expenseCategoryStartingFromDate);
        t.execute();

        assertEquals(4000.0, b.plannedMonthlyExpenses(new DateRangeImpl("201607")), 0.001);
    }

    @Test(expected = Exception.class)
    public void testAddingSubcategoriesWithConflictingIds() throws Exception {
        int budgetId = 1;
        String budgetName = "Budżet Adama";
        Transaction t = new CreateNewBudgetTransaction(budgetId, budgetName);
        t.execute();

        Budget b = DummyDatabase.instance().getBudget(budgetId);

        int expenseCategoryId = 1;
        DateRange expenseCategoryStartingFromDate = new DateRangeImpl("201607");

        t = new AddExpenseCategoryTransaction(budgetId, expenseCategoryId, "Jedzenie", null, expenseCategoryStartingFromDate);
        t.execute();

        t = new AddExpenseSubcategoryTransaction(budgetId, 12, expenseCategoryId, "Jedzenie w domu", null, expenseCategoryStartingFromDate);
        t.execute();

        t = new AddExpenseSubcategoryTransaction(budgetId, 121, 12, "biedronka", 1000.00, expenseCategoryStartingFromDate);
        t.execute();

        t = new AddExpenseSubcategoryTransaction(budgetId, 122, 12, "delikatesy", 1000.00, expenseCategoryStartingFromDate);
        t.execute();

        t = new AddExpenseSubcategoryTransaction(budgetId, 11, expenseCategoryId, "Jedzenie na mieście", null, expenseCategoryStartingFromDate);
        t.execute();

        t = new AddExpenseSubcategoryTransaction(budgetId, 111, 11, "knajpy", 1000.00, expenseCategoryStartingFromDate);
        t.execute();

        t = new AddExpenseSubcategoryTransaction(budgetId, 122, 11, "pizza na dowóz", 1000.00, expenseCategoryStartingFromDate);//conflicting id
        t.execute();//exception expected
    }

    @Test(expected = Exception.class)
    public void testAddingExpenseCategoryWithExistingSubcategoryOnThisLevel() throws Exception {
        int budgetId = 1;
        String budgetName = "Budżet Adama";
        Transaction t = new CreateNewBudgetTransaction(budgetId, budgetName);
        t.execute();

        Budget b = DummyDatabase.instance().getBudget(budgetId);

        int expenseCategoryId = 1;
        DateRange expenseCategoryStartingFromDate = new DateRangeImpl("201607");

        t = new AddExpenseCategoryTransaction(budgetId, expenseCategoryId, "Jedzenie", null, expenseCategoryStartingFromDate);
        t.execute();

        t = new AddExpenseSubcategoryTransaction(budgetId, 11, expenseCategoryId, "Jedzenie w domu", null, expenseCategoryStartingFromDate);
        t.execute();

        t = new AddExpenseSubcategoryTransaction(budgetId, 12, expenseCategoryId, "Jedzenie w domu", null, expenseCategoryStartingFromDate);
        t.execute();// the same name on the same level, exception
    }

    @Test
    public void testAddingExpenseCategoryWithExistingSubcategoryOnAnotherLevel() throws Exception {
        int budgetId = 1;
        String budgetName = "Budżet Adama";
        Transaction t = new CreateNewBudgetTransaction(budgetId, budgetName);
        t.execute();

        Budget b = DummyDatabase.instance().getBudget(budgetId);


        DateRange expenseCategoryStartingFromDate = new DateRangeImpl("201607");

        t = new AddExpenseCategoryTransaction(budgetId, 1, "Ja", null, expenseCategoryStartingFromDate);
        t.execute();

        t = new AddExpenseSubcategoryTransaction(budgetId, 11, 1, "Jedzenie na mieście", 1200.00, expenseCategoryStartingFromDate);
        t.execute();

        t = new AddExpenseSubcategoryTransaction(budgetId, 12, 1, "Jedzenie w domu", 1000.00, expenseCategoryStartingFromDate);
        t.execute();

        t = new AddExpenseCategoryTransaction(budgetId, 2, "Rodzice", null, expenseCategoryStartingFromDate);
        t.execute();

        t = new AddExpenseSubcategoryTransaction(budgetId, 21, 2, "Jedzenie na mieście", 1200.00, expenseCategoryStartingFromDate);
        t.execute();

        t = new AddExpenseSubcategoryTransaction(budgetId, 22, 2, "Jedzenie w domu", 1000.00, expenseCategoryStartingFromDate);
        t.execute();

        assertEquals(4400.0, b.plannedMonthlyExpenses(new DateRangeImpl("201607")), 0.001);
    }

    @Test(expected = Exception.class)
    public void testAddingSubcategoryToNonExistentExpenseCategory() throws Exception {
        int budgetId = 1;
        String budgetName = "Budżet Adama";
        Transaction t = new CreateNewBudgetTransaction(budgetId, budgetName);
        t.execute();

        Budget b = DummyDatabase.instance().getBudget(budgetId);

        DateRange expenseCategoryStartingFromDate = new DateRangeImpl("201607");

        t = new AddExpenseCategoryTransaction(budgetId, 1, "Ja", null, expenseCategoryStartingFromDate);
        t.execute();

        t = new AddExpenseSubcategoryTransaction(budgetId, 11, 1111, "Jedzenie na mieście", 1200.00, expenseCategoryStartingFromDate);
        t.execute();// exception expected

    }

    @Test
    public void testAddingExpenseCategoryAndExpenses() throws Exception {
        int budgetId = 1;
        String budgetName = "Budżet Adama";
        Transaction t = new CreateNewBudgetTransaction(budgetId, budgetName);
        t.execute();

        Budget b = DummyDatabase.instance().getBudget(budgetId);

        t = new AddExpenseCategoryTransaction(budgetId, 1, "Jedzenie", 200.00, new DateRangeImpl("201607"));
        t.execute();

        t = new AddExpenseTransaction(1, budgetId, 1, 100.0, new DateRangeImpl("20160714"), "Parówki");
        t.execute();

        assertEquals(0.5, b.expensesSpentPercentage(new DateRangeImpl("201607")), 0.001);
    }

    @Test(expected = Exception.class)
    public void testAddingExpenseExceedingPlannedAmount() throws Exception {
        int budgetId = 1;
        String budgetName = "Budżet Adama";
        Transaction t = new CreateNewBudgetTransaction(budgetId, budgetName);
        t.execute();

        Budget b = DummyDatabase.instance().getBudget(budgetId);

        t = new AddExpenseCategoryTransaction(budgetId, 1, "Jedzenie", 200.00, new DateRangeImpl("201607"));
        t.execute();

        t = new AddExpenseTransaction(1, budgetId, 1, 300.0, new DateRangeImpl("20160714"), "Parówki");
        t.execute();

        assertEquals(1.5, b.expensesSpentPercentage(new DateRangeImpl("201607")), 0.001);
    }

    @Test
    public void testForceAddingExpenseExceedingPlannedAmount() throws Exception {
        int budgetId = 1;
        String budgetName = "Budżet Adama";
        Transaction t = new CreateNewBudgetTransaction(budgetId, budgetName);
        t.execute();

        Budget b = DummyDatabase.instance().getBudget(budgetId);

        t = new AddExpenseCategoryTransaction(budgetId, 1, "Jedzenie", 200.00, new DateRangeImpl("201607"));
        t.execute();

        t = new AddForcefulExpenseTransaction(1, budgetId, 1, 300.0, new DateRangeImpl("20160714"), "Parówki");
        t.execute();

        assertEquals(1.5, b.expensesSpentPercentage(new DateRangeImpl("201607")), 0.001);
    }

    @Test(expected = Exception.class)
    public void testAddingExpenseToNonExistentCategory() throws Exception {
        int budgetId = 1;
        String budgetName = "Budżet Adama";
        Transaction t = new CreateNewBudgetTransaction(budgetId, budgetName);
        t.execute();

        Budget b = DummyDatabase.instance().getBudget(budgetId);

        t = new AddExpenseCategoryTransaction(budgetId, 1, "Jedzenie", 200.00, new DateRangeImpl("201607"));
        t.execute();

        t = new AddExpenseTransaction(1, budgetId, 2, 100.0, new DateRangeImpl("20160714"), "Parówki");
        t.execute();

        assertEquals(0.5, b.expensesSpentPercentage(new DateRangeImpl("201607")), 0.001);
    }

    @Test
    public void testAddingOnceAYearExpense() throws Exception {
        int budgetId = 1;
        String budgetName = "Budżet Adama";
        Transaction t = new CreateNewBudgetTransaction(budgetId, budgetName);
        t.execute();

        Budget b = DummyDatabase.instance().getBudget(budgetId);

        t = new AddExpenseCategoryTransaction(budgetId, 1, "Samochód", 200.00, new DateRangeImpl("201607"));
        t.execute();

        t = new SetNewExpectedAmountCyclicForTransaction(budgetId, 1, 1600.00, DateRange.YEARLY, new DateRangeImpl("20160726"), "Ubezpieczenie AC/OC");
        t.execute();

        assertEquals(   0.00, b.plannedMonthlyExpenses(new DateRangeImpl("201507")), 0.01);
        assertEquals(1600.00, b.plannedMonthlyExpenses(new DateRangeImpl("201607")), 0.01);
        assertEquals( 200.00, b.plannedMonthlyExpenses(new DateRangeImpl("201608")), 0.01);
        assertEquals(1600.00, b.plannedMonthlyExpenses(new DateRangeImpl("201707")), 0.01);
        assertEquals(1600.00, b.plannedMonthlyExpenses(new DateRangeImpl("201807")), 0.01);
        assertEquals( 200.00, b.plannedMonthlyExpenses(new DateRangeImpl("201901")), 0.01);

    }
}