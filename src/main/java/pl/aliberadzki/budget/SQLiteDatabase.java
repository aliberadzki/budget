package pl.aliberadzki.budget;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by aliberadzki on 2016-08-01.
 */
public class SQLiteDatabase implements Datasource {

    private static SQLiteDatabase db;

    private Connection connection;
    private Statement statement;
    private Datasource dummy = DummyDatabase.instance();

    private SQLiteDatabase() throws SQLException, ClassNotFoundException {

        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:test.db");
        statement = connection.createStatement();

        String sql = "CREATE TABLE BUDGET " +
                "(ID INT PRIMARY KEY     NOT NULL," +
                " NAME           TEXT    NOT NULL)";
        statement.executeUpdate(sql);

        sql = "CREATE TABLE CATEGORY " +
                "(ID INT PRIMARY KEY     NOT NULL," +
                " NAME           TEXT    NOT NULL)";
        statement.executeUpdate(sql);
        statement.close();
        connection.close();
    }
    @Override
    public Budget getBudget(Integer budgetId) {
        return db.getBudget(budgetId);
    }

    @Override
    public void addBudget(Budget budget) {
        String sql = "INSERT INTO BUDGET (ID,NAME) " +
                "VALUES (" + budget.getId() + ", '" + budget.getName() + "');";
        try {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        db.addBudget(budget);
    }

    @Override
    public CashFlowCategory getCategory(Integer budgetId, Integer categoryId) {
        return db.getCategory(budgetId, categoryId);
    }

    @Override
    public void addCategory(Integer budgetId, CashFlowCategory category) throws Exception {
        db.addCategory(budgetId, category);
    }

    @Override
    public void addCategory(Integer budgetId, Integer masterCategoryId, CashFlowCategory category) throws Exception {
        db.addCategory(budgetId, masterCategoryId, category);
    }

    @Override
    public void clear() {
        db.clear();
    }

    public static Datasource instance() {
        if(db == null) {
            try {
                db = new SQLiteDatabase();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return db;
    }

    @Override
    public void addOperation(Integer budgetId, Integer categoryId, Operation operation) throws Exception {
        db.addOperation(budgetId, categoryId, operation);
    }

    @Override
    public void forceAddOperation(Integer budgetId, Integer categoryId, Operation operation) throws Exception {
        db.forceAddOperation(budgetId,categoryId, operation);
    }
}
