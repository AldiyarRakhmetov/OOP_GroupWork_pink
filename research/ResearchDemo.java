package university.research;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ResearchDemo {

    static class StaffResearcher extends ResearcherImpl {
        private final String name;
        private final int hIndex;

        StaffResearcher(String name) { this.name = name; this.hIndex = -1; }

        @Override public String getResearcherName() { return name; }
        @Override public String toString() { return name; }
    }

    static class StudentResearcher extends ResearcherImpl {
        private final String name;
        private Researcher supervisor;

        StudentResearcher(String name) { this.name = name; }

        public void assignSupervisor(Researcher supervisor)
                throws InvalidSupervisorException {
            if (supervisor.getHIndex() < 3) {
                throw new InvalidSupervisorException(
                        supervisor.toString(), supervisor.getHIndex());
            }
            this.supervisor = supervisor;
            System.out.println("[" + name + "] Supervisor assigned: " + supervisor);
        }

        @Override public String getResearcherName() { return name; }
        @Override public String toString() { return name; }
    }


    @SuppressWarnings("deprecation")
    public static void main(String[] args) {

        ResearchPaper p1 = new ResearchPaper(
                "Deep Learning for NLP",
                Arrays.asList("Alice", "Bob"),
                "IEEE Trans. Neural Networks",
                12, new Date(2023 - 1900, 5, 1), 150, "10.1109/abc");

        ResearchPaper p2 = new ResearchPaper(
                "Quantum Computing Basics",
                Arrays.asList("Alice"),
                "Nature",
                8, new Date(2022 - 1900, 1, 15), 300, "10.1038/xyz");

        ResearchPaper p3 = new ResearchPaper(
                "Graph Neural Networks Survey",
                Arrays.asList("Bob", "Carol"),
                "ACM Computing Surveys",
                25, new Date(2024 - 1900, 0, 10), 80, "10.1145/def");

        StaffResearcher alice = new StaffResearcher("Dr. Alice");
        StaffResearcher bob   = new StaffResearcher("Dr. Bob (tutor)");

        alice.addPaper(p1);
        alice.addPaper(p2);
        bob.addPaper(p3);

        System.out.println("\n--- h-index check ---");
        System.out.println("Alice h-index: " + alice.getHIndex()); // expects 1
        System.out.println("Bob   h-index: " + bob.getHIndex());   // expects 1

        System.out.println("\n--- Alice's papers sorted BY_CITATIONS ---");
        alice.printPapers(ResearchPaperComparators.BY_CITATIONS);

        System.out.println("\n--- Alice's papers sorted BY_DATE ---");
        alice.printPapers(ResearchPaperComparators.BY_DATE);

        System.out.println("\n--- Alice's papers sorted BY_LENGTH ---");
        alice.printPapers(ResearchPaperComparators.BY_LENGTH);

        ResearchProject project = new ResearchProject("AI in Education");

        try {
            project.addParticipant(alice);
            project.addParticipant(bob);
            project.addPaper(p1);
            project.addPaper(p3);
        } catch (NonResearcherJoinProjectException e) {
            System.err.println("ERROR: " + e.getMessage());
        }

        project.printProjectInfo();

        System.out.println("\n--- Project papers BY_DATE ---");
        project.printAllPapers(ResearchPaperComparators.BY_DATE);

        System.out.println("\n--- Supervisor assignment (h-index < 3 → exception) ---");
        StudentResearcher student = new StudentResearcher("Student Almaz (4th year)");
        try {
            student.assignSupervisor(bob);
        } catch (InvalidSupervisorException e) {
            System.out.println("Caught: " + e.getMessage());
        }
        try {
            alice.addPaper(new ResearchPaper("Paper3", List.of("Alice"), "X", 5,
                    new Date(2021-1900,3,1), 5, "doi3"));
            alice.addPaper(new ResearchPaper("Paper4", List.of("Alice"), "Y", 4,
                    new Date(2020-1900,3,1), 10, "doi4"));
            System.out.println("Alice h-index now: " + alice.getHIndex());
            student.assignSupervisor(alice);
        } catch (InvalidSupervisorException e) {
            System.out.println("Caught: " + e.getMessage());
        }

        System.out.println("\nAlice top cited paper: " + alice.getTopCitedPaper());
        System.out.println("Alice total citations: " + alice.calculateCitations());
    }
}
