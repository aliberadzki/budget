package pl.aliberadzki.budget;

import java.sql.*;

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

        String sql = "CREATE TABLE IF NOT EXISTS BUDGET " +
                "(ID    INT     PRIMARY KEY," +
                " NAME  TEXT)";
        statement.executeUpdate(sql);

        sql = "CREATE TABLE IF NOT EXISTS CATEGORY " +
                "(ID        INT     PRIMARY KEY," +
                " BUDGET    INT," +
                " MASTER    INT," +
                " NAME      TEXT," +
                " 'GROUP'     TEXT," +
                " AMOUNT    DOUBLE," +
                " 'FROM'      TEXT," +
                " FOREIGN KEY(BUDGET) REFERENCES BUDGET(ID)," +
                " FOREIGN KEY(MASTER) REFERENCES CATEGORY(ID)" +
                ")";
        statement.executeUpdate(sql);

        sql = "CREATE TABLE IF NOT EXISTS CATEGORY_OVERRIDE " +
                "(ID        INT    PRIMARY KEY," +
                " CATEGORY  INT," +
                " TYPE      TEXT," +
                " AMOUNT    DOUBLE," +
                " 'FROM'      TEXT," +
                " FOREIGN KEY (CATEGORY) REFERENCES CATEGORY(ID)" +
                ")";
        statement.executeUpdate(sql);

        sql = "CREATE TABLE IF NOT EXISTS OPERATION " +
                "(ID        INT    PRIMARY KEY," +
                " CATEGORY  INT," +
                " NAME      TEXT," +
                " AMOUNT    DOUBLE," +
                " 'WHEN'      TEXT," +
                " FOREIGN KEY(CATEGORY) REFERENCES CATEGORY(ID)" +
                ")";
        statement.executeUpdate(sql);
    }
    @Override
    public Budget getBudget(Integer budgetId) {
        return dummy.getBudget(budgetId);
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
        dummy.addBudget(budget);
    }

    @Override
    public CashFlowCategory getCategory(Integer budgetId, Integer categoryId) {
        CashFlowCategory cfc = null;
        try {
            ResultSet rs = statement.executeQuery( "SELECT * FROM CATEGORY WHERE ID =" + categoryId + ";" );
            Integer masterId = rs.getInt("MASTER");
            Double amount = rs.getDouble("AMOUNT");
            String name = rs.getString("NAME");
            DateRange date = new DateRangeImpl(rs.getString("FROM"));
            CashFlowCategoryGroup cfcg = CashFlowCategoryGroup.create(rs.getString("GROUP"));
            cfc = new CashFlowCategoryImpl(categoryId, masterId, name, cfcg, amount, date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cfc;
    }

    @Override
    public void addCategory(Integer budgetId, CashFlowCategory category) throws Exception {
        String sql = "INSERT INTO CATEGORY (ID,BUDGET,MASTER,NAME,'GROUP',AMOUNT,'FROM') " +
                "VALUES " +
                "(" + category.getId() + ", " +
                budgetId + "," +
                " NULL," +
                "'" + category.getName() +"'," +
                "'" + category.getGroup().getCode() + "'," +
                category.getBasicAmount() + "," +
                "'" + category.getStartingFrom() + "'" +
                ");";
        try {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //dummy.addCategory(budgetId, category);
    }

    @Override
    public void addCategory(Integer budgetId, Integer masterCategoryId, CashFlowCategory category) throws Exception {
        String sql = "INSERT INTO CATEGORY (ID,BUDGET,MASTER,NAME,'GROUP',AMOUNT,'FROM') " +
                "VALUES " +
                "(" + category.getId() + ", " +
                budgetId + "," +
                masterCategoryId +"," +
                "'" + category.getName() +"'," +
                "'" + category.getGroup().getCode() + "'," +
                category.getBasicAmount() + "," +
                "'" + category.getStartingFrom() + "'" +
                ");";
        try {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //dummy.addCategory(budgetId, masterCategoryId, category);
    }

    @Override
    public void clear() {
        String sql = "DELETE FROM OPERATION ";
        try {
            statement.executeUpdate(sql);
            sql = "DELETE FROM CATEGORY_OVERRIDE ";
            statement.executeUpdate(sql);
            sql = "DELETE FROM CATEGORY ";
            statement.executeUpdate(sql);
            sql = "DELETE FROM BUDGET ";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dummy.clear();
    }

    public static Datasource instance() {
        if(db == null) {
            try {
                db = new SQLiteDatabase();
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return db;
    }

    @Override
    public void addOperation(Integer budgetId, Integer categoryId, Operation operation) throws Exception {
        dummy.addOperation(budgetId, categoryId, operation);
        String sql = "INSERT INTO OPERATION (ID,CATEGORY,NAME,AMOUNT,'WHEN') " +
                "VALUES " +
                "(" + operation.getId() + ", " +
                categoryId + "," +
                "'" + operation.getName() + "'," +
                operation.getAmount() +"," +
                "'" + operation.getDate() + "'" +
                ");";
        try {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void forceAddOperation(Integer budgetId, Integer categoryId, Operation operation) throws Exception {
        String sql = "INSERT INTO OPERATION (ID,CATEGORY,NAME,AMOUNT,'WHEN') " +
                "VALUES " +
                "(" + operation.getId() + ", " +
                categoryId + "," +
                "'" + operation.getName() + "'," +
                operation.getAmount() +"," +
                "'" + operation.getDate() + "'" +
                ");";
        try {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //dummy.forceAddOperation(budgetId,categoryId, operation);
    }
}
