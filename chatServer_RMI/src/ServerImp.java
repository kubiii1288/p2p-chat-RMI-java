
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerImp extends UnicastRemoteObject implements IServer {

    private List<String> userLists;
    private String serverName;
    private Map<String, INode> connections;

    public ServerImp(String name) throws RemoteException {
        super();
        userLists = new ArrayList<>();
        this.serverName = name;
        connections = new HashMap<>();
    }

    @Override
    public String getName() throws RemoteException {
        return serverName;
    }

    @Override
    public void broadcastMessage(IMessage msg) throws RemoteException {
        for (String clientName : connections.keySet()) {
            if (!clientName.equals(msg.getSender())) {
                connections.get(clientName).retrieveMessage(msg);
            }
        }
    }

    @Override
    public void registerClient(INode client) throws RemoteException {
        // add new client and update the time stamp of current clients
        for (INode node : connections.values()) {
            node.updateId(true, 0);
        }
        connections.put(client.getName(), client);
        userLists.add(client.getName());
        client.setId(getNumberOfClient() - 1);

        System.out.println(client.getName() + " is just connected!");

    }

    @Override
    public void deleteNode(String target) throws RemoteException {
        if (contains(target)) {
            connections.remove(target);
            synchronized (userLists) {
                int targetId = userLists.indexOf(target);
                userLists.remove(targetId);
                for (String node : connections.keySet()) {
                    connections.get(node).updateId(false, targetId);
                }
            }
            System.out.println(target + " has left!!!");
        }
    }

    @Override
    public boolean contains(String nodeName) throws RemoteException {
        return connections.containsKey(nodeName);
    }

    @Override
    public List<String> getClients() throws RemoteException {
        return userLists;
    }

    @Override
    public String getGreeting() throws RemoteException {
        return "Welcome to chat room";
    }

    @Override
    public void sendUnicast(IMessage msg) throws RemoteException {
        String receiver = msg.getReceiver();
        connections.get(receiver).retrieveMessage(msg);
    }

    @Override
    public Integer getNumberOfClient() {
        return userLists.size();
    }

    @Override
    public int getNodeId(String nodeName) throws RemoteException {
        synchronized (userLists) {
            return userLists.indexOf(nodeName);
        }
    }

    @Override
    public void sendElection(boolean isLeaderMsg, int processID, int leaderID, int nextNodeId) throws RemoteException {
        int receiver = nextNodeId;
        connections.get(userLists.get(receiver)).receiveElection(isLeaderMsg, processID, leaderID);

    }

    @Override
    public void refresh() throws RemoteException {
        for (String node : connections.keySet()) {
            connections.get(node).GUIupdate();
        }
    }

}
