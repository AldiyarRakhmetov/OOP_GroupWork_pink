package demos.Kuanysh;
import models.course.*;
import models.users.*;
import models.enums.*;
import models.exceptions.*;
import models.research.*;

public class TestLogicDemo{
    public static void main(String[] args) {
        testDuplicateRegistration();
        testCreditLimit();
        testInvalidMark();
        testTeacherNotAssigned();
        //testInvalidSupervisor();
        testNonResearcherProject();
    }

    private static void testDuplicateRegistration() {
        System.out.println("\nTEST 1: Duplicate registration");

        try {
            Student s = new Student(1, "student", "123", "S1", "FIT", "CS", 2);
            Course c = new Course("CS101", "OOP", 3, "CS", 2);

            s.registerForCourse(c);
            s.registerForCourse(c);

            System.out.println("FAILED: duplicate allowed");
        } catch (AlreadyRegisteredException e) {
            System.out.println("PASSED: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("FAILED: " + e.getMessage());
        }
    }

    private static void testCreditLimit() {
        System.out.println("\nTEST 2: Credit limit");

        try {
            Student s = new Student(2, "student2", "123", "S2", "FIT", "CS", 2);
            Course big = new Course("BIG101", "Big Course", 25, "CS", 2);

            s.registerForCourse(big);

            System.out.println("FAILED: credit limit ignored");
        } catch (CreditLimitExceededException e) {
            System.out.println("PASSED: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("FAILED: " + e.getMessage());
        }
    }

    private static void testInvalidMark() {
        System.out.println("\nTEST 3: Invalid mark");

        try {
            Mark mark = new Mark(-10, 100, 90);

            System.out.println("FAILED: invalid mark accepted: " + mark);
        } catch (InvalidMarkException e) {
            System.out.println("PASSED: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("FAILED: unexpected error: " + e.getMessage());
        }
    }

    private static void testTeacherNotAssigned() {
        System.out.println("\nTEST 4: Teacher not assigned");

        try {
            Student s = new Student(3, "student3", "123", "S3", "FIT", "CS", 2);
            Teacher t = new Teacher(4, "teacher", "123", TeacherTitle.TUTOR, false);
            Course c = new Course("CS102", "Algorithms", 3, "CS", 2);

            s.registerForCourse(c);

            Mark mark = new Mark(25, 25, 35);
            t.putMark(s, c, mark);

            System.out.println("CHECK: if teacher was not assigned, system should reject this");
        } catch (Exception e) {
            System.out.println("PASSED/ERROR HANDLED: " + e.getMessage());
        }
    }



    private static void testNonResearcherProject() {
        System.out.println("\nTEST 6: Non researcher joins project");

        try {
            ResearchProject project = new ResearchProject("AI Project");
            Object randomPerson = new Object();

            project.addParticipant(randomPerson);

            System.out.println("FAILED: non-researcher joined project");
        } catch (NonResearcherJoinProjectException e) {
            System.out.println("PASSED: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("FAILED: " + e.getMessage());
        }
    }
}


