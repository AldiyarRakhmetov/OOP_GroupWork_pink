package models.users;

import java.time.LocalDateTime;
import java.util.Vector;

import models.enums.OrderStatus;
import models.system.Order;

public class TechSupportSpecialist extends Employee {
    private Vector<Order> orders;

    public TechSupportSpecialist(int id, String username, String password,
                                 String employeeId, double salary, LocalDateTime hireDate) {
        super(id, username, password, employeeId, salary, hireDate, false);
        this.orders = new Vector<>();
    }

    public TechSupportSpecialist(int id, String username, String password,
                                 String employeeId, double salary) {
        super(id, username, password, employeeId, salary, LocalDateTime.now(), false);
        this.orders = new Vector<>();
    }

    public void getOrder(Order order) {
        if (order == null) throw new IllegalArgumentException("Order cannot be null");
        orders.add(order);
    }

    public void viewOrders() {
        System.out.println("\n" + username + "'s orders:");
        if (orders.isEmpty()) { System.out.println("  (none)"); return; }
        for (Order order : orders) System.out.println(order);
    }

    public boolean acceptOrder(int id) {
        for (Order order : orders) {
            if (order.getId() == id) {
                if (order.getStatus() != OrderStatus.SENT) {
                    System.out.println("Order " + id + " is already accepted/in progress");
                    return false;
                }
                order.setStatus(OrderStatus.ACCEPTED);
                System.out.println("Order " + id + " accepted");
                return true;
            }
        }
        System.out.println("Order not found: " + id);
        return false;
    }

    public boolean inProgressOrder(int id) {
        for (Order order : orders) {
            if (order.getId() == id) {
                if (order.getStatus() == OrderStatus.ACCEPTED) {
                    order.setStatus(OrderStatus.IN_PROGRESS);
                    System.out.println("Order " + id + " is now in progress");
                    return true;
                }
                System.out.println("Order " + id + " must be ACCEPTED before going IN_PROGRESS. Current: " + order.getStatus());
                return false;
            }
        }
        System.out.println("Order not found: " + id);
        return false;
    }

    @Override
    public void viewProfile() {
        System.out.println("Tech Support Specialist Profile");
        System.out.println("ID: " + id);
        System.out.println("Username: " + username);
        System.out.println("Employee ID: " + getEmployeeId());
        System.out.println("Salary: " + getSalary());
        System.out.println("Hire date: " + getHireDate());
        System.out.println("Orders count: " + orders.size());
    }

    @Override
    public String toString() {
        return "TechSupportSpecialist: " + username + " (id=" + id + ", employee id=" + getEmployeeId() + ")";
    }
}
