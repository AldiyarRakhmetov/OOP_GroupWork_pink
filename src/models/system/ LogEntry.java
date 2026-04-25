package models.system;

import java.util.Date;

public class LogEntry {
    private String action;
    private Date timestamp;
    private String username;

    public LogEntry(String action, String username) {
        this.action    = action;
        this.username  = username;
        this.timestamp = new Date(); // текущее время
    }

    @Override
    public String toString() {
        return String.format("[%s] %-20s → %s", timestamp, username, action);
    }

    public String getAction()    { return action; }
    public Date getTimestamp()   { return timestamp; }
    public String getUsername()  { return username; }
}