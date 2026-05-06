package demos.Aldiyar;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import models.users.Employee;
import models.research.ResearchPaper;
import models.research.ResearchProject;

public class EmployeeDemo {
    public static void main(String[] args) {

        //employees
        Employee alice = new Employee(1, "Alice", "pass1", "E001", 50000,
                LocalDateTime.now(), false);

        Employee bob = new Employee(2, "Bob", "pass2", "E002", 70000,
                LocalDateTime.now(), true);

        //sendMessage
        alice.sendMessage(bob, "Hello Bob!");
        bob.sendMessage(alice, "Hi Alice!");

        System.out.println("Bob messages: " + bob.getMessages());

        //sendComplaint
        alice.sendComplaint(bob, "Too much work!");
        System.out.println("Bob complaints: " + bob.getComplaints());

        //ResearchPapers
        ResearchPaper p1 = new ResearchPaper(
                "AI in Education",
                Arrays.asList("Alice", "Bob"),
                "Journal A",
                12,
                new Date(),
                50,
                "doi-1"
        );

        ResearchPaper p2 = new ResearchPaper(
                "ML Basics",
                List.of("Bob"),
                "Journal B",
                10,
                new Date(),
                30,
                "doi-2"
        );

        ResearchPaper p3 = new ResearchPaper(
                "Deep Learning",
                List.of("Alice"),
                "Journal C",
                15,
                new Date(),
                70,
                "doi-3"
        );

        ResearchProject project = new ResearchProject("AI Project");

        //Researcher
        try {
            bob.addPaper(p1);
            bob.addPaper(p2);
            bob.addPaper(p3);

            System.out.println("\n--- Papers sorted (by citations DESC) ---");
            bob.printPapers(Comparator.naturalOrder());

            bob.joinProject(project);

            System.out.println("\nTop paper:");
            bob.getTopCitedPaper().printInfo();

            System.out.println("\nTotal citations: " + bob.calculateCitations());
            System.out.println("H-index: " + bob.getHIndex());

            System.out.println("\nAll papers:");
            for (ResearchPaper p : bob.getPapers()) {
                System.out.println(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        //!isResearcher
        System.out.println("\n--- Alice tries research ---");
        alice.printPapers(Comparator.naturalOrder());
        System.out.println("Alice H-index: " + alice.getHIndex());

        //toggle
        alice.toggleResearcher();

        System.out.println("Alice is now researcher: " + alice.isResearcher());
    }
}