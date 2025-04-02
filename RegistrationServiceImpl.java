import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class RegistrationServiceImpl extends UnicastRemoteObject implements RegistrationService {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/web_project";
    private static final String DB_USER = "root"; // Use your DB username
    private static final String DB_PASSWORD = "Betty@22292229"; // Use your DB password

    // Constructor to export the remote object and initialize the database
    protected RegistrationServiceImpl() throws RemoteException {
        super();
        initializeDatabase();
    }

    // Method to create the database and table if they don't exist
    private void initializeDatabase() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/", DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement()) {

            // Create the database if it doesn't exist
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS registration_db");

            // Connect to the specific database and create the table
            try (Connection dbConnection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 Statement dbStatement = dbConnection.createStatement()) {

                String createTableQuery = "CREATE TABLE IF NOT EXISTS registrations (" +
                                          "id INT AUTO_INCREMENT PRIMARY KEY, " +
                                          "username VARCHAR(100) NOT NULL, " +
                                          "mobile VARCHAR(15) NOT NULL, " +
                                          "password VARCHAR(100) NOT NULL, " +
                                          "email VARCHAR(255) NOT NULL, " +
                                          "dob DATE NOT NULL, " +
                                          "address TEXT NOT NULL)";

                
                
                dbStatement.executeUpdate(createTableQuery);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error initializing the database: " + e.getMessage());
        }
    }

    // Implementation of the register method to save data in the database
    @Override
    public boolean register(String username, String mobile, String password, String dob, String address) throws RemoteException {
        System.out.println("Received data: " + username + ", " + mobile + ", " + password + ", " + dob + ", " + address);

        String insertQuery = "INSERT INTO registrations (username, mobile, password, dob, address) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, mobile);
            preparedStatement.setString(3, password);
            preparedStatement.setDate(4, java.sql.Date.valueOf(dob)); // Convert String to SQL Date
            preparedStatement.setString(5, address);

            preparedStatement.executeUpdate(); // Execute the query
            return true; // Indicate success
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error saving data to the database: " + e.getMessage());
            return false; // Indicate failure
        }
    }
}
