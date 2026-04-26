package models.users;

import models.system.LogEntry;

import java.util.ArrayList;
import java.util.List;

public class Admin extends User {

    private List<LogEntry> logs;

    public Admin(int id, String username, String password) {
        super(id, username, password);
        this.logs = new ArrayList<>();
    }

    public void addUser(User user) {
        System.out.println("Admin [" + username + "] added user: " + user.getUsername());
        log("ADD_USER: " + user.getUsername());
    }

    public void updateUser(User user) {
        System.out.println("Admin [" + username + "] updated user: " + user.getUsername());
        log("UPDATE_USER: " + user.getUsername());
    }

    public void removeUser(User user) {
        System.out.println("Admin [" + username + "] removed user: " + user.getUsername());
        log("REMOVE_USER: " + user.getUsername());
    }

    public void viewLogFiles() {
        if (logs.isEmpty()) {
            System.out.println("No log entries found.");
            return;
        }
        System.out.println("SYSTEM LOGS");
        for (LogEntry entry : logs) {
            System.out.println(entry.toString());
        }
        System.out.println("Total entries: " + logs.size());
    }

    private void log(String action) {
        logs.add(new LogEntry(action, this.username));
    }

    public List<LogEntry> getLogs() {
        return logs;
    }

    @Override
    public void viewProfile() {
        System.out.println("Admin Profile");
        System.out.println("ID: " + id);
        System.out.println("Username: " + username);
        System.out.println("Role: Admin");
        System.out.println("Actions logged: " + logs.size());
    }

    @Override
    public String toString() {
        return "Admin: " + username + " (id=" + id + ")";
    }
}