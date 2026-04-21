package university.research;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
public class ResearchPaper implements Comparable<ResearchPaper> {
    private String title;
    private List<String> authors;
    private String journal;
    private int pages;
    private Date publishDate;
    private int citations;
    private String doi;
    public ResearchPaper(String title, List<String> authors, String journal, int pages, Date publishDate, int citations, String doi) {
        this.title = title;
        this.authors = new ArrayList<>(authors);
        this.journal = journal;
        this.pages = pages;
        this.publishDate = publishDate;
        this.citations = citations;
        this.doi = doi;
    }

    @Override
    public int compareTo(ResearchPaper other) {
        return Integer.compare(other.citations, this.citations);
    }

    public int getLength() {
        return pages;
    }

    public void printInfo() {
        System.out.println("=== Research Paper ===");
        System.out.println("Title    : " + title);
        System.out.println("Authors  : " + String.join(", ", authors));
        System.out.println("Journal  : " + journal);
        System.out.println("Pages    : " + pages);
        System.out.println("Published: " + publishDate);
        System.out.println("Citations: " + citations);
        System.out.println("DOI      : " + doi);
    }

    @Override
    public String toString() {
        return "\"" + title + "\" (" + publishDate + ") — citations: " + citations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResearchPaper)) return false;
        ResearchPaper that = (ResearchPaper) o;
        return Objects.equals(doi, that.doi);
    }

    @Override
    public int hashCode() {
        return Objects.hash(doi);
    }

    public String getTitle()        { return title; }
    public List<String> getAuthors(){ return new ArrayList<>(authors); }
    public String getJournal()      { return journal; }
    public int getPages()           { return pages; }
    public Date getPublishDate()    { return publishDate; }
    public int getCitations()       { return citations; }
    public String getDoi()          { return doi; }

    public void setCitations(int citations) { this.citations = citations; }
}
