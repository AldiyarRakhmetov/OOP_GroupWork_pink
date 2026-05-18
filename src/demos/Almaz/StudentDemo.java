package demos.Almaz;

import database.Database;
import models.course.Course;
import models.course.Mark;
import models.enums.TeacherTitle;
import models.exceptions.*;
import models.research.*;
import models.users.*;

import java.util.Date;
import java.util.List;

public class StudentDemo {

    static void main(String[] args) throws Exception {
        Database db = Database.getInstance();

        sep("1. Student Creation & Profile");

        Student alice = new Student(1, "Alice", "pass1", "S001", "FIT", "CS", 2);
        Student bob   = new Student(2, "Bob",   "pass2", "S002", "FIT", "CS", 4);
        db.addUser(alice);
        db.addUser(bob);

        alice.viewProfile();
        System.out.println();
        bob.viewProfile();

        // ── constructor validation ──────────────────────────────────────────
        sep("2. Constructor Validation");

        tryRun("Blank username",     () -> new Student(3, "",    "pass", "S003", "FIT", "CS", 2));
        tryRun("Blank studentId",    () -> new Student(3, "Eve", "pass", "",     "FIT", "CS", 2));
        tryRun("Year out of range",  () -> new Student(3, "Eve", "pass", "S003", "FIT", "CS", 5));
        tryRun("Negative ID",        () -> new Student(-1, "Eve","pass", "S003", "FIT", "CS", 2));

        // ── course registration ─────────────────────────────────────────────
        sep("3. Course Registration");

        Teacher prof = new Teacher(10, "DrSmith", "pass", TeacherTitle.PROFESSOR, true);
        Course oop  = new Course("CS101", "OOP",               3, "CS", 2);
        Course math = new Course("MA201", "Discrete Math",     4, "CS", 2);
        Course ai   = new Course("CS301", "Artificial Intel",  6, "CS", 3);
        Course algo = new Course("CS201", "Algorithms",        5, "CS", 2);
        Course net  = new Course("CS401", "Networking",        3, "CS", 3);

        prof.addCourse(oop);
        prof.addCourse(math);
        prof.addCourse(ai);
        prof.addCourse(algo);
        prof.addCourse(net);

        tryRun("Register null course", () -> alice.registerForCourse(null));

        tryChecked("Register for OOP",  () -> alice.registerForCourse(oop));
        tryChecked("Register for Math", () -> alice.registerForCourse(math));
        tryChecked("Register for AI",   () -> alice.registerForCourse(ai));

        System.out.println("Alice total credits: " + alice.getTotalCredits()); // all pending → 0 non-failed yet

        // duplicate registration
        sep("4. Duplicate & Credit-Limit");

        tryChecked("Duplicate OOP (should fail)", () -> alice.registerForCourse(oop));

        Course huge = new Course("BIG999", "Huge Course", 20, "CS", 1);
        tryChecked("Exceed 21 credits (should fail)", () -> alice.registerForCourse(huge));

        // ── marks ───────────────────────────────────────────────────────────
        sep("5. Mark Validation");

        tryRun("Negative first att",  () -> new Mark(-1, 20, 30));
        tryRun("First att > 30",      () -> new Mark(31, 20, 30));
        tryRun("Second att > 30",     () -> new Mark(20, 31, 30));
        tryRun("Final exam > 40",     () -> new Mark(20, 20, 41));

        sep("6. Putting Marks (Teacher)");

        prof.putMark(alice, oop,  new Mark(28, 27, 38)); // ~93  → A-
        prof.putMark(alice, math, new Mark(20, 18, 25)); // ~63  → C
        prof.putMark(alice, ai,   new Mark(15, 14, 18)); // ~47  → F

        alice.viewMarks();
        System.out.printf("Alice GPA: %.2f%n", alice.getGpa());
        System.out.println("Alice failed courses: " + alice.getFailedCoursesCount());
        System.out.println("Alice total (passing) credits: " + alice.getTotalCredits());

        sep("7. Duplicate Mark (should fail)");
        tryRun("Put mark twice on OOP", () -> prof.putMark(alice, oop, new Mark(10, 10, 10)));

        // ── retake ──────────────────────────────────────────────────────────
        sep("8. Retake Passed Course (should fail) vs Retake Failed Course (allowed)");

        // OOP was passed → retake blocked
        tryChecked("Re-register passed OOP (should fail)", () -> alice.registerForCourse(oop));

        // AI was failed → retake allowed
        tryChecked("Re-register failed AI (should succeed)", () -> alice.registerForCourse(ai));
        System.out.println("Alice is retaking AI — no mark yet:");
        alice.viewMarks();

        // ── fail limit ──────────────────────────────────────────────────────
        sep("9. 3-Fail Limit on Registration");

        Student carl = new Student(3, "Carl", "pass3", "S003", "FIT", "CS", 2);
        Course c1 = new Course("X1", "CourseX1", 3, "CS", 1);
        Course c2 = new Course("X2", "CourseX2", 3, "CS", 1);
        Course c3 = new Course("X3", "CourseX3", 3, "CS", 1);
        Course c4 = new Course("X4", "CourseX4", 3, "CS", 1);
        prof.addCourse(c1); prof.addCourse(c2); prof.addCourse(c3); prof.addCourse(c4);

        tryChecked("Carl registers X1", () -> carl.registerForCourse(c1));
        tryChecked("Carl registers X2", () -> carl.registerForCourse(c2));
        tryChecked("Carl registers X3", () -> carl.registerForCourse(c3));

        prof.putMark(carl, c1, new Mark(0, 0, 0)); // F
        prof.putMark(carl, c2, new Mark(0, 0, 0)); // F
        prof.putMark(carl, c3, new Mark(0, 0, 0)); // F

        System.out.println("Carl failed courses: " + carl.getFailedCoursesCount());
        tryChecked("Carl registers X4 after 3 fails (should fail)", () -> carl.registerForCourse(c4));

        // ── teacher authorization ────────────────────────────────────────────
        sep("10. Teacher Puts Mark for Foreign Course (should fail)");

        Teacher otherTeacher = new Teacher(20, "Stranger", "pass", TeacherTitle.LECTURER, false);
        tryRun("Stranger marks Alice's OOP",
                () -> otherTeacher.putMark(alice, oop, new Mark(10, 10, 10)));

        // ── teacher marks non-enrolled student ──────────────────────────────
        sep("11. Mark Non-Enrolled Student (should fail)");

        Student outsider = new Student(99, "Ghost", "pass", "S099", "FIT", "CS", 2);
        tryRun("Prof marks Ghost on OOP (not enrolled)",
                () -> prof.putMark(outsider, oop, new Mark(25, 25, 35)));

        // ── teacher rating ───────────────────────────────────────────────────
        sep("12. Teacher Rating");

        alice.rateTeacher(prof, 5);
        tryRun("Invalid rating 0",  () -> alice.rateTeacher(prof, 0));
        tryRun("Invalid rating 6",  () -> alice.rateTeacher(prof, 6));
        tryRun("Null teacher",      () -> alice.rateTeacher(null, 3));

        // ── transcript ───────────────────────────────────────────────────────
        sep("13. Transcript");

        alice.getTranscript().printTranscript();

        // ── supervisor (4th year only) ────────────────────────────────────────
        sep("14. Supervisor Assignment");

        // Alice is 2nd year → must fail
        tryChecked("Alice (yr 2) gets supervisor (should fail)",
                () -> alice.setSupervisor(prof));

        // Bob is 4th year
        ResearchPaper p1 = new ResearchPaper("AI Paper",   List.of("DrSmith"), "IEEE", 10, new Date(), 80, "doi-1");
        ResearchPaper p2 = new ResearchPaper("ML Paper",   List.of("DrSmith"), "ACM",  12, new Date(), 70, "doi-2");
        ResearchPaper p3 = new ResearchPaper("NLP Paper",  List.of("DrSmith"), "NIPS", 15, new Date(), 60, "doi-3");
        prof.addPaper(p1); prof.addPaper(p2); prof.addPaper(p3);
        System.out.println("DrSmith h-index: " + prof.getHIndex());

        tryChecked("Bob (yr 4) gets valid supervisor", () -> bob.setSupervisor(prof));
        bob.viewProfile();

        // weak supervisor (h-index < 3)
        Teacher weakProf = new Teacher(21, "Junior", "pass", TeacherTitle.LECTURER, true);
        weakProf.addPaper(new ResearchPaper("One Paper", List.of("Junior"), "J", 5, new Date(), 1, "doi-w"));
        System.out.println("Junior h-index: " + weakProf.getHIndex());
        tryChecked("Bob gets weak supervisor (should fail)", () -> bob.setSupervisor(weakProf));

        tryRun("Null supervisor", () -> bob.setSupervisor(null));

        // ── researcher functionality ───────────────────────────────────────────
        sep("15. Student as Researcher");

        System.out.println("Alice isResearcher: " + alice.isResearcher());
        alice.toggleResearcher();
        System.out.println("After toggle: " + alice.isResearcher());

        ResearchPaper sp1 = new ResearchPaper("Student Research", List.of("Alice"), "Journal", 8, new Date(), 5, "doi-s1");
        ResearchPaper sp2 = new ResearchPaper("Another Study",    List.of("Alice"), "Journal", 6, new Date(), 3, "doi-s2");
        alice.addPaper(sp1);
        alice.addPaper(sp2);

        alice.printPapers(ResearchPaperComparators.BY_CITATIONS);
        System.out.println("Alice h-index: " + alice.getHIndex());
        System.out.println("Alice citations: " + alice.calculateCitations());

        ResearchProject project = new ResearchProject("Student AI Initiative");
        try {
            alice.joinProject(project);
        } catch (NonResearcherJoinProjectException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
        project.printProjectInfo();

        // toggle back off
        alice.toggleResearcher();
        System.out.println("Alice isResearcher after toggle off: " + alice.isResearcher());
        tryRun("Add paper when not researcher", () -> alice.addPaper(sp1));

        // ── final profile ─────────────────────────────────────────────────────
        sep("16. Final Profiles");
        alice.viewProfile();
        System.out.println();
        bob.viewProfile();
    }

    // ── helpers ───────────────────────────────────────────────────────────────

    @FunctionalInterface
    interface CheckedAction { void run() throws Exception; }

    /** Expects an exception — prints PASSED if one is thrown, FAILED otherwise */
    private static void tryRun(String label, CheckedAction action) {
        System.out.print("[" + label + "] → ");
        try {
            action.run();
            System.out.println("FAILED (no exception thrown)");
        } catch (Exception e) {
            System.out.println("PASSED (" + e.getClass().getSimpleName() + ": " + e.getMessage() + ")");
        }
    }

    /** Runs normally — prints OK or shows the exception */
    private static void tryChecked(String label, CheckedAction action) {
        System.out.print("[" + label + "] → ");
        try {
            action.run();
            System.out.println("OK");
        } catch (Exception e) {
            System.out.println("BLOCKED (" + e.getClass().getSimpleName() + ": " + e.getMessage() + ")");
        }
    }

    private static void sep(String title) {
        System.out.println("\n══ " + title + " ══");
    }
}