package pl.aliberadzki.budget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by aliberadzki on 2016-07-29.
 */
public class InvestmentCategory implements CashFlowCategory {
    private Map<DateRange, Double> expectedBalancesOverridesForMonth;
    private Map<DateRange, Double> expectedBalancesOverridesSinceMonth;
    private List<Operation> expenses;
    private List<CashFlowCategory> subcatgories;

    private DateRange effectiveSince;
    private Double basicAmount = 0.0;

    private String name;
    private Integer id;
    private Integer masterId;

    public InvestmentCategory(Integer categoryId, String description, Double amount, DateRange startingFrom) throws Exception {
        if(startingFrom == null) {
            startingFrom = DateRange.now();
        }
        expectedBalancesOverridesForMonth = new HashMap<>();
        expectedBalancesOverridesSinceMonth = new HashMap<>();
        expenses = new ArrayList<>();
        subcatgories = new ArrayList<>();

        this.id = categoryId;
        this.name = description;
        this.basicAmount = amount;
        this.effectiveSince = startingFrom;
    }

    public InvestmentCategory(Integer id, Integer masterCategoryId, String name, Double plannedAmount, DateRange date) throws Exception {
        this(id, name, plannedAmount, date);
        this.masterId = masterCategoryId;

    }

    @Override
    public Double getExpectedBalanceAt(DateRange date) {
        return null;
    }

    @Override
    public void setNewExpectedAmountFrom(DateRange date, Double amount) throws Exception {

    }

    @Override
    public void setNewExpectedAmountFor(DateRange date, Double amount) throws Exception {

    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void addOperation(Operation operation) throws Exception {

    }

    @Override
    public void forceAddOperation(Operation operation) {

    }

    @Override
    public Double getBalanceAt(DateRange date) {
        return null;
    }

    @Override
    public void addSubCategory(CashFlowCategory subcategory) throws Exception {

    }

    @Override
    public CashFlowCategory getSubCategory(Integer id) {
        return null;
    }

    @Override
    public CashFlowCategory getSubCategory(String name) {
        return null;
    }

    @Override
    public Integer getMasterCategoryId() {
        return null;
    }

    @Override
    public Integer getId() {
        return this.id;
    }
}
