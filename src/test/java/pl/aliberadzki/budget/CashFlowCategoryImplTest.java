package pl.aliberadzki.budget;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by aliberadzki on 2016-07-25.
 */
public class CashFlowCategoryImplTest {

    CashFlowCategoryGroup cfcg = new ExpenseGroup();

    @Test
    public void testGetExpectedBalanceAt() throws Exception {
        CashFlowCategory cfc = new CashFlowCategoryImpl(123, "nazwa", cfcg, 123.45);
        assertEquals(123.45, cfc.getExpectedBalanceAt(new DateRangeImpl()), 0.01);
    }

    @Test
    public void testSetNewExpectedAmountFrom() throws Exception {
        CashFlowCategory cfc = new CashFlowCategoryImpl(123, "nazwa", cfcg, 123.45);
        cfc.setNewExpectedAmountFrom(new DateRangeImpl("201807"), 345.67);
        assertEquals(123.45, cfc.getExpectedBalanceAt(new DateRangeImpl()), 0.01);
    }

    @Test
    public void testSetNewExpectedAmountFor() throws Exception {

    }

    @Test
    public void testGetName() throws Exception {

    }

    @Test
    public void testAddOperation() throws Exception {

    }

    @Test
    public void testForceAddOperation() throws Exception {

    }

    @Test
    public void testGetBalanceAt() throws Exception {

    }

    @Test
    public void testAddSubCategory() throws Exception {

    }

    @Test
    public void testGetId() throws Exception {
        Integer id = 123;
        CashFlowCategory cfc = new CashFlowCategoryImpl(id, "nazwa", cfcg, 21.0);
        assertEquals(id, cfc.getId());
    }
}