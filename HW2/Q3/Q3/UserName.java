import java.util.ArrayList;
import java.util.HashMap;

public class UserName {
    private String userName;
    private String password;
    private ArrayList<Integer> userOwnTaghvims;
    private ArrayList<Integer> userSharedTaghvims;
    private ArrayList<Integer> enabledTaghvims;
    private int logedInBefore = 0;
    private static HashMap<String, String> allUsersWithTheirPassword = new HashMap<>();
    private static ArrayList<UserName> allUsersObject = new ArrayList<>();

    public UserName(String userName, String password) {
        this.userName = userName;
        this.password = password;
        this.userOwnTaghvims = new ArrayList<>();
        this.userSharedTaghvims = new ArrayList<>();
        this.enabledTaghvims = new ArrayList<>();
        allUsersWithTheirPassword.put(userName, password);
        allUsersObject.add(this);
    }

    public static UserName getObjectByUserName(String userName) {
        for (int i = 0; i < allUsersObject.size(); ++i) {
            if (allUsersObject.get(i).userName.equals(userName)) return allUsersObject.get(i);
        }
        return null;
    }

    public static boolean checkingPassword(String userName, String password) {
        if (allUsersWithTheirPassword.containsKey(userName))
            return allUsersWithTheirPassword.get(userName).equals(password);
        return false;
    }

    public static boolean doesUserNameExists(String userName) {
        return allUsersWithTheirPassword.containsKey(userName);
    }

    public static void changePassword(String newPassword, String userName) {
        UserName userNameWhomWantToChange = UserName.getObjectByUserName(userName);
        userNameWhomWantToChange.setPassword(newPassword);
        allUsersWithTheirPassword.put(userName, newPassword);
    }

    private void setPassword(String newPassword) {
        this.password = newPassword;
    }

    public static void removeUser(String userName) {
        UserName userToRemove = getObjectByUserName(userName);
        allUsersObject.remove(userToRemove);
        allUsersWithTheirPassword.remove(userName);
    }

    public static ArrayList<String> getAllUserNames() {
        ArrayList<String> allUsers = new ArrayList<>();
        allUsersWithTheirPassword.forEach((key, value) -> {
            allUsers.add(key);
        });
        return allUsers;
    }

    public void addToUserOwnTaghvims(int id) {
        this.userOwnTaghvims.add(id);
    }

    public void addToSharedTaghvims(int id) {
        this.userSharedTaghvims.add(id);
    }

    public void removeFromUserOwnTaghvims(int id) {
        this.userOwnTaghvims.remove((Integer) id);
    }

    public void removeFromSharedTaghvims(int id) {
        this.userSharedTaghvims.remove((Integer) id);
    }

    public boolean doesUserOwnTaghvimsContain(int id) {
        return this.userOwnTaghvims.contains(id);
    }

    public boolean doesUserSharedTaghvimsContain(int id) {
        return this.userSharedTaghvims.contains(id);
    }

    public void setLogedInBefore(int logedInBefore) {
        this.logedInBefore = logedInBefore;
    }

    public int getLogedInBefore() {
        return logedInBefore;
    }

    public ArrayList<Integer> getUserOwnTaghvims() {
        return this.userOwnTaghvims;
    }

    public ArrayList<Integer> getUserSharedTaghvims() {
        return this.userSharedTaghvims;
    }

    public void addToEnabledTaghvims(Integer id) {
        enabledTaghvims.add(id);
    }

    public void removeFromEnabledTaghvims(Integer id) {
        enabledTaghvims.remove(id);
    }

    public ArrayList<Integer> getEnabledTaghvims() {
        return enabledTaghvims;
    }
}
