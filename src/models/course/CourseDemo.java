package models.course;
import models.users.*;
import models.enums.*;
import java.util.Date;

public class CourseDemo {
    public static void main(String[] args) {

        try {
            // 👤 создаём пользователей
            Student student1 = new Student(1, "john", "123", "S1", "FIT", "CS", 2);
            Student student2 = new Student(2, "alice", "123", "S2", "FIT", "CS", 2);

            Teacher teacher = new Teacher(
                    3,
                    "prof",
                    "123",
                    TeacherTitle.PROFESSOR,
                    true
            );

            //  создаём курс
            Course course = new Course("CS101", "OOP", 3, "CS", 2);

            // 👨‍ назначаем преподавателя
            course.addTeacher(teacher);

            //  студенты регистрируются
            student1.registerForCourse(course);
            student2.registerForCourse(course);

            //  преподаватель ставит оценки
            Mark m1 = new Mark(25, 25, 35);
            Mark m2 = new Mark(20, 20, 30);

            teacher.putMark(student1, course, m1);
            teacher.putMark(student2, course, m2);

            // выводим студентов курса
            System.out.println("\n=== STUDENTS IN COURSE ===");
            for (Student s : course.getStudents()) {
                System.out.println(s);
            }

            //  выводим оценки
            System.out.println("\n=== MARKS ===");
            System.out.println(student1.getUsername() + ": " + course.getMark(student1));
            System.out.println(student2.getUsername() + ": " + course.getMark(student2));

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}