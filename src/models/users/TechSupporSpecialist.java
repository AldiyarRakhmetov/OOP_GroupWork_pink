package models.users;

import java.time.LocalDateTime;
import java.util.Vector;

public class TechSupporSpecialist extends Employee {
    private Vector<String> orders;

    public TechSupporSpecialist(int id, String username, String password, String employeeId, double salary,
        LocalDateTime hireDate){
            super(id, username, password, employeeId, salary, hireDate, false);
    }
    public TechSupporSpecialist(int id, String username, String password, String employeeId, double salary){
            super(id, username, password, employeeId, salary, LocalDateTime.now(), false);
    }
    
    //

    @Override
    public void viewProfile(){
        System.out.println("Tech Support Specialist Profile");
        System.out.println("ID: " + id);
        System.out.println("Username: " + username);
        System.out.println("Employee ID: " + super.getEmployeeId());
        System.out.println("Salary: " + super.getSalary());
        System.out.println("Hire date: " + super.getHireDate());
        System.out.println("Orders count: " + orders.size());
    }

    @Override
    public String toString(){
        return "Tech Support specilist " + username + "(id=" + id + ", employee id=" + 
        super.getEmployeeId() + ")";
    }
}
