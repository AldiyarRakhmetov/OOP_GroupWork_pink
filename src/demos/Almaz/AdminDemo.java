package demos.Almaz;

import java.time.LocalDateTime;

import models.users.*;
import models.enums.*;
import database.Database;

public class AdminDemo {
    public static void main(String[] args) {

        Database db = Database.getInstance();

        // -------------------------------
        // Create Admin
        // -------------------------------
        Admin admin = new Admin(1, "SuperAdmin", "root123");

        admin.viewProfile();
        System.out.println(admin);

        // -------------------------------
        // Login / Logout
        // -------------------------------
        System.out.println("Login success: " + admin.login("SuperAdmin", "root123"));
        admin.logout();

        // -------------------------------
        // Create Users
        // -------------------------------
        Student student = new Student(
                10, "Alice", "pass",
                "S001", "FIT", "CS", 2
        );

        Teacher teacher = new Teacher(
                20, "DrSmith", "pass",
                TeacherTitle.LECTURER,
                true
        );

        Manager manager = new Manager(
                30, "Manager1", "pass",
                "M001", 80000,
                LocalDateTime.now(),
                false,
                ManagerType.OR_MANAGER
        );

        // -------------------------------
        // Add Users
        // -------------------------------
        System.out.println("\n--- Adding Users ---");
        admin.addUser(student);
        admin.addUser(teacher);
        admin.addUser(manager);

        // -------------------------------
        // View Users in Database
        // -------------------------------
        System.out.println("\n--- All Users in Database ---");
        for (User u : db.getUsers()) {
            System.out.println(u);
        }

        // -------------------------------
        // Update User (simulated)
        // -------------------------------
        System.out.println("\n--- Updating User ---");
        student.setPassword("newpass123");
        admin.updateUser(student);

        // -------------------------------
        // Remove User
        // -------------------------------
        System.out.println("\n--- Removing User ---");
        admin.removeUser(teacher);

        // -------------------------------
        // View Users After Removal
        // -------------------------------
        System.out.println("\n--- Users After Removal ---");
        db.getUsers().forEach(System.out::println);

        // -------------------------------
        // Generate Some Logs from System
        // -------------------------------
        db.log("Manual log entry", "system");
        db.log("Another action", "system");

        // -------------------------------
        // View Logs
        // -------------------------------
        admin.viewLogs();

        // -------------------------------
        // Final Profile
        // -------------------------------
        System.out.println("\n--- Final Profile ---");
        admin.viewProfile();
    }
}