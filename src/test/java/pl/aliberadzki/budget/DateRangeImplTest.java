package pl.aliberadzki.budget;

import org.junit.Test;
import org.omg.CORBA.Object;

import static org.junit.Assert.*;

/**
 * Created by aliberadzki on 24.07.16.
 */
public class DateRangeImplTest {

    @Test
    public void testSimpleIncrement() throws Exception {
        DateRange dr = new DateRangeImpl("20170601");

        dr = dr.increment(DateRange.MONTHLY);
        assertEquals((new DateRangeImpl("20170701")).toString(), dr.toString());
        dr = dr.increment(DateRange.DAILY);
        assertEquals((new DateRangeImpl("20170702")).toString(), dr.toString());
        dr = dr.increment(DateRange.YEARLY);
        assertEquals((new DateRangeImpl("20180702")).toString(), dr.toString());
        dr = dr.increment(DateRange.WEEKLY);
        assertEquals((new DateRangeImpl("20180709")).toString(), dr.toString());
    }

    @Test
    public void testEqualsAndHashCode() throws Exception {
        DateRange dr = new DateRangeImpl("201607");
        DateRange dr2 = new DateRangeImpl("201707");

        dr = dr.increment(DateRange.YEARLY);

        assertEquals(dr2, dr);
        assertEquals(dr2.hashCode(), dr.hashCode());
    }

    @Test
    public void testEdgeIncrement() throws Exception {
        DateRange lastDayOfYear = new DateRangeImpl("20161231");
        DateRange almostLastDayOfFebruaryLeap = new DateRangeImpl("20160228");
        DateRange lastDayOfFebruaryNormal = new DateRangeImpl("20150228");
        DateRange lastDayOfAugust = new DateRangeImpl("20160831");
        DateRange lastDayOfAugust2 = new DateRangeImpl("20160831");
        DateRange lastDayOfSeptember = new DateRangeImpl("20160930");
        DateRange lastWeek = new DateRangeImpl("20160928");

        assertEquals((new DateRangeImpl("20170101")).toString(), lastDayOfYear.increment(DateRange.DAILY).toString());
        assertEquals((new DateRangeImpl("20160229")).toString(), almostLastDayOfFebruaryLeap.increment(DateRange.DAILY).toString());
        assertEquals((new DateRangeImpl("20150301")).toString(), lastDayOfFebruaryNormal.increment(DateRange.DAILY).toString());
        assertEquals((new DateRangeImpl("20160901")).toString(), lastDayOfAugust.increment(DateRange.DAILY).toString());
        assertEquals((new DateRangeImpl("20160930")).toString(), lastDayOfAugust2.increment(DateRange.MONTHLY).toString());
        assertEquals((new DateRangeImpl("20161001")).toString(), lastDayOfSeptember.increment(DateRange.DAILY).toString());
        assertEquals((new DateRangeImpl("20161005")).toString(), lastWeek.increment(DateRange.WEEKLY).toString());

    }

    @Test
    public void testToString() throws Exception {

    }

    @Test
    public void testEquals() throws Exception {
        DateRange dr1 = new DateRangeImpl(2016, 7, 24);

    }

    @Test
    public void testIncludes() throws Exception {
        DateRange in = new DateRangeImpl("20160724");
        DateRange notIn = new DateRangeImpl("20160824");
        DateRange period = new DateRangeImpl("201607");

        assertTrue(period.includes(in));
        assertFalse(period.includes(notIn));
    }

    @Test
    public void testIsIncludedIn() throws Exception {
        DateRange in = new DateRangeImpl("20160724");
        DateRange notIn = new DateRangeImpl("20160824");
        DateRange period = new DateRangeImpl("201607");

        assertTrue(in.isIncludedIn(period));
        assertFalse(notIn.isIncludedIn(period));
    }
}