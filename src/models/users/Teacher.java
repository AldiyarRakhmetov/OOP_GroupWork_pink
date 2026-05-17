package models.users;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import models.enums.TeacherTitle;
import models.exceptions.NonResearcherJoinProjectException;
import models.research.ResearchPaper;
import models.research.ResearchProject;
import models.research.Researcher;
import models.research.ResearcherImpl;
import database.Database;
import models.course.Course;
import models.course.Mark;
import models.course.Lesson;

public class Teacher extends User implements Researcher {
    private TeacherTitle title;
    private boolean isResearcher;
    private List<Course> courses;
    private ResearcherImpl researcherImpl;

    public Teacher(int id, String username, String password, TeacherTitle title, boolean isResearcher){
        super(id, username, password);
        this.title = title;
        this.isResearcher = isResearcher;
        this.courses = new ArrayList<>();

        if (title == TeacherTitle.PROFESSOR) {
            if (!this.isResearcher) {
                System.out.println("Professors are always researchers! " +
                        username + " was automatically set as researcher");
            }
            this.isResearcher = true;
        }

        if (this.isResearcher) {
            this.researcherImpl = new ResearcherImpl() {
                @Override
                public String getResearcherName() {
                    return username;
                }
            };
        }
    }

    public void setTitle(TeacherTitle title) {
        this.title = title;
    }
    public TeacherTitle getTitle() {
        return title;
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

        if (!isResearcher) {
            researcherImpl = null;
        }
    }

    public boolean isResearcher(){
        return isResearcher;
    }

    public void putMark(Student student, Course course, Mark mark) {
        if (student == null || course == null || mark == null) {
            throw new IllegalArgumentException("Student, course and mark cannot be null");
        }

        if (!courses.contains(course)) {
            throw new IllegalStateException("Teacher does not teach this course");
        }

        if (!course.getTeachers().contains(this)) {
            throw new IllegalStateException("Teacher is not assigned to this course");
        }

        if (!course.getStudents().contains(student)) {
            throw new IllegalStateException("Student is not registered for this course");
        }
        if (course.getMark(student) != null) {
            throw new IllegalStateException("Mark for this student is already assigned");
        }

        try {
            student.receiveMark(course, mark);
            course.addMark(student, mark);

            Database.getInstance().log(
                    "Mark assigned to " + student.getUsername() + " for " + course.getCode(),
                    username
            );

            System.out.println("Teacher " + username + " put mark for " + student.getUsername());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void printPapers(Comparator<ResearchPaper> comparator){
        if (!isResearcher) {
            System.out.println(username + " is not a researcher");
        } else {
            researcherImpl.printPapers(comparator);
        }
    }
    public void joinProject(ResearchProject project) throws NonResearcherJoinProjectException{
        if (!isResearcher) {
            System.out.println(username + " is not a researcher");
        } else {
            researcherImpl.joinProject(project);
        }
    }
    public void addPaper(ResearchPaper paper){
        if (!isResearcher) {
            System.out.println(username + " is not a researcher");
        } else {
            researcherImpl.addPaper(paper);
        }
    }
    public ResearchPaper getTopCitedPaper(){
        if (!isResearcher) {
            System.out.println(username + " is not a researcher");
            return new ResearchPaper("", Collections.emptyList(), "", 0, null, 0, "");
        } else {
            return researcherImpl.getTopCitedPaper();
        }
    }
    public int calculateCitations(){
        if (!isResearcher) {
            System.out.println(username + " is not a researcher");
            return 0;
        } else {
            return researcherImpl.calculateCitations();
        }
    }
    public int getHIndex(){
        if (!isResearcher) {
            System.out.println(username + " is not a researcher");
            return 0;
        } else {
            return researcherImpl.getHIndex();
        }
    }
    public List<ResearchPaper> getPapers(){
        if (!isResearcher) {
            System.out.println(username + " is not a researcher");
            return Collections.emptyList();
        } else {
            return researcherImpl.getPapers();
        }
    }


    public void addCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }

        if (!courses.contains(course)) {
            courses.add(course);
        }

        if (!course.getTeachers().contains(this)) {
            course.addTeacher(this);
        }
    }

    public void updateCourses(){
        courses.removeIf(course -> !course.getTeachers().contains(this));
    }
    public void viewCourses(){
        System.out.println("Courses that are taught by teacher " + username + ":\n");
        for (Course course : courses){
            System.out.println(course);
        }
    }
    public void manageCourse(Course course, Lesson lesson) {
        if (course == null || lesson == null) {
            throw new IllegalArgumentException("Course and lesson cannot be null");
        }

        if (!courses.contains(course)) {
            throw new IllegalStateException("Teacher does not teach this course");
        }

        course.addLesson(lesson);
    }
    public void viewStudents(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }

        if (!courses.contains(course)) {
            throw new IllegalStateException("Teacher does not teach this course");
        }

        for (Student student : course.getStudents()) {
            System.out.println(student);
        }
    }

    @Override
    public void viewProfile(){
        System.out.println("Employee Profile");
        System.out.println("ID: " + id);
        System.out.println("Username: " + username);
        System.out.println("Teacher title: " + title);
        System.out.println("Is resercher: " + isResearcher);
    }

    @Override
    public String toString(){
        return "Teacher: " + username + " (id=" + id + ", title=" + title + ")";
    }
}
