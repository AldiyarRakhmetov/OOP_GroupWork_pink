package models.users;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import models.course.*;
import models.enums.TeacherTitle;
import models.exceptions.NonResearcherJoinProjectException;
import models.research.ResearchPaper;
import models.research.ResearchProject;
import models.research.ResearcherImpl;
import database.Database;

public class Teacher extends User {
    private TeacherTitle title;
    private boolean isResearcher;

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
