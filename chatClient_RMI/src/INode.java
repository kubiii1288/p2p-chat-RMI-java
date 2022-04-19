
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface INode extends Remote {

    public String getName() throws RemoteException;

    public void retrieveMessage(IMessage msg) throws RemoteException;

    public void sendUnicast(IMessage msg) throws RemoteException;

    public void sendBroadCast(IMessage msg) throws RemoteException;
    
    public ArrayList<Integer> getTimeStamp() throws RemoteException;
    
    public void setId(int id) throws RemoteException;
    
    public int getId() throws RemoteException;
    
    public void updateId(boolean add, int index) throws RemoteException;
    
    public void disconnect() throws RemoteException;
    
    public List<String> getUsers() throws RemoteException;
    
    public void GUIupdate() throws RemoteException;
    
    public void startElection() throws RemoteException;
    
    public void sendElection(boolean isLeaderMsg, int processId, int leaderId, int nextNodeId) throws RemoteException;
    
    public void receiveElection(boolean isLeaderMsg, int processId, int leaderId) throws RemoteException;
    
    public void refresh() throws RemoteException;

}
