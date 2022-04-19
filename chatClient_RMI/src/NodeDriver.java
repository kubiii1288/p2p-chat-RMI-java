
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
//Run the Server driver 1st then the Node driver


public class NodeDriver {

    public static void main(String[] args) {

        try {
            IServer chatServer = (IServer) Naming.lookup("chatroom");

            String clientName = "";
            //avoid duplication
            do {
                clientName = JOptionPane.showInputDialog(null, "Enter User Name:");
            } while (chatServer.contains(clientName));
            
            ClientGUI clientGUI = new ClientGUI();
            clientGUI.setVisible(true);

            //initialize node 
            NodeImp client = new NodeImp(clientName, chatServer);
            client.setGUI(clientGUI);
            clientGUI.setClient(client);
        } catch (NotBoundException | MalformedURLException | RemoteException ex) {
            Logger.getLogger(NodeDriver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
