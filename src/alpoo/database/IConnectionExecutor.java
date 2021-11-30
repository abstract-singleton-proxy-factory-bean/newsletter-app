package alpoo.database;

import java.sql.Connection;

/**
 * Functional interface for handling database procedures
 */
public interface IConnectionExecutor<T> {
    /**
     * Executes the database procedure
     * 
     * @param connection Database connection
     * @return Result of the procedure
     * @throws Exception Exception thrown by the database
     */
    T call(Connection connection) throws Exception;
}
