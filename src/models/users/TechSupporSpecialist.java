package models.users;

import java.time.LocalDateTime;
import java.util.Vector;

import models.enums.OrderStatus;
import models.system.Order;

public class TechSupporSpecialist extends Employee {
    private Vector<Order> orders;

    public TechSupporSpecialist(int id, String username, String password, String employeeId, double salary,
        LocalDateTime hireDate){
            super(id, username, password, employeeId, salary, hireDate, false);
    }
    public TechSupporSpecialist(int id, String username, String password, String employeeId, double salary){
            super(id, username, password, employeeId, salary, LocalDateTime.now(), false);
    }
    
    public void getOrder(Order order){
        orders.add(order);
    }
    public void viewOrders(){
        System.out.println(username + "'s orders:\n");
        for (Order order : orders){
            System.out.println(order + "\n");
        }
    }
    public boolean acceptOrder(int id){
        for (Order order : orders){
            if (order.getId() == id){
                if (order.getStatus() != OrderStatus.SENT){
                    System.out.println("Order with id " + id + " is already accepted/in progress!");
                    return false;
                } else {
                    System.out.println("Order with id " + id + " was accepted");
                    order.setStatus(OrderStatus.ACCEPTED);
                    return true;
                }
            }
        }
        System.out.println("Couldn't find order with id " + id + "!");
        return false;
    }
    public boolean inProgressOrder(int id){
        for (Order order : orders){
            if (order.getId() == id){
                if (order.getStatus() == OrderStatus.ACCEPTED){
                    System.out.println("Order with id " + id + " is now in progress");
                    order.setStatus(OrderStatus.IN_PROGRESS);
                    return true;
                } else {
                    System.out.println("Order with id " + id + " is not accepted/already in progress!");
                    return false;
                }
            }
        }
        System.out.println("Couldn't find order with id " + id + "!");
        return false;
    }

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
