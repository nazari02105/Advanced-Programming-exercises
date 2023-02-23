import java.util.ArrayList;
import java.util.HashMap;

public class Taghvim {
    private String ownerOfTaghvim;
    private String titleOfTaghvim;
    private int idOfTaghvim;
    private static HashMap<Integer, Taghvim> allUsersAndTheirTaghvim = new HashMap<>();
    public static int counter = 0;
    private ArrayList<String> taghvimEvents;
    private ArrayList<String> taghvimTasks;
    private ArrayList<String> sharedWith;


    public Taghvim(String ownerOfTaghvim, String titleOfTaghvim) {
        this.ownerOfTaghvim = ownerOfTaghvim;
        this.titleOfTaghvim = titleOfTaghvim;
        counter += 1;
        this.idOfTaghvim = counter;
        allUsersAndTheirTaghvim.put(counter, this);
        taghvimEvents = new ArrayList<>();
        taghvimTasks = new ArrayList<>();
        sharedWith = new ArrayList<>();
    }

    public String getOwnerOfTaghvim() {
        return ownerOfTaghvim;
    }

    public static boolean doesItHasTaghvim(int id) {
        return allUsersAndTheirTaghvim.containsKey(id);
    }

    public static void removeTaghvim(int id) {
        allUsersAndTheirTaghvim.remove(id);
    }

    public static Taghvim getTheTaghvimById(int id) {
        return allUsersAndTheirTaghvim.get(id);
    }

    public int getIdOfTaghvim() {
        return idOfTaghvim;
    }

    public String getTitleOfTaghvim() {
        return titleOfTaghvim;
    }

    public void setTitleOfTaghvim(String titleOfTaghvim) {
        this.titleOfTaghvim = titleOfTaghvim;
    }

    public void addToTaghvimEvents(String event) {
        this.taghvimEvents.add(event);
    }

    public void addToTaghvimTasks(String task) {
        this.taghvimTasks.add(task);
    }

    public void removeFromTaghvimEvents(String event) {
        this.taghvimEvents.remove(event);
    }

    public void removeFromTaghvimTasks(String task) {
        this.taghvimTasks.remove(task);
    }

    public boolean doesTaghvimEventsContain(String event) {
        return this.taghvimEvents.contains(event);
    }

    public boolean doesTaghvimTasksContain(String task) {
        return this.taghvimTasks.contains(task);
    }

    public ArrayList<String> allEventsOfTaghvim() {
        return this.taghvimEvents;
    }

    public ArrayList<String> allTasksOfTaghvim() {
        return this.taghvimTasks;
    }

    public void addToSharedWith(String user) {
        sharedWith.add(user);
    }

    public ArrayList<String> getArrayOfSharedWith() {
        return sharedWith;
    }

    public void removeFromSharedWith(String user) {
        sharedWith.remove(user);
    }
}
