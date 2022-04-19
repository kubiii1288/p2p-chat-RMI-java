
import java.io.Serializable;
import java.util.ArrayList;

public interface IMessage extends Serializable {

    public String getContent();

    public String getSender();

    public String getReceiver();

    public ArrayList<Integer> getTimeStamp();
}
