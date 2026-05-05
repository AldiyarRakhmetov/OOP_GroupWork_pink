package models.users;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import models.course.*;
import models.enums.TeacherTitle;
import models.exceptions.NonResearcherJoinProjectException;
import models.exceptions.StudentNotFoundException;
import models.research.ResearchPaper;
import models.research.ResearchProject;
import models.research.ResearcherImpl;
import database.Database;
import models.course.Course;
import models.course.Mark;

public class Teacher extends User {
    private TeacherTitle title;
    private boolean isResearcher;
    private List<Course> courses;
    private ResearcherImpl researcherImpl;

    public Teacher(int id, String username, String password, TeacherTitle title, boolean isResearcher){
        super(id, username, password);
        this.title = title;
        this.isResearcher = isResearcher;

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

    public void toggleResearcher(){
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

    public boolean isResearcher(){
        return isResearcher;
    }

    public void putMark(Student student, Course course, Mark mark) {

        try {
            course.addMark(student, mark);
            student.receiveMark(course, mark);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
        Database.getInstance().log(
                "Mark assigned to " + student.getUsername(),
                username
        );

        System.out.println("Teacher " + username + " put mark for " + student.getUsername());
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


    public void addCourse(Course course){
        courses.add(course);
        course.addTeacher(this);
    }
    public void updateCourses(){
        for (Course course : courses){
            List<Teacher> courseTeachers = course.getTeachers();
            if (!courseTeachers.contains(this)){
                courses.remove(course);
            }
        }
    }
    public void viewCourses(){
        System.out.println("Courses that are taught by teacher " + username + ":\n");
        for (Course course : courses){
            System.out.println(course);
        }
    }
    public void manageCourse(Course course, int credits){
        courses.stream().filter(obj -> obj.equals(course)).findFirst()
        .ifPresent(obj -> obj.setCredits(credits));
    }
    public void manageCourse(Course course, int credits, int yearOfStudy){
        courses.stream().filter(obj -> obj.equals(course)).findFirst()
        .ifPresent(obj -> {obj.setCredits(credits); obj.setYearOfStudy(yearOfStudy);});
    }
    public void manageCourse(Course course, String major){
        courses.stream().filter(obj -> obj.equals(course)).findFirst()
        .ifPresent(obj -> obj.setMajor(major));
    }
    public void viewStudents(Course course){
        courses.stream().filter(obj -> obj.equals(course)).findFirst()
        .ifPresent(obj -> obj.getStudents());
    }
    public void putMark(Course course, Student student, Mark mark){
        courses.stream().filter(obj -> obj.equals(course)).findFirst()
        .ifPresent(obj -> {
                            try {
                                obj.addMark(student, mark); // Potential NumberFormatException
                            } catch (StudentNotFoundException e) {
                                System.err.println("Can't give a mark to non-existant student!");
                            }
                        });
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
