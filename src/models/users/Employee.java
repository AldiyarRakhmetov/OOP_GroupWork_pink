package models.users;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

import models.exceptions.NonResearcherJoinProjectException;
import models.research.ResearchPaper;
import models.research.ResearchProject;
import models.research.ResearcherImpl;

import java.util.Collections;

public class Employee extends User {
    private String employeeId;
    private double salary;
    private LocalDateTime hireDate;
    private Vector<String> messages = new Vector<>();
    private Vector<String> complaints = new Vector<>();
    private ResearcherImpl researcherImpl; //research implimintation if is a research
    private boolean isResearcher;

    //init
    public Employee(int id, String username, String password, String employeeId, double salary,
        LocalDateTime hireDate, boolean isResearcher){
        super(id, username, password);
        this.employeeId = employeeId;
        this.salary = salary;
        this.hireDate = hireDate;
        this.isResearcher = isResearcher;
    }

    public String getEmployeeId() { //getters and setters
        return employeeId;
    }
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public double getSalary() {
        return salary;
    }
    public void setSalary(double salary) {
        this.salary = salary;
    }

    public LocalDateTime getHireDate() {
        return hireDate;
    }
    public void setHireDate(LocalDateTime hireDate) {
        this.hireDate = hireDate;
    }

    public void toggleResearcher(){
        isResearcher = !isResearcher;
    }
    public boolean isResearcher(){
        return isResearcher;
    }

    public void addMessage(String message){
        messages.add(message);
    }
    public void addComplaint(String complaint){
        complaints.add(complaint);
    }
    public Vector<String> getMessages() {
        return messages;
    }
    public Vector<String> getComplaints() {
        return complaints;
    }
    public void clearMessages(){
        messages.clear();
    }


    public void sendMessage(Employee reciever, String text){
        reciever.addMessage("FROM " + super.getUsername() + ": " + text);
    }
    public void sendComplaint(Employee reciever, String text){
        reciever.addComplaint("FROM " + super.getUsername() + ": " + text);
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
            researcherImpl.joinProject(project);;
        }
    }
    public void addPaper(ResearchPaper paper){
        if (!isResearcher) {
            System.out.println(username + " is not a researcher");
        } else {
            researcherImpl.addPaper(paper);;
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
    List<ResearchPaper> getPapers(){
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
        System.out.println("Employee ID: " + employeeId);
        System.out.println("Salary: " + salary);
        System.out.println("Hire date: " + hireDate);
        System.out.println("Compliant count: " + complaints.size());
        System.out.println("Is resercher: " + isResearcher);
    }

    @Override
    public String toString(){
        return "Employee: " + username + " (id=" + id + ", employee id=" + employeeId + ")";
    }
}
