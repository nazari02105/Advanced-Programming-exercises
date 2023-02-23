import java.util.HashMap;

public class Event {
    private String title;
    private String startDate;
    private String endDate = null;
    private Integer numberOfRepeat = null;
    private String periodOfRepeat = null;
    private String doesItHasLink = null;
    private String link = null;
    private static HashMap<String, Event> allEvents = new HashMap<>();


    public Event(String title, String startDate) {
        this.title = title;
        this.startDate = startDate;
        allEvents.put(title, this);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public static Event getEventByTitle(String title) {
        return allEvents.get(title);
    }

    public static boolean thisEventExists(String event) {
        return allEvents.containsKey(event);
    }

    public static void removeFromAllEvents(String event) {
        allEvents.remove(event);
    }

    public static void addToAllEvents(String title, Event event) {
        allEvents.put(title, event);
    }
}
