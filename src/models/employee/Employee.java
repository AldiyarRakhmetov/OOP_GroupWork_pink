package models.employee;
import models.users.User;
import java.util.Date;

public abstract class Employee extends User {
    private String employeeId;
    private double salary;
    private Date hireDate;

    //init
    public Employee(int id, String username, String password, String employeeId, double salary,
        Date hireDate
    ){
        super(id, username, password);
        this.employeeId = employeeId;
        this.salary = salary;
        this.hireDate = hireDate;
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

    public Date getHireDate() {
        return hireDate;
    }
    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }


    //functions to be added once I'm explained what the helly they're supposed to do
}
