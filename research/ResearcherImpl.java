package university.research;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
public abstract class ResearcherImpl implements Researcher {

    protected List<ResearchPaper> papers = new ArrayList<>();
    protected List<ResearchProject> projects = new ArrayList<>();


    @Override
    public void printPapers(Comparator<ResearchPaper> comparator) {
        if (papers.isEmpty()) {
            System.out.println("[" + getResearcherName() + "] No papers published yet.");
            return;
        }
        System.out.println("=== Papers of " + getResearcherName() + " ===");
        papers.stream()
              .sorted(comparator)
              .forEach(System.out::println);
    }

    @Override
    public void joinProject(ResearchProject project) throws NonResearcherJoinProjectException {
        if (!projects.contains(project)) {
            projects.add(project);
            System.out.println("[" + getResearcherName() + "] Joined project: " + project.getTopic());
        }
    }

    @Override
    public void addPaper(ResearchPaper paper) {
        if (!papers.contains(paper)) {
            papers.add(paper);
        }
    }

    @Override
    public ResearchPaper getTopCitedPaper() {
        return papers.stream()
                .max(Comparator.comparingInt(ResearchPaper::getCitations))
                .orElse(null);
    }

    @Override
    public int calculateCitations() {
        return papers.stream().mapToInt(ResearchPaper::getCitations).sum();
    }

    @Override
    public int getHIndex() {
        List<Integer> sorted = papers.stream()
                .map(ResearchPaper::getCitations)
                .sorted(Comparator.reverseOrder())
                .toList();
        int h = 0;
        for (int i = 0; i < sorted.size(); i++) {
            if (sorted.get(i) >= i + 1) h = i + 1;
            else break;
        }
        return h;
    }

    @Override
    public List<ResearchPaper> getPapers() {
        return new ArrayList<>(papers);
    }

    public abstract String getResearcherName();
}
