package pl.aliberadzki.budget;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by aliberadzki on 2016-07-15.
 */
public class DummyDatabase implements Datasource {
    private static DummyDatabase db;

    private Map<String, CashFlowCategory> allCategories;

    public static Datasource instance() {
        if(db == null) {
            db = new DummyDatabase();
        }
        return db;
    }

    private DummyDatabase() {
        this.allCategories = new HashMap<>();
    }

    public void addCategory(CashFlowCategory cfc) {

    }
}
