
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class NodeImp extends UnicastRemoteObject implements INode {

    private boolean participant;
    private Integer id;
    private String name;
    private IServer chatserver;
    private ArrayList<Integer> timeStamp;
    private ClientGUI gui;

    public NodeImp(String name, IServer server) throws RemoteException {
        participant = false;
        this.name = name;
        this.chatserver = server;
        chatserver.registerClient(this);
        timeStamp = new ArrayList<>(Collections.nCopies(chatserver.getNumberOfClient(), 0));
    }

    public void setGUI(ClientGUI gui) {
        this.gui = gui;
    }

    @Override
    public String getName() throws RemoteException {
        return this.name;
    }

    @Override
    public void retrieveMessage(IMessage msg) throws RemoteException {
        increaseTimeStamp();
        updateTimpStamp(msg.getTimeStamp());
        displayMessage(msg);
        gui.displayTimeStamp();
    }

    @Override
    public void sendUnicast(IMessage msg) throws RemoteException {
        increaseTimeStamp();
        chatserver.sendUnicast(msg);
        String msgToDisplay = "-> [" + msg.getReceiver() + "] " + msg.getContent();
        gui.displayMessage(msgToDisplay);
        gui.displayTimeStamp();

    }

    @Override
    public void sendBroadCast(IMessage msg) throws RemoteException {
        increaseTimeStamp();
        chatserver.broadcastMessage(msg);
        String msgToDisplay = "[" + msg.getSender() + "]: " + msg.getContent();
        gui.displayMessage(msgToDisplay);
        gui.displayTimeStamp();

    }

    private void displayMessage(IMessage msg) {
        String msgToDisplay = "[" + msg.getSender() + "]: " + msg.getContent();
        gui.displayMessage(msgToDisplay);

    }

    private void updateTimpStamp(ArrayList<Integer> otherTimeStamp) {
        //update vector time stamp
        for (int i = 0; i < this.timeStamp.size(); i++) {
            timeStamp.set(i, Math.max(timeStamp.get(i), otherTimeStamp.get(i)));
        }
    }

    @Override
    public ArrayList<Integer> getTimeStamp() throws RemoteException {
        return this.timeStamp;
    }

    @Override
    public void setId(int id) throws RemoteException {
        this.id = id;
    }

    @Override
    public int getId() throws RemoteException {
        return id;
    }

    private void increaseTimeStamp() {
        synchronized (timeStamp) {
            timeStamp.set(id, 1 + timeStamp.get(id));
        }
    }

    @Override
    public void updateId(boolean add, int index) throws RemoteException {
        if (add) {
            //new node connects to server
            this.timeStamp.add(0);
            increaseTimeStamp();

        } else {
            //update when node get disconnected
            timeStamp.remove(index);
            if (id > index) {
                id--;
            }
            increaseTimeStamp();
        }
        GUIupdate();
    }

    @Override
    public void disconnect() throws RemoteException {
        chatserver.deleteNode(getName());
    }

    @Override
    public List<String> getUsers() throws RemoteException {
        return chatserver.getClients();
    }

    @Override
    public void GUIupdate() throws RemoteException {
        gui.updateUsers();
    }
    //Chang-Robert election algorithm

    @Override
    public void startElection() throws RemoteException {
        participant = true;
        increaseTimeStamp();
        sendElection(false, this.hashCode(), -1, (id + 1) % chatserver.getNumberOfClient());
    }

    @Override
    public void sendElection(boolean isLeaderMsg, int processId, int leaderId, int nextNodeId) throws RemoteException {
        increaseTimeStamp();
        chatserver.sendElection(isLeaderMsg, processId, leaderId, nextNodeId);
    }

    @Override
    public void receiveElection(boolean isLeaderMsg, int processId, int leaderId) throws RemoteException {
        increaseTimeStamp();
        if (!isLeaderMsg) {
            if (processId > this.hashCode()) {
                participant = true;
                sendElection(false, processId, -1, (id + 1) % chatserver.getNumberOfClient());
            } else if (processId == this.hashCode()) {
                sendElection(true, this.hashCode(), this.hashCode(), (id + 1) % chatserver.getNumberOfClient());
            } else if (processId < this.hashCode()) {
                if (!participant) {
                    startElection();
                }
            }
        } else {
            int leaderID = leaderId;
            participant = false;
            if (leaderID != this.hashCode()) {
                sendElection(true, this.hashCode(), leaderID, (id + 1) % chatserver.getNumberOfClient());
            } else {

                IMessage message = new Message(this.name, "all", this.name + " is a leader", getTimeStamp());
                sendBroadCast(message);
            }
        }
    }

    @Override
    public void refresh() throws RemoteException {
        chatserver.refresh();
    }

}
