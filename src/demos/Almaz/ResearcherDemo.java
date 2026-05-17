package demos;
package demos.Almaz;
import models.enums.TeacherTitle;
import models.exceptions.InvalidSupervisorException;
import models.exceptions.NonResearcherJoinProjectException;
import models.research.*;
import models.users.Student;

import database.Database;
import models.course.Course;
import models.course.Mark;
import models.enums.ManagerType;
import models.enums.TeacherTitle;
import models.exceptions.*;
import models.research.*;
import models.system.News;
import models.users.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static class NamedResearcher extends ResearcherImpl {
        private final String name;
        NamedResearcher(String name) {
            this.name = name;
        }
        @Override public String getResearcherName() { return name; }
        @Override public String toString()           { return name; }
    }
public class ResearcherDemo {

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║         UNIVERSITY SYSTEM DEMO           ║");
        System.out.println("╚══════════════════════════════════════════╝\n");

        Database db = Database.getInstance();

        Teacher aldiyar = new Teacher(1, "Aldiyar", "pass1",
                TeacherTitle.PROFESSOR, true);

        Student almaz = new Student(2, "Almaz", "pass2",
                "24B030983", "SITE", "Computer Systems and Softwere", 2);

        Manager kuanysh = new Manager(3, "Kuanysh", "pass3",
                "M001", 350000.0,
                LocalDateTime.of(2020, 9, 1, 9, 0),
                false, ManagerType.OR_MANAGER);

        Employee marat = new Employee(4, "Marat", "pass4",
                "E001", 280000.0,
                LocalDateTime.of(2021, 3, 15, 9, 0),
                true);

        Admin admin = new Admin(5, "Admin", "adminpass");

        db.addUser(aldiyar);
        db.addUser(almaz);
        db.addUser(kuanysh);
        db.addUser(marat);

        System.out.println("Created: " + aldiyar);
        System.out.println("Created: " + almaz);
        System.out.println("Created: " + kuanysh);
        System.out.println("Created: " + marat);

        System.out.println("\n── 2. Course Setup ──");

        Course oop  = new Course("CS101", "Object-Oriented Programming", 6, "CS", 1);
        Course math = new Course("MA201", "Discrete Mathematics", 5, "CS", 2);
        Course ai   = new Course("CS301", "Artificial Intelligence", 6, "CS", 3);

        db.addCourse(oop);
        db.addCourse(math);
        db.addCourse(ai);

        kuanysh.addCourse(oop);
        kuanysh.addCourse(math);
        kuanysh.addCourse(ai);

        kuanysh.assignTeacher(oop, aldiyar);
        kuanysh.assignTeacher(ai, aldiyar);
        aldiyar.addCourse(oop);
        aldiyar.addCourse(ai);
        System.out.println("Kuanysh assigned Aldiyar to OOP and AI");

        try {
            almaz.registerForCourse(oop);
            almaz.registerForCourse(math);
            almaz.registerForCourse(ai);
            System.out.println("Almaz registered for 3 courses");
        } catch (CreditLimitExceededException | AlreadyRegisteredException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }

        System.out.println("\n── 3. Grading ──");

        aldiyar.putMark(almaz, oop, new Mark(28, 27, 38));
        aldiyar.putMark(almaz, ai,  new Mark(26, 25, 36));

        try {
            almaz.receiveMark(math, new Mark(25, 24, 35));
        } catch (CourseRetakeLimitException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }

        almaz.viewMarks();
        System.out.printf("Almaz GPA: %.2f%n", almaz.getGpa());
        almaz.rateTeacher(aldiyar, 5);


        System.out.println("\n── 4. Research Papers ──");

        ResearchPaper p1 = new ResearchPaper(
                "AI Applications in Education",
                List.of("Aldiyar", "Marat"), "IEEE Education", 14,
                java.sql.Date.valueOf("2023-06-10"), 95, "10.1109/edu.2023.001");

        ResearchPaper p2 = new ResearchPaper(
                "Deep Learning for NLP",
                List.of("Aldiyar"), "Nature AI", 18,
                java.sql.Date.valueOf("2022-11-20"), 72, "10.1038/ai.2022.042");

        ResearchPaper p3 = new ResearchPaper(
                "Blockchain in University Systems",
                List.of("Marat", "Aldiyar"), "ACM Computing", 12,
                java.sql.Date.valueOf("2024-02-05"), 40, "10.1145/acm.2024.007");

        ResearchPaper p4 = new ResearchPaper(
                "Smart Campus IoT Architecture",
                List.of("Marat"), "IoT Journal", 10,
                java.sql.Date.valueOf("2023-09-15"), 28, "10.5678/iot.2023.033");

        aldiyar.addPaper(p1);
        aldiyar.addPaper(p2);
        aldiyar.addPaper(p3);

        marat.addPaper(p1);
        marat.addPaper(p3);
        marat.addPaper(p4);

        System.out.println("\nAldiyar papers (by citations):");
        aldiyar.printPapers(ResearchPaperComparators.BY_CITATIONS);

        System.out.println("\nMarat papers (by date):");
        marat.printPapers(ResearchPaperComparators.BY_DATE);


        System.out.println("\n── 5. Research Project ──");

        NamedResearcher aldiyarR = new NamedResearcher("Aldiyar");
        NamedResearcher maratR   = new NamedResearcher("Marat");

        aldiyarR.addPaper(p1); aldiyarR.addPaper(p2); aldiyarR.addPaper(p3);
        maratR.addPaper(p1);   maratR.addPaper(p3);   maratR.addPaper(p4);

        ResearchProject project = new ResearchProject("Smart University Kazakhstan");
        try {
            project.addParticipant(aldiyarR);
            project.addParticipant(maratR);
        } catch (NonResearcherJoinProjectException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
        project.addPaper(p1);
        project.addPaper(p2);
        project.addPaper(p3);

        System.out.println();
        project.printProjectInfo();

        System.out.println("\nAll project papers (by length):");
        project.printAllPapers(ResearchPaperComparators.BY_LENGTH);

        System.out.println("\n── 6. Supervisor Assignment ──");
        System.out.println("Aldiyar h-index: " + aldiyarR.getHIndex());

        try {
            almaz.setSupervisor(aldiyarR);
        } catch (InvalidSupervisorException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }

        NamedResearcher weakR = new NamedResearcher("Berik");
        weakR.addPaper(new ResearchPaper("Short Paper", List.of("Berik"),
                "Journal", 5, java.sql.Date.valueOf("2024-01-01"), 2, "10.0/test"));
        System.out.println("Berik h-index: " + weakR.getHIndex() + " → should throw exception:");
        try {
            almaz.setSupervisor(weakR);
        } catch (InvalidSupervisorException e) {
            System.out.println("Correct! Exception caught: " + e.getMessage());
        }
        System.out.println("\n── 7. News ──");
        List<News> newsList = new ArrayList<>();
        kuanysh.addNews(newsList, new News("Welcome to Spring Semester 2025!", "Spring semester has started."));
        kuanysh.addNews(newsList, new News("Research Grant Applications Open", "Apply before the deadline."));
        System.out.println("Total news: " + newsList.size());

        System.out.println("\n── 8. Admin Logs ──");
        admin.addUser(almaz);
        admin.viewLogs();

        System.out.println("\n── 9. User Profiles ──");
        aldiyar.viewProfile();
        System.out.println();
        almaz.viewProfile();
        System.out.println();
        kuanysh.viewProfile();

        System.out.println("\n── 10. Research Statistics ──");
        System.out.printf("%-10s | papers: %d | citations: %3d | h-index: %d | top: %s%n",
                "Aldiyar", aldiyarR.getPapers().size(),
                aldiyarR.calculateCitations(), aldiyarR.getHIndex(),
                aldiyarR.getTopCitedPaper().getTitle());
        System.out.printf("%-10s | papers: %d | citations: %3d | h-index: %d | top: %s%n",
                "Marat", maratR.getPapers().size(),
                maratR.calculateCitations(), maratR.getHIndex(),
                maratR.getTopCitedPaper().getTitle());

    }
}
