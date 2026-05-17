package services;

import models.users.*;
import models.enums.TeacherTitle;
import models.enums.ManagerType;

import java.time.LocalDateTime;

public class UserFactory {

    private UserFactory() {}

    public static User createStudent(int id, String username, String password) {
        return new Student(
                id,
                username,
                password,
                "S" + id,
                "SITE",
                "CS",
                1
        );
    }

    public static User createTeacher(int id, String username, String password) {
        return new Teacher(
                id,
                username,
                password,
                TeacherTitle.TUTOR,
                false
        );
    }

    public static User createProfessor(int id, String username, String password) {
        return new Teacher(
                id,
                username,
                password,
                TeacherTitle.PROFESSOR,
                true
        );
    }

    public static User createManager(int id, String username, String password) {
        return new Manager(
                id,
                username,
                password,
                "M" + id,
                500000,
                LocalDateTime.now(),
                false,
                ManagerType.OR_MANAGER
        );
    }

    public static User createAdmin(int id, String username, String password) {
        return new Admin(id, username, password);
    }
}