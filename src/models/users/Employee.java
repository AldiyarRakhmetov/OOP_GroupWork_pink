package models.users;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;
import java.util.Collections;

import models.exceptions.NonResearcherJoinProjectException;
import models.research.ResearchPaper;
import models.research.ResearchProject;
import models.research.ResearcherImpl;

public class Employee extends User {
    private String employeeId;
    private double salary;
    private LocalDateTime hireDate;
    private Vector<String> messages = new Vector<>();
    private Vector<String> complaints = new Vector<>();
    private ResearcherImpl researcherImpl;
    private boolean isResearcher;

    public Employee(int id, String username, String password, String employeeId, double salary,
                    LocalDateTime hireDate, boolean isResearcher) {
        super(id, username, password);
        if (employeeId == null || employeeId.isBlank())
            throw new IllegalArgumentException("Employee ID cannot be empty");
        if (salary < 0) throw new IllegalArgumentException("Salary cannot be negative");
        if (hireDate == null) throw new IllegalArgumentException("Hire date cannot be null");
        this.employeeId = employeeId;
        this.salary = salary;
        this.hireDate = hireDate;
        this.isResearcher = isResearcher;
        if (isResearcher) {
            researcherImpl = new ResearcherImpl();
            researcherImpl.setName(username);
        }
    }

    public String getEmployeeId()              { return employeeId; }
    public void setEmployeeId(String id)       { if (id == null || id.isBlank()) throw new IllegalArgumentException("Employee ID cannot be empty"); this.employeeId = id; }
    public double getSalary()                  { return salary; }
    public void setSalary(double salary)       { if (salary < 0) throw new IllegalArgumentException("Salary cannot be negative"); this.salary = salary; }
    public LocalDateTime getHireDate()         { return hireDate; }
    public void setHireDate(LocalDateTime d)   { if (d == null) throw new IllegalArgumentException("Hire date cannot be null"); this.hireDate = d; }

    public void toggleResearcher() {
        isResearcher = !isResearcher;
        if (isResearcher && researcherImpl == null) {
            researcherImpl = new ResearcherImpl();
            researcherImpl.setName(username);
        }
    }

    public boolean isResearcher() { return isResearcher; }

    public void addMessage(String message)   { if (message != null) messages.add(message); }
    public void addComplaint(String c)       { if (c != null) complaints.add(c); }
    public Vector<String> getMessages()      { return messages; }
    public Vector<String> getComplaints()    { return complaints; }
    public void clearMessages()              { messages.clear(); }

    public void sendMessage(Employee receiver, String text) {
        if (receiver == null) throw new IllegalArgumentException("Receiver cannot be null");
        if (text == null || text.isBlank()) throw new IllegalArgumentException("Message cannot be empty");
        receiver.addMessage("FROM " + username + ": " + text);
    }

    public void sendComplaint(Employee receiver, String text) {
        if (receiver == null) throw new IllegalArgumentException("Receiver cannot be null");
        if (text == null || text.isBlank()) throw new IllegalArgumentException("Complaint cannot be empty");
        receiver.addComplaint("FROM " + username + ": " + text);
    }

    private ResearcherImpl requireResearcher() {
        if (!isResearcher || researcherImpl == null)
            throw new IllegalStateException(username + " is not a researcher");
        return researcherImpl;
    }

    public void printPapers(Comparator<ResearchPaper> comparator) {
        if (!isResearcher) { System.out.println(username + " is not a researcher"); return; }
        researcherImpl.printPapers(comparator);
    }

    public void joinProject(ResearchProject project) throws NonResearcherJoinProjectException {
        if (!isResearcher) { System.out.println(username + " is not a researcher"); return; }
        researcherImpl.joinProject(project);
    }

    public void addPaper(ResearchPaper paper) {
        requireResearcher().addPaper(paper);
    }

    public ResearchPaper getTopCitedPaper() {
        if (!isResearcher) { System.out.println(username + " is not a researcher"); return null; }
        return researcherImpl.getTopCitedPaper();
    }

    public int calculateCitations() {
        if (!isResearcher) return 0;
        return researcherImpl.calculateCitations();
    }

    public int getHIndex() {
        if (!isResearcher) return 0;
        return researcherImpl.getHIndex();
    }

    public List<ResearchPaper> getPapers() {
        if (!isResearcher) return Collections.emptyList();
        return researcherImpl.getPapers();
    }

    @Override
    public void viewProfile() {
        System.out.println("Employee Profile");
        System.out.println("ID: " + id);
        System.out.println("Username: " + username);
        System.out.println("Employee ID: " + employeeId);
        System.out.println("Salary: " + salary);
        System.out.println("Hire date: " + hireDate);
        System.out.println("Complaint count: " + complaints.size());
        System.out.println("Is researcher: " + isResearcher);
    }

    @Override
    public String toString() {
        return "Employee: " + username + " (id=" + id + ", employee id=" + employeeId + ")";
    }
}
