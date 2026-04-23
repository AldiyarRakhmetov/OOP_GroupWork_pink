package models.research;

import java.util.Comparator;


public final class ResearchPaperComparators {

    private ResearchPaperComparators() {}

    public static final Comparator<ResearchPaper> BY_DATE =
            Comparator.comparing(ResearchPaper::getPublishDate).reversed();

    public static final Comparator<ResearchPaper> BY_CITATIONS =
            Comparator.comparingInt(ResearchPaper::getCitations).reversed();

    public static final Comparator<ResearchPaper> BY_LENGTH =
            Comparator.comparingInt(ResearchPaper::getLength).reversed();
}
