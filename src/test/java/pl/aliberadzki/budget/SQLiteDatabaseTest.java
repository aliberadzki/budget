package pl.aliberadzki.budget;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by aliberadzki on 2016-08-01.
 */
public class SQLiteDatabaseTest {
    @Test
    public void testinitiatingDB() throws Exception {
        Datasource datasource = SQLiteDatabase.instance();

    }
}