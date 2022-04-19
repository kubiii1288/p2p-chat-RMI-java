
import java.util.ArrayList;

public class Message implements IMessage {

    private String sender;
    private String receiver;
    private String content;
    private ArrayList<Integer> timeStamp;

    public Message(String sender, String receiver, String content, ArrayList<Integer> timeStamp) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.timeStamp = timeStamp;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public String getSender() {
        return sender;
    }

    @Override
    public String getReceiver() {
        return receiver;
    }

    @Override
    public ArrayList<Integer> getTimeStamp() {
        return timeStamp;
    }

}
