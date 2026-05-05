package models.users;

import models.system.LogEntry;
import database.Database;

import java.util.ArrayList;
import java.util.List;

public class Admin extends User {

    public Admin(int id, String username, String password) {
        super(id, username, password);
    }

    public void addUser(User user) {
        Database.getInstance().addUser(user);

        System.out.println("Admin [" + username + "] added user: " + user.getUsername());
    }

    public void updateUser(User user) {
        Database.getInstance().log("UPDATE_USER: " + user.getUsername(), username);

        System.out.println("Admin [" + username + "] updated user: " + user.getUsername());
    }

    public void removeUser(User user) {
        Database.getInstance().removeUser(user);

        System.out.println("Admin [" + username + "] removed user: " + user.getUsername());
    }

    public void viewLogs() {

        var logs = Database.getInstance().getLogs();

        if (logs.isEmpty()) {
            System.out.println("No log entries found.");
            return;
        }

        System.out.println("\n=== SYSTEM LOGS ===");

        for (LogEntry log : logs) {
            System.out.println(log);
        }

        System.out.println("Total entries: " + logs.size());
    }


    @Override
    public void viewProfile() {
        System.out.println("Admin Profile");
        System.out.println("ID: " + id);
        System.out.println("Username: " + username);
        System.out.println("Role: Admin");
        int logCount = Database.getInstance().getLogs().size();
        System.out.println("Actions logged: " + logCount);
    }

    @Override
    public String toString() {
        return "Admin: " + username + " (id=" + id + ")";
    }
}