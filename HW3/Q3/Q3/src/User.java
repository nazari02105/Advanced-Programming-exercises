import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User {
    public static HashMap<String, User> allUsers = new HashMap<>();
    private String username;
    private String password;
    private Integer port = null;
    private HashMap<String, Integer> usernamePortArray = new HashMap<>();
    private HashMap<String, String> usernameHostArray = new HashMap<>();
    private HashMap<String, ArrayList<String>> messagesReceived = new HashMap<>();
    private ArrayList<String> allMessages = new ArrayList<>();
    private String focusedHost = null;
    private Integer focusedPort = null;

    public User (String username, String password){
        this.username = username;
        this.password = password;
        allUsers.put(username, this);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public void addToUsernamePortArray (String username, Integer port){
        this.usernamePortArray.put(username, port);
    }

    public HashMap<String, Integer> getUsernamePortArray() {
        return usernamePortArray;
    }

    public void addToUsernameHostArray (String username, String host){
        this.usernameHostArray.put(username, host);
    }

    public HashMap<String, String> getUsernameHostArray() {
        return usernameHostArray;
    }

    public static User getUserByPort (Integer port){
        for (Map.Entry<String, User> entry : allUsers.entrySet()){
            if (entry.getValue().getPort() != null && entry.getValue().getPort().equals(port))
                return entry.getValue();
        }
        return null;
    }

    public HashMap<String, ArrayList<String>> getMessagesReceived() {
        return messagesReceived;
    }

    public void setMessagesReceived(HashMap<String, ArrayList<String>> messagesReceived) {
        this.messagesReceived = messagesReceived;
    }

    public String getFocusedHost() {
        return focusedHost;
    }

    public void setFocusedHost(String focusedHost) {
        this.focusedHost = focusedHost;
    }

    public Integer getFocusedPort() {
        return focusedPort;
    }

    public void setFocusedPort(Integer focusedPort) {
        this.focusedPort = focusedPort;
    }

    public ArrayList<String> getAllMessages() {
        return allMessages;
    }

    public void setAllMessages(ArrayList<String> allMessages) {
        this.allMessages = allMessages;
    }
}
