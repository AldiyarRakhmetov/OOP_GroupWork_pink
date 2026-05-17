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
    private Researcher supervisor;
    private boolean isResearcher;
    private ResearcherImpl researcherImpl;

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
        this.courseMarks = new HashMap<>();
        this.transcript = new Transcript();
        this.isResearcher = false;
        this.researcherImpl = null;
    }

    public Student(int id, String username, String password,
                   String studentId, String school, String major, int yearOfStudy, boolean isResearcher) {
        this(id, username, password, studentId, school, major, yearOfStudy);
        this.isResearcher = isResearcher;
        if (isResearcher) {
            this.researcherImpl = new ResearcherImpl() {
                @Override
                public String getResearcherName() {
                    return username;
                }
            };
        }
    }

    // ── Course methods ─────────────────────────────────────────────────────

    public void registerForCourse(Course course) throws CreditLimitExceededException, AlreadyRegisteredException {
        if (courseMarks.containsKey(course)) {
            throw new AlreadyRegisteredException();
        }
        if (getTotalCredits() + course.getCredits() > 21) {
            throw new CreditLimitExceededException(
                    "Cannot register: credit limit of 21 exceeded. Current: " + getTotalCredits()
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
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
        System.out.printf("%s rated teacher %s: %d/5%n",
                username, teacher.getUsername(), rate);
    }

    public void receiveMark(Course course, Mark mark) throws CourseRetakeLimitException {
        if (course == null || mark == null) {
            throw new IllegalArgumentException("Course and mark cannot be null");
        }
        if (!courseMarks.containsKey(course)) {
            throw new IllegalStateException("Student is not registered for: " + course.getTitle());
        }
        if (courseMarks.get(course) != null) {
            throw new IllegalStateException("Mark for this course is already assigned");
        }
        if (getFailedCoursesCount() >= 3) {
            throw new CourseRetakeLimitException(
                    username + " has already failed 3 courses. Cannot fail more."
            );
        }
        courseMarks.put(course, mark);
        transcript.addRecord(mark);
        updateGPA();
    }

    public void setSupervisor(Researcher supervisor) throws InvalidSupervisorException {
        if (supervisor == null) {
            throw new InvalidSupervisorException("Supervisor cannot be null");
        }
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

    // ── Researcher support ─────────────────────────────────────────────────

    public boolean isResearcher() {
        return isResearcher;
    }

    public void toggleResearcher() {
        isResearcher = !isResearcher;
        if (isResearcher && researcherImpl == null) {
            researcherImpl = new ResearcherImpl() {
                @Override
                public String getResearcherName() {
                    return username;
                }
            };
        }
    }

    public void printPapers(Comparator<ResearchPaper> comparator) {
        if (!isResearcher) {
            System.out.println(username + " is not a researcher");
        } else {
            researcherImpl.printPapers(comparator);
        }
    }

    public void joinProject(ResearchProject project) throws NonResearcherJoinProjectException {
        if (!isResearcher) {
            System.out.println(username + " is not a researcher");
        } else {
            researcherImpl.joinProject(project);
        }
    }

    public void addPaper(ResearchPaper paper) {
        if (!isResearcher) {
            System.out.println(username + " is not a researcher");
        } else {
            researcherImpl.addPaper(paper);
        }
    }

    public ResearchPaper getTopCitedPaper() {
        if (!isResearcher) {
            System.out.println(username + " is not a researcher");
            return new ResearchPaper("", Collections.emptyList(), "", 0, null, 0, "");
        } else {
            return researcherImpl.getTopCitedPaper();
        }
    }

    public int calculateCitations() {
        if (!isResearcher) {
            System.out.println(username + " is not a researcher");
            return 0;
        } else {
            return researcherImpl.calculateCitations();
        }
    }

    public int getHIndex() {
        if (!isResearcher) {
            System.out.println(username + " is not a researcher");
            return 0;
        } else {
            return researcherImpl.getHIndex();
        }
    }

    public List<ResearchPaper> getPapers() {
        if (!isResearcher) {
            System.out.println(username + " is not a researcher");
            return Collections.emptyList();
        } else {
            return researcherImpl.getPapers();
        }
    }

    // ── assignMentor overloads ─────────────────────────────────────────────

    public void assignMentor(Teacher teacher) {
        if (teacher.isResearcher()) {
            System.out.println("Mentor (Teacher) " + teacher.getUsername() + " assigned to " + username);
        } else {
            System.out.println("Cannot assign mentor: " + teacher.getUsername() + " is not a researcher");
        }
    }

    public void assignMentor(Student student) {
        if (student.isResearcher()) {
            System.out.println("Mentor (Student) " + student.getUsername() + " assigned to " + username);
        } else {
            System.out.println("Cannot assign mentor: " + student.getUsername() + " is not a researcher");
        }
    }

    public void assignMentor(Employee employee) {
        if (employee.isResearcher()) {
            System.out.println("Mentor (Employee) " + employee.getUsername() + " assigned to " + username);
        } else {
            System.out.println("Cannot assign mentor: " + employee.getUsername() + " is not a researcher");
        }
    }

    // ── Getters ────────────────────────────────────────────────────────────

    public String getStudentId()       { return studentId; }
    public String getSchool()          { return school; }
    public String getMajor()           { return major; }
    public int getYearOfStudy()        { return yearOfStudy; }
    public double getGpa()             { return gpa; }
    public Researcher getSupervisor()  { return supervisor; }

    public int getTotalCredits() {
        return courseMarks.entrySet().stream()
                .filter(e -> e.getValue() != null && !e.getValue().calculateLetter().equals("F"))
                .mapToInt(e -> e.getKey().getCredits())
                .sum();
    }

    public int getFailedCoursesCount() {
        return (int) courseMarks.values().stream()
                .filter(mark -> mark != null && mark.calculateLetter().equals("F"))
                .count();
    }

    // ── Profile ────────────────────────────────────────────────────────────

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
        System.out.println("Credits: " + getTotalCredits() + " / 21");
        System.out.println("Failed: " + getFailedCoursesCount() + " / 3");
        System.out.println("Is Researcher: " + isResearcher);
        System.out.println("Supervisor: " +
                (supervisor instanceof User
                        ? ((User) supervisor).getUsername()
                        : "None"));
    }

    @Override
    public String toString() {
        return "Student: " + username + " (id=" + id + ", gpa=" + gpa + ")";
    }
}