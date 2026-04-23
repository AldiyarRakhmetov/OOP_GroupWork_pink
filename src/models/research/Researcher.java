package models.research;

import models.exceptions.NonResearcherJoinProjectException;

import java.util.Comparator;
import java.util.List;

public interface Researcher {
    void printPapers(Comparator<ResearchPaper> comparator);
    void joinProject(ResearchProject project) throws NonResearcherJoinProjectException;
    void addPaper(ResearchPaper paper);
    ResearchPaper getTopCitedPaper();
    int calculateCitations();
    int getHIndex();
    List<ResearchPaper> getPapers();
}
