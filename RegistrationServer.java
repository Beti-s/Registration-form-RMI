import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class RegistrationServer {
    public static void main(String[] args) {
        try {
            // Set the hostname or IP address of the server
            System.setProperty("java.rmi.server.hostname", "10.198.70.101");

            // Start the RMI registry programmatically
            LocateRegistry.createRegistry(5000);

            // Bind the service
            RegistrationService service = new RegistrationServiceImpl();
            Naming.rebind("rmi://10.198.70.101:5000/RegistrationService", service);

            System.out.println("Server is running...");
        } catch (Exception e) {
            System.err.println("Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}