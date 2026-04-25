package models.users;

import models.course.Course;
import models.course.Mark;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Student extends User {
    private String studentId;
    private String school;
    private String major;
    private int yearOfStudy;
    private double gpa;
    private int totalCredits;
    private int failedCoursesCount;
    private Researcher supervisor; // 0..1 supervisor

    private Map<Course, Mark> courseMarks;
    private Transcript transcript;

    public Student(String username, String password,
                   String studentId, String school, String major, int yearOfStudy) {
        super(username, password);
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

    public void registerForCourse(Course course) {
        if (courseMarks.containsKey(course)) {
            System.out.println("Already registered for: " + course.getTitle());
            return;
        }
        courseMarks.put(course, null);
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

    public Transcript getTranscript() {
        return transcript;
    }

    public void rateTeacher(Teacher teacher, int rate) {
        if (rate < 1 || rate > 5) {
            System.out.println("Rating must be between 1 and 5.");
            return;
        }
        System.out.printf("%s rated teacher %s: %d/5%n", username, teacher.getUsername(), rate);
    }

    public void receiveMark(Course course, Mark mark) {
        if (!courseMarks.containsKey(course)) {
            System.out.println("Student is not registered for: " + course.getTitle());
            return;
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

    private void updateGPA() {
        this.gpa = transcript.calculateGPA();
    }

    public String getStudentId() { return studentId; }
    public String getSchool() { return school; }
    public String getMajor() { return major; }
    public int getYearOfStudy() { return yearOfStudy; }
    public double getGpa() { return gpa; }
    public int getTotalCredits() { return totalCredits; }
    public int getFailedCoursesCount() { return failedCoursesCount; }
    public Researcher getSupervisor() { return supervisor; }

    public void setSupervisor(Researcher supervisor) {
        this.supervisor = supervisor;
    }
    @Override
    public void viewProfile() {
        System.out.println("Student Profile");
        System.out.println("ID: " + studentId);
        System.out.println("Username: " + username);
        System.out.println("School: " + school);
        System.out.println("Major: " + major);
        System.out.println("Year: " + yearOfStudy);
        System.out.printf ("GPA: %.2f%n", gpa);
        System.out.println("Credits: " + totalCredits);
        System.out.println("Failed: " + failedCoursesCount);
        System.out.println("Supervisor: " + (supervisor != null ? supervisor.getUsername() : "None"));
    }
}