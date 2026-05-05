package database;

import models.users.User;
import models.course.Course;
import models.system.LogEntry;

import java.util.*;

public class Database {

    private static Database instance;

    private List<User> users = new ArrayList<>();
    private List<Course> courses = new ArrayList<>();
    private List<LogEntry> logs = new ArrayList<>();

    private Database() {}

    public static Database getInstance() {
        if (instance == null) instance = new Database();
        return instance;
    }

    public void addUser(User user) {
        users.add(user);
        log("User added", user.getUsername());
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public void removeUser(User user) {
        users.remove(user);
        log("User removed", user.getUsername());
    }

    public List<User> getUsers() {
        return new ArrayList<>(users);
    }
    public List<Course> getCourses() {
        return new ArrayList<>(courses);
    }
    public void removeCourse(Course course) {
        courses.remove(course);
        log("Course removed: " + course.getCode(), "system");
    }

    public void log(String action, String username) {
        logs.add(new LogEntry(action, username));
    }

    public List<LogEntry> getLogs() {
        return new ArrayList<>(logs);
    }
}