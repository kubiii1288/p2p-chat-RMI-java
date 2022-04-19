
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

//Run the Server driver 1st then the Node driver

public class ServerDriver {

    public static void main(String[] args) {
        try {
            IServer chatServer = new ServerImp("Admin");
            LocateRegistry.createRegistry(1099);
            String url = "chatroom";
            Naming.rebind(url, chatServer);
            System.out.println("RMI Server Ready...");
        } catch (RemoteException ex) {
            System.err.println("Can't create a new registry");
            ex.printStackTrace();
        } catch (MalformedURLException ex) {
            System.err.println("Unable to see names: " + ex);
        }
    }
}
