package university.research;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class ResearchProject {

    private String topic;
    private List<Researcher> participants;
    private List<ResearchPaper> publishedPapers;

    public ResearchProject(String topic) {
        this.topic = topic;
        this.participants = new ArrayList<>();
        this.publishedPapers = new ArrayList<>();
    }


    public void addParticipant(Researcher researcher) throws NonResearcherJoinProjectException {
        if (researcher == null) {
            throw new NonResearcherJoinProjectException(
                    "Cannot add null participant to project \"" + topic + "\"");
        }
        if (participants.contains(researcher)) {
            System.out.println("[INFO] Participant already in project: " + researcher);
            return;
        }
        participants.add(researcher);
        researcher.joinProject(this);      // notify the researcher
        System.out.println("[Project] Participant added to \"" + topic + "\"");
    }

    public void addPaper(ResearchPaper paper) {
        if (!publishedPapers.contains(paper)) {
            publishedPapers.add(paper);
        }
    }


    public void printProjectInfo() {
        System.out.println("========================================");
        System.out.println("Research Project: " + topic);
        System.out.println("Participants (" + participants.size() + "):");
        for (Researcher r : participants) {
            System.out.println("  - " + r + "  [citations: " + r.calculateCitations() + "]");
        }
        System.out.println("Published Papers (" + publishedPapers.size() + "):");
        publishedPapers.stream()
                .sorted(ResearchPaperComparators.BY_CITATIONS)
                .forEach(p -> System.out.println("  * " + p));
        System.out.println("========================================");
    }


    public void printAllPapers(Comparator<ResearchPaper> comparator) {
        System.out.println("--- All papers in project \"" + topic + "\" ---");
        participants.stream()
                .flatMap(r -> r.getPapers().stream())
                .distinct()
                .sorted(comparator)
                .forEach(System.out::println);
    }

    public String getTopic()                         { return topic; }
    public List<Researcher> getParticipants()        { return new ArrayList<>(participants); }
    public List<ResearchPaper> getPublishedPapers()  { return new ArrayList<>(publishedPapers); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResearchProject)) return false;
        return Objects.equals(topic, ((ResearchProject) o).topic);
    }

    @Override
    public int hashCode() { return Objects.hash(topic); }

    @Override
    public String toString() { return "ResearchProject{topic='" + topic + "'}"; }
}
