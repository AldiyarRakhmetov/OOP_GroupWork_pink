package models.users;

import models.system.LogEntry;
import database.Database;

public class Admin extends User {

    public Admin(int id, String username, String password) {
        super(id, username, password);
    }

    public void addUser(User user) {
        if (user == null) throw new IllegalArgumentException("User cannot be null");
        Database.getInstance().addUser(user);
        System.out.println("Admin [" + username + "] added user: " + user.getUsername());
    }

    public void updateUser(User user) {
        if (user == null) throw new IllegalArgumentException("User cannot be null");
        Database.getInstance().log("UPDATE_USER: " + user.getUsername(), username);
        System.out.println("Admin [" + username + "] updated user: " + user.getUsername());
    }

    public void removeUser(User user) {
        if (user == null) throw new IllegalArgumentException("User cannot be null");
        Database.getInstance().removeUser(user);
        System.out.println("Admin [" + username + "] removed user: " + user.getUsername());
    }

    public void viewLogs() {
        var logs = Database.getInstance().getLogs();
        if (logs.isEmpty()) { System.out.println("No log entries found."); return; }
        System.out.println("\n=== SYSTEM LOGS ===");
        for (LogEntry log : logs) System.out.println(log);
        System.out.println("Total entries: " + logs.size());
    }

    @Override
    public void viewProfile() {
        System.out.println("Admin Profile");
        System.out.println("ID: " + id);
        System.out.println("Username: " + username);
        System.out.println("Role: Admin");
        System.out.println("Actions logged: " + Database.getInstance().getLogs().size());
    }

    @Override
    public String toString() {
        return "Admin: " + username + " (id=" + id + ")";
    }
}
