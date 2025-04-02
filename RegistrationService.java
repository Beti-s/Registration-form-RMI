import java.rmi.Remote;
import java.rmi.RemoteException;

// Interface for the registration service
public interface RegistrationService extends Remote {
    boolean register(String username, String mobile, String password, String dob, String address) throws RemoteException;
}
