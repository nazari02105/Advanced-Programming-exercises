import java.util.HashMap;

public class Task {
    private String title;
    private String startTime;
    private String endTime;
    private String startDate;
    private String endDate = null;
    private Integer numberOfRepeat = null;
    private String periodOfRepeat = null;
    private String doesItHasLink = null;
    private String link = null;
    private static HashMap<String, Task> allTasks = new HashMap<>();

    public Task(String title, String startTime, String endTime, String startDate) {
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startDate = startDate;
        allTasks.put(title, this);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getNumberOfRepeat() {
        return numberOfRepeat;
    }

    public void setNumberOfRepeat(Integer numberOfRepeat) {
        this.numberOfRepeat = numberOfRepeat;
    }

    public String getPeriodOfRepeat() {
        return periodOfRepeat;
    }

    public void setPeriodOfRepeat(String periodOfRepeat) {
        this.periodOfRepeat = periodOfRepeat;
    }

    public String getDoesItHasLink() {
        return doesItHasLink;
    }

    public void setDoesItHasLink(String doesItHasLink) {
        this.doesItHasLink = doesItHasLink;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public static Task getTaskByTitle(String title) {
        return allTasks.get(title);
    }

    public static boolean thisTaskExists(String task) {
        return allTasks.containsKey(task);
    }

    public static void removeFromAllTasks(String task) {
        allTasks.remove(task);
    }

    public static void addToAllTasks(String title, Task task) {
        allTasks.put(title, task);
    }
}
