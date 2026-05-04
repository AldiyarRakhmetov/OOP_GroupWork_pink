import models.enums.TeacherTitle;
import models.exceptions.InvalidSupervisorException;
import models.exceptions.NonResearcherJoinProjectException;
import models.research.*;
import models.users.Student;

import java.util.Date;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        System.out.println("==========================================");
        System.out.println("     RESEARCH DEMO                        ");
        System.out.println("==========================================\n");

        TeacherResearcher prof1    = new TeacherResearcher("Dr. Akhmetov",  TeacherTitle.PROFESSOR);
        TeacherResearcher prof2    = new TeacherResearcher("Dr. Nurlanova", TeacherTitle.PROFESSOR);
        TeacherResearcher lecturer = new TeacherResearcher("Mr. Serik",     TeacherTitle.SENIOR_LECTURER);

        @SuppressWarnings("deprecation")
        ResearchPaper p1 = new ResearchPaper("Deep Learning in NLP",
                List.of("Dr. Akhmetov", "Dr. Nurlanova"), "IEEE Transactions",
                18, new Date(2023-1900,5,10), 120, "10.1109/NLP.2023.001");
        @SuppressWarnings("deprecation")
        ResearchPaper p2 = new ResearchPaper("Blockchain for Education",
                List.of("Dr. Akhmetov"), "Elsevier Journal",
                12, new Date(2022-1900,2,5), 80, "10.1016/EDU.2022.002");
        @SuppressWarnings("deprecation")
        ResearchPaper p3 = new ResearchPaper("AI Ethics in Central Asia",
                List.of("Dr. Akhmetov", "Mr. Serik"), "Springer",
                22, new Date(2021-1900,9,1), 55, "10.1007/AI.2021.003");
        @SuppressWarnings("deprecation")
        ResearchPaper p4 = new ResearchPaper("Federated Learning Survey",
                List.of("Dr. Nurlanova"), "ACM Computing",
                15, new Date(2022-1900,6,20), 40, "10.1145/FL.2022.004");
        @SuppressWarnings("deprecation")
        ResearchPaper p5 = new ResearchPaper("Cloud Security Models",
                List.of("Dr. Nurlanova", "Mr. Serik"), "IEEE Security",
                11, new Date(2023-1900,3,14), 25, "10.1109/CS.2023.005");
        @SuppressWarnings("deprecation")
        ResearchPaper p6 = new ResearchPaper("Smart Campus Systems",
                List.of("Mr. Serik"), "ACM Digital",
                9, new Date(2023-1900,8,15), 18, "10.1145/SC.2023.006");

        prof1.addPaper(p1); prof1.addPaper(p2); prof1.addPaper(p3);
        prof2.addPaper(p1); prof2.addPaper(p4); prof2.addPaper(p5);
        lecturer.addPaper(p3); lecturer.addPaper(p5); lecturer.addPaper(p6);

        System.out.println("--- Research Project Setup ---");
        ResearchProject project = new ResearchProject("AI-Driven Smart University");
        try {
            project.addParticipant(prof1);
            project.addParticipant(prof2);
            project.addParticipant(lecturer);
        } catch (NonResearcherJoinProjectException e) {
            System.out.println("Error: " + e.getMessage());
        }
        project.addPaper(p1); project.addPaper(p2);
        project.addPaper(p3); project.addPaper(p4);
        project.addPaper(p5); project.addPaper(p6);

        System.out.println();
        project.printProjectInfo();

        System.out.println("\n--- Dr. Akhmetov Papers (by citations) ---");
        prof1.printPapers(ResearchPaperComparators.BY_CITATIONS);

        System.out.println("\n--- Dr. Nurlanova Papers (by date) ---");
        prof2.printPapers(ResearchPaperComparators.BY_DATE);

        System.out.println("\n--- All Project Papers (by length) ---");
        project.printAllPapers(ResearchPaperComparators.BY_LENGTH);

        System.out.println("\n--- Researcher Statistics ---");
        System.out.printf("%-22s | %9s | %7s | %s%n",
                "Name", "Citations", "H-index", "Top Paper");
        System.out.println("-".repeat(70));
        for (TeacherResearcher r : List.of(prof1, prof2, lecturer)) {
            ResearchPaper top = r.getTopCitedPaper();
            System.out.printf("%-22s | %9d | %7d | %s%n",
                    r.getResearcherName(),
                    r.calculateCitations(),
                    r.getHIndex(),
                    top != null ? top.getTitle() : "—");
        }

        System.out.println("\n--- Supervisor Assignment ---");
        Student student = new Student(101, "Aizat", "pass",
                "SE-2001", "SITE", "CS", 4);
        try {
            System.out.println("Prof1 h-index: " + prof1.getHIndex());
            student.setSupervisor(prof1);
            student.viewProfile();
        } catch (InvalidSupervisorException e) {
            System.out.println("Cannot assign: " + e.getMessage());
        }

        System.out.println("\n--- Demo completed successfully! ---");
    }

    static class TeacherResearcher extends ResearcherImpl {
        private final String name;
        private final TeacherTitle title;

        TeacherResearcher(String name, TeacherTitle title) {
            this.name  = name;
            this.title = title;
            System.out.println("[Init] " + name + " (" + title + ") created as researcher.");
        }

        @Override public String getResearcherName() { return name; }
        @Override public String toString()           { return "Researcher: " + name; }
    }
}