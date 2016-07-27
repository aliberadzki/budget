package pl.aliberadzki.budget;

/**
 * Created by aliberadzki on 2016-07-25.
 */
public interface Transaction {

    void execute() throws Exception;

    void rollback() throws Exception;
}
