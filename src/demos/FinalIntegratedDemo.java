package demos;

import database.Database;
import models.course.*;
import models.enums.*;
import models.exceptions.*;
import models.research.*;
import models.system.*;
import models.users.*;
import services.UserFactory;

import java.time.LocalDateTime;
import java.util.*;

public class FinalIntegratedDemo {

    public static void main(String[] args) {
        System.out.println("===== RESEARCH-ORIENTED UNIVERSITY SYSTEM DEMO =====");

        try {
            Database db = Database.getInstance();

            // 1. Users / Factory Pattern
            Admin admin = (Admin) UserFactory.createAdmin(1, "admin", "admin123");

            Student student = new Student(
                    2,
                    "student",
                    "123",
                    "S002",
                    "SITE",
                    "CS",
                    2,
                    true
            );

            Teacher teacher = new Teacher(
                    3,
                    "professor",
                    "123",
                    TeacherTitle.PROFESSOR,
                    true
            );

            Manager manager = new Manager(
                    4,
                    "manager",
                    "123",
                    "M004",
                    500000,
                    LocalDateTime.now(),
                    false,
                    ManagerType.OR_MANAGER
            );

            admin.addUser(student);
            admin.addUser(teacher);
            admin.addUser(manager);

            System.out.println("\n===== LOGIN TEST =====");
            System.out.println("Student login: " + student.login("student", "123"));

            // 2. Course creation
            Course oop = new Course(
                    "CS101",
                    "Object Oriented Programming",
                    3,
                    "CS",
                    2
            );

            Lesson lecture = new Lesson(
                    "L001",
                    "Introduction to OOP",
                    LessonType.LECTURE
            );

            Lesson practice = new Lesson(
                    "P001",
                    "Classes and Objects Practice",
                    LessonType.PRACTICE
            );

            oop.addLesson(lecture);
            oop.addLesson(practice);

            manager.addCourse(oop);

            // 3. Manager assigns teacher
            System.out.println("\n===== MANAGER ASSIGNS TEACHER =====");
            manager.assignTeacher(oop, teacher);
            teacher.viewCourses();

            // 4. Student registration
            System.out.println("\n===== COURSE REGISTRATION =====");
            CourseRegistration registration =
                    new CourseRegistration(1, student, oop);

            manager.approveRegistration(registration);
            System.out.println(registration);

            // 5. Teacher views students
            System.out.println("\n===== TEACHER VIEWS STUDENTS =====");
            teacher.viewStudents(oop);

            // 6. Teacher manages course lesson
            System.out.println("\n===== TEACHER MANAGES COURSE =====");
            Lesson extraLesson = new Lesson(
                    "L002",
                    "Inheritance and Polymorphism",
                    LessonType.LECTURE
            );

            teacher.manageCourse(oop, extraLesson);
            System.out.println("New lesson added by teacher.");

            // 7. Teacher puts mark
            System.out.println("\n===== TEACHER PUTS MARK =====");
            Mark mark = new Mark(27, 28, 35);
            teacher.putMark(student, oop, mark);

            // 8. Student views marks and profile
            System.out.println("\n===== STUDENT MARKS =====");
            student.viewMarks();

            System.out.println("\n===== STUDENT PROFILE =====");
            student.viewProfile();

            System.out.println("\n===== TRANSCRIPT =====");
            System.out.println(student.getTranscript());

            // 9. Student rates teacher
            System.out.println("\n===== STUDENT RATES TEACHER =====");
            student.rateTeacher(teacher, 5);

            // 10. Research functionality
            System.out.println("\n===== RESEARCH FUNCTIONALITY =====");

            ResearchPaper paper1 = new ResearchPaper(
                    "AI in Education",
                    List.of("professor", "student"),
                    "IEEE Education Journal",
                    10,
                    new Date(),
                    15,
                    "10.1000/ai-edu"
            );

            ResearchPaper paper2 = new ResearchPaper(
                    "OOP Learning Systems",
                    List.of("professor"),
                    "Computer Science Review",
                    8,
                    new Date(),
                    7,
                    "10.1000/oop-sys"
            );

            teacher.addPaper(paper1);
            teacher.addPaper(paper2);

            ResearchProject project = new ResearchProject("AI University Assistant");

            project.addParticipant(teacher);
            project.addParticipant(student);

            project.addPaper(paper1);
            project.addPaper(paper2);

            System.out.println("\n--- Papers sorted by citations ---");
            teacher.printPapers(ResearchPaperComparators.BY_CITATIONS);

            System.out.println("\n--- Project info ---");
            project.printProjectInfo();

            // 11. Supervisor logic
            System.out.println("\n===== SUPERVISOR LOGIC =====");
            Student fourthYearStudent = new Student(
                    5,
                    "senior",
                    "123",
                    "S005",
                    "SITE",
                    "CS",
                    4,
                    true
            );

            admin.addUser(fourthYearStudent);

            try {
                fourthYearStudent.setSupervisor(teacher);
            } catch (InvalidSupervisorException e) {
                System.out.println("Supervisor error: " + e.getMessage());
            }

            // 12. Manager report
            System.out.println("\n===== MANAGER VIEWS STUDENTS BY GPA =====");
            manager.viewStudentsByGPA(List.of(student, fourthYearStudent));

            // 13. News
            System.out.println("\n===== NEWS =====");
            List<News> newsList = new ArrayList<>();
            News news = new News("Registration opened", "Course registration is now available.");
            manager.addNews(newsList, news);

            for (News n : newsList) {
                System.out.println(n);
            }

            // 14. Error handling demo
            System.out.println("\n===== ERROR HANDLING DEMO =====");

            try {
                Mark invalidMark = new Mark(-10, 50, 100);
                teacher.putMark(student, oop, invalidMark);
            } catch (Exception e) {
                System.out.println("Invalid mark rejected: " + e.getMessage());
            }

            try {
                student.registerForCourse(oop);
            } catch (Exception e) {
                System.out.println("Duplicate registration rejected: " + e.getMessage());
            }

            try {
                ResearchProject invalidProject = new ResearchProject("Invalid Join Test");
                invalidProject.addParticipant(new Object());
            } catch (Exception e) {
                System.out.println("Non-researcher rejected: " + e.getMessage());
            }

            // 15. Admin logs
            System.out.println("\n===== SYSTEM LOGS =====");
            admin.viewLogs();

            System.out.println("\n===== DEMO FINISHED SUCCESSFULLY =====");

        } catch (Exception e) {
            System.out.println("DEMO ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
}