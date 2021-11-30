package alpoo.database;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Manager for the database connections
 */
public class Database {
    private static Database instance;
    private Connection connection;

    /**
     * Gets an instance of the database
     * 
     * @return Database instance
     */
    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }

        return instance;
    }

    /**
     * Private constructor for the database
     */
    private Database() {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/alpoo", "root", "docker");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Executes a database procedure with a connection
     * 
     * @param <T>      Return type of the procedure
     * @param executor Executor interface for the database procedure
     * @return Result of the procedure
     */
    public <T> T executeWithConnection(IConnectionExecutor<T> executor) {
        try {
            T result = executor.call(this.connection);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
