package demos.Kuanysh;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import models.users.*;
import models.enums.*;
import models.course.*;
import models.system.*;
import database.Database;

public class ManagerDemo {
    public static void main(String[] args) {

        Database db = Database.getInstance();

        // -------------------------------
        // Create Manager
        // -------------------------------
        Manager manager = new Manager(
                1,
                "Admin",
                "admin123",
                "M001",
                90000,
                LocalDateTime.now(),
                false,
                ManagerType.OR_MANAGER
        );

        manager.viewProfile();

        // -------------------------------
        // Login / Logout
        // -------------------------------
        System.out.println("Login success: " + manager.login("Admin", "admin123"));
        manager.logout();

        // -------------------------------
        // Create Course
        // -------------------------------
        Course course = new Course("CS101", "Intro to CS", 3, "CS", 1);
        manager.addCourse(course);

        // -------------------------------
        // Create Teacher & Assign
        // -------------------------------
        Teacher teacher = new Teacher(
                2,
                "DrSmith",
                "pass",
                TeacherTitle.LECTURER,
                true
        );

        manager.assignTeacher(course, teacher);

        // -------------------------------
        // Create Students
        // -------------------------------
        Student s1 = new Student(10, "Alice", "pass", "S001", "FIT", "CS", 2);
        Student s2 = new Student(11, "Bob", "pass", "S002", "FIT", "CS", 3);

        List<Student> students = new ArrayList<>();
        students.add(s1);
        students.add(s2);

        // -------------------------------
        // View Students
        // -------------------------------
        System.out.println("\n--- Students ---");
        manager.viewStudents(students);

        System.out.println("\n--- Students sorted by GPA ---");
        manager.viewStudentsByGPA(students);

        // -------------------------------
        // Course Registration (REAL FLOW)
        // -------------------------------
        CourseRegistration reg1 = new CourseRegistration(1001, s1, course);

        System.out.println("\nBefore approval: " + reg1);

        try {
            manager.approveRegistration(reg1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("After approval: " + reg1);

        // Try approving again (edge case)
        try {
            manager.approveRegistration(reg1);
        } catch (Exception e) {
            System.out.println("Expected error: " + e.getMessage());
        }

        // Reject another registration
        CourseRegistration reg2 = new CourseRegistration(1002, s2, course);
        manager.rejectRegistration(reg2);
        System.out.println("Rejected: " + reg2);

        // -------------------------------
        // News System
        // -------------------------------
        List<News> newsList = new ArrayList<>();

        News news1 = new News("Semester Start", "Welcome to the new semester!");
        News news2 = new News("Exam Week", "Final exams schedule released.");

        manager.addNews(newsList, news1);
        manager.addNews(newsList, news2);

        System.out.println("\n--- All News ---");
        for (News n : newsList) {
            System.out.println(n);
        }

        // Sorting news (uses Comparable)
        newsList.sort(null);
        System.out.println("\n--- News sorted by date ---");
        newsList.forEach(System.out::println);

        // -------------------------------
        // Report Generation
        // -------------------------------
        try {
            manager.createReport("Student Performance Report", students);
            System.out.println("\nReport exported to report.txt");
        } catch (Exception e) {
            System.out.println("Report error: " + e.getMessage());
        }

        // -------------------------------
        // Database Logs
        // -------------------------------
        System.out.println("\n--- System Logs ---");
        for (LogEntry log : db.getLogs()) {
            System.out.println(log);
        }

        // -------------------------------
        // Database Course View
        // -------------------------------
        System.out.println("\n--- Courses in Database ---");
        for (Course c : db.getCourses()) {
            System.out.println(c);
        }

        // Remove course
        db.removeCourse(course);

        System.out.println("\n--- Courses after removal ---");
        db.getCourses().forEach(System.out::println);

        // -------------------------------
        // Final Profile
        // -------------------------------
        System.out.println("\n--- Final Profile ---");
        manager.viewProfile();
    }
}