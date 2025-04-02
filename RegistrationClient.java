import java.rmi.Naming;
import java.util.Scanner;

public class RegistrationClient {
    public static void main(String[] args) {
        try {
            // Replace "127.0.0.1" with the server's actual IP address
            String serverIp = "192.168.1.10"; // Use the actual server IP
            String serviceUrl = "rmi://" + serverIp + ":5000/RegistrationService";

            RegistrationService service = (RegistrationService) Naming.lookup(serviceUrl);

           Scanner scanner = new Scanner(System.in);

            System.out.print("Enter Username: ");
            String username = scanner.nextLine();

            System.out.print("Enter Email: ");
            String email = scanner.nextLine();

            System.out.print("Enter Password: ");
            String password = scanner.nextLine();

            String response = service.registerUser(username, email, password);
            System.out.println(response);

        } catch (Exception e) {
            System.err.println("Client error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
