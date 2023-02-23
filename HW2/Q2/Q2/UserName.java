import java.util.ArrayList;
import java.util.HashMap;

public class UserName {
    private String userName;
    private String password;
    private int score = 0;
    private int wins = 0;
    private int draws = 0;
    private int loses = 0;
    private int coins = 50;
    public static HashMap<String, String> allUsers = new HashMap<String, String>();
    public static ArrayList<UserName> allUsersObject = new ArrayList<UserName>();


    public UserName(String userName, String password) {
        this.userName = userName;
        this.password = password;
        allUsers.put(userName, password);
        allUsersObject.add(this);
    }

    public static boolean userExists(String userName) {
        return allUsers.containsKey(userName);
    }

    public static boolean isPasswordTrue(String userName, String password) {
        return allUsers.get(userName).equals(password);
    }

    public static UserName getObjectByUserName(String userName) {
        for (int i = 0; i < allUsersObject.size(); ++i) {
            if (allUsersObject.get(i).userName.equals(userName)) return allUsersObject.get(i);
        }
        return null;
    }

    public ArrayList<UserName> getAllUsersObject() {
        return allUsersObject;
    }

    public int getScore() {
        return score;
    }

    public int getWins() {
        return wins;
    }

    public int getDraws() {
        return draws;
    }

    public int getLoses() {
        return loses;
    }

    public String getUserName() {
        return userName;
    }

    public int getCoins() {
        return coins;
    }

    public void increaseCoins(int amount) {
        this.coins += amount;
    }

    public void decreaseCoins(int amount) {
        this.coins -= amount;
    }

    public void increaseScore(int score) {
        this.score += score;
    }

    public void increaseWin() {
        this.wins += 1;
    }

    public void increaseDraw() {
        this.draws += 1;
    }

    public void increseLose() {
        this.loses += 1;
    }

    public String getPassword() {
        return password;
    }
}
