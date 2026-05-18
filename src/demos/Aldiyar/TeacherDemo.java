/*package demos.Aldiyar;

import java.util.Comparator;
import java.util.Date;

import models.users.*;
import models.enums.TeacherTitle;
import models.course.*;
import models.research.*;

public class TeacherDemo {
    public static void main(String[] args) {
        //teachers
        Teacher teacher = new Teacher(
                1,
                "DrSmith",
                "pass",
                TeacherTitle.LECTURER,
                false
        );

        teacher.viewProfile();
        System.out.println(teacher);

        //toggleResearcher
        teacher.toggleResearcher();
        System.out.println("Is researcher: " + teacher.isResearcher());

        //Courses
        Course course1 = new Course("CS101", "Intro to CS", 3, "CS", 1);
        Course course2 = new Course("CS202", "Data Structures", 4, "CS", 2);

        teacher.addCourse(course1);
        teacher.addCourse(course2);

        teacher.viewCourses();

        //manageCourse
        teacher.manageCourse(course1, 5); // change credits
        teacher.manageCourse(course2, 4, 2); // credits + year
        teacher.manageCourse(course1, "Software Engineering"); // change major

        //studnets
        Student student = new Student(
                10,
                "Alice",
                "pass",
                "S001",
                "FIT",
                "CS",
                2
        );

        //registering
        try {
            student.registerForCourse(course1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        //viewStudents
        teacher.viewStudents(course1);

        //putMark
        Mark mark = new Mark(30, 30, 35); // assumes constructor exists

        teacher.putMark(student, course1, mark); // full version

        teacher.putMark(course1, student, mark); // stream version

        student.viewMarks();

        //research papers
        ResearchPaper p1 = new ResearchPaper(
                "AI Paper",
                java.util.List.of("DrSmith"),
                "Journal A",
                10,
                new Date(),
                50,
                "doi-1"
        );

        ResearchPaper p2 = new ResearchPaper(
                "ML Paper",
                java.util.List.of("DrSmith"),
                "Journal B",
                12,
                new Date(),
                30,
                "doi-2"
        );

        //addPapers
        teacher.addPaper(p1);
        teacher.addPaper(p2);

        //printPapers
        teacher.printPapers(Comparator.naturalOrder());

        //papers methods
        System.out.println("Top paper:");
        teacher.getTopCitedPaper().printInfo();

        System.out.println("Total citations: " + teacher.calculateCitations());
        System.out.println("H-index: " + teacher.getHIndex());

        System.out.println("All papers: " + teacher.getPapers());

        //research project
        ResearchProject project = new ResearchProject("AI Project");

        try {
            teacher.joinProject(project);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        //updateCourses
        course2.removeTeacher(teacher);
        teacher.updateCourses();

        System.out.println("\nAfter updateCourses():");
        teacher.viewCourses();

        //setTitle
        teacher.setTitle(TeacherTitle.PROFESSOR); // auto researcher
        System.out.println("New title: " + teacher.getTitle());
        System.out.println("Is researcher: " + teacher.isResearcher());

        //viewProfile
        teacher.viewProfile();
    }
}*/