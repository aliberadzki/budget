package pl.aliberadzki.budget;

import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by aliberadzki on 2016-08-01.
 */
public class SQLiteDatabaseTest {
    Datasource datasource = SQLiteDatabase.instance();

    @Test
    public void testInitiatingDB() throws Exception {
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
        Budget b = new BaseBudget(1, "Budżet Adama");
        datasource.addBudget(b);
        CashFlowCategory cfc = new CashFlowCategoryImpl(1, "JEDZENIE", new ExpenseGroup(), 1000.0, new DateRangeImpl("20160801"));
        datasource.addCategory(1, cfc);

        CashFlowCategory cfc2 = datasource.getCategory(1, 1);

        assertEquals(cfc.getId(), cfc2.getId());
        assertEquals(cfc.getName(), cfc2.getName());
        assertEquals(cfc.getBasicAmount(), cfc2.getBasicAmount());
        assertEquals(cfc.getGroup().getCode(), cfc2.getGroup().getCode());
    }

    @After
    public void tearDown() throws Exception {
        datasource.clear();
    }
}