package pl.aliberadzki.budget;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by aliberadzki on 2016-08-01.
 */
public class SQLiteDatabaseTest {
    Datasource datasource;
    @Test
    public void testinitiatingDB() throws Exception {
        datasource = SQLiteDatabase.instance();
        assertNotNull(datasource);
    }

    @Test
    public void testAddBudget() throws Exception {
        Budget b = new BaseBudget(1, "Budżet Adama");
        datasource.addBudget(b);

        Budget b2 = datasource.getBudget(1);

        assertEquals(b,b2);
    }

    @Test
    public void testAddCategory() throws Exception {
        CashFlowCategory cfc = new CashFlowCategoryImpl(1, "JEDZENIE", new ExpenseGroup(), 1000.0, new DateRangeImpl("20160801"));
        datasource.addCategory(1, cfc);

        CashFlowCategory cfc2 = datasource.getCategory(1, 1);

        assertEquals(cfc, cfc2);
    }
}