
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IServer extends Remote {

    public String getName() throws RemoteException;

    public void broadcastMessage(IMessage msg) throws RemoteException;

    public void sendUnicast(IMessage msg) throws RemoteException;

    public void registerClient(INode client) throws RemoteException;

    public void deleteNode(String target) throws RemoteException;

    public boolean contains(String nodeName) throws RemoteException;

    public List<String> getClients() throws RemoteException;

    public String getGreeting() throws RemoteException;
    
    public Integer getNumberOfClient() throws RemoteException;
    
    public int getNodeId(String nodeName) throws RemoteException;
    
    public void sendElection(boolean isLeaderMsg, int processID, int leaderID, int nextNodeId) throws RemoteException;
    
    public void refresh() throws RemoteException;
}
