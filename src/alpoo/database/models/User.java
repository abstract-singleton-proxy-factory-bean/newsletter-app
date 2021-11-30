package alpoo.database.models;

/**
 * User model
 */
public class User {
    public final String name;
    public final String email;

    /**
     * Constructor for the user
     * 
     * @param name  User name
     * @param email User email
     */
    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
