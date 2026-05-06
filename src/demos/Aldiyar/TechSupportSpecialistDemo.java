package demos.Aldiyar;

import java.time.LocalDateTime;

import models.users.TechSupportSpecialist;
import models.system.Order;

public class TechSupportSpecialistDemo {
    public static void main(String[] args) {

        //tech supports
        TechSupportSpecialist tech = new TechSupportSpecialist(
                1,
                "SupportGuy",
                "pass",
                "TS001",
                40000,
                LocalDateTime.now()
        );

        tech.viewProfile();
        System.out.println(tech);

        //orders
        Order order1 = new Order(101, "Fix login issue");
        Order order2 = new Order(102, "Reset password");
        Order order3 = new Order(103, "System crash bug");

        //getOrder
        tech.getOrder(order1);
        tech.getOrder(order2);
        tech.getOrder(order3);

        //viewOrders
        tech.viewOrders();

        //AcceptOrder
        tech.acceptOrder(101);
        tech.acceptOrder(101);
        tech.acceptOrder(999);

        //inProgressOrder
        tech.inProgressOrder(101);
        tech.inProgressOrder(102);

        tech.viewOrders();

        //Employee features
        TechSupportSpecialist anotherTech = new TechSupportSpecialist(
                2,
                "Helper",
                "pass",
                "TS002",
                38000
        );

        tech.sendMessage(anotherTech, "Please take order 102");
        anotherTech.sendComplaint(tech, "Too many tickets!");

        System.out.println("\nMessages of Helper: " + anotherTech.getMessages());
        System.out.println("Complaints of SupportGuy: " + tech.getComplaints());

        //viewProfile
        tech.viewProfile();
    }
}