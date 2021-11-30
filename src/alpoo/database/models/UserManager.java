package alpoo.database.models;

import java.sql.PreparedStatement;
import alpoo.database.Database;

/**
 * Manages the instanciation and execution of database procedures for the user
 */
public class UserManager {
    private Database database;

    /**
     * Public constructor for the user manager
     * 
     * @param database Database instance
     */
    public UserManager(Database database) {
        this.database = database;
    }

    /**
     * Creates a user in the database
     * 
     * @param name  User name
     * @param email User email
     * @return User instance
     */
    public User createUser(String name, String email) {
        User user = database.executeWithConnection((connection) -> {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO users (name, email) VALUES (?, ?)");

            statement.setString(1, name);
            statement.setString(2, email);

            return new User(name, email);
        });

        return user;
    }
}
