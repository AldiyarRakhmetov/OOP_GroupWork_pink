package models.users;

import java.util.*;
import models.exceptions.*;
import models.research.*;
import models.transcript.*;
import models.course.Course;
import models.course.Mark;

public class Student extends User {
    private String studentId;
    private String school;
    private String major;
    private int yearOfStudy;
    private double gpa;
    private int totalCredits;
    private int failedCoursesCount;
    private Researcher supervisor;

    private Map<Course, Mark> courseMarks;
    private Transcript transcript;

    public Student(int id, String username, String password,
                   String studentId, String school, String major, int yearOfStudy) {
        super(id, username, password);
        this.studentId = studentId;
        this.school = school;
        this.major = major;
        this.yearOfStudy = yearOfStudy;
        this.gpa = 0.0;
        this.totalCredits = 0;
        this.failedCoursesCount = 0;
        this.courseMarks = new HashMap<>();
        this.transcript = new Transcript();
    }

    public void registerForCourse(Course course) throws CreditLimitExceededException, AlreadyRegisteredException  {
        if (courseMarks.containsKey(course)) {
            throw new AlreadyRegisteredException();
        }
        if (totalCredits + course.getCredits() > 21) {
            throw new CreditLimitExceededException(
                    "Cannot register: credit limit of 21 exceeded. Current: " + totalCredits
            );
        }
        courseMarks.put(course, null);
        course.addStudent(this);
        System.out.println(username + " registered for course: " + course.getTitle());
    }

    public void viewMarks() {
        if (courseMarks.isEmpty()) {
            System.out.println("No courses registered.");
            return;
        }
        System.out.println("Marks for " + username);
        for (Map.Entry<Course, Mark> entry : courseMarks.entrySet()) {
            Course course = entry.getKey();
            Mark mark     = entry.getValue();
            if (mark == null) {
                System.out.println(course.getTitle() + ": No mark yet");
            } else {
                System.out.printf("%s: %s (%.2f)%n",
                        course.getTitle(),
                        mark.calculateLetter(),
                        mark.calculateTotal()
                );
            }
        }
    }

    public List<Course> getCourses() {
        return new ArrayList<>(courseMarks.keySet());
    }

    public Transcript getTranscript() {
        return transcript;
    }

    public void rateTeacher(Teacher teacher, int rate) {
        if (rate < 1 || rate > 5) {
            System.out.println("Rating must be between 1 and 5.");
            return;
        }
        System.out.printf("%s rated teacher %s: %d/5%n",
                username, teacher.getUsername(), rate);
    }

    public void receiveMark(Course course, Mark mark) throws CourseRetakeLimitException {
        if (!courseMarks.containsKey(course)) {
            System.out.println("Student is not registered for: " + course.getTitle());
            return;
        }
        if (failedCoursesCount >= 3) {
            throw new CourseRetakeLimitException(
                    username + " has already failed 3 courses. Cannot fail more."
            );
        }
        courseMarks.put(course, mark);
        transcript.addRecord(mark);
        updateGPA();

        if (mark.calculateLetter().equals("F")) {
            failedCoursesCount++;
        } else {
            totalCredits += course.getCredits();
        }
    }

    public void setSupervisor(Researcher supervisor) throws InvalidSupervisorException {
        if (yearOfStudy != 4) {
            throw new InvalidSupervisorException(
                    "Only 4th year students can have a research supervisor."
            );
        }
        if (supervisor.getHIndex() < 3) {
            throw new InvalidSupervisorException(
                    "Supervisor h-index must be >= 3. Current: " + supervisor.getHIndex()
            );
        }
        this.supervisor = supervisor;
        if (supervisor instanceof User) {
            System.out.println("Supervisor " +
                    ((User) supervisor).getUsername() +
                    " assigned to " + username);
        } else {
            System.out.println("Supervisor assigned to " + username);
        }
    }

    private void updateGPA() {
        this.gpa = transcript.calculateGPA();
    }

    public String getStudentId()       { return studentId; }
    public String getSchool()          { return school; }
    public String getMajor()           { return major; }
    public int getYearOfStudy()        { return yearOfStudy; }
    public double getGpa()             { return gpa; }
    public int getTotalCredits()       { return totalCredits; }
    public int getFailedCoursesCount() { return failedCoursesCount; }
    public Researcher getSupervisor()  { return supervisor; }

    @Override
    public void viewProfile() {
        System.out.println("Student Profile");
        System.out.println("ID: " + id);
        System.out.println("Username: " + username);
        System.out.println("Student ID: " + studentId);
        System.out.println("School: " + school);
        System.out.println("Major: " + major);
        System.out.println("Year: " + yearOfStudy);
        System.out.printf ("GPA: %.2f%n", gpa);
        System.out.println("Credits: " + totalCredits + " / 21");
        System.out.println("Failed: " + failedCoursesCount + " / 3");
        System.out.println("Supervisor: " +
                (supervisor instanceof User
                        ? ((User) supervisor).getUsername()
                        : "Assigned"));
    }

    @Override
    public String toString() {
        return "Student: " + username + " (id=" + id + ", gpa=" + gpa + ")";
    }
}