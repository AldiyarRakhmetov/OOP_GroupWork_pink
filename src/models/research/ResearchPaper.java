package models.research;

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

    public ResearchPaper(String title, List<String> authors, String journal,
                         int pages, Date publishDate, int citations, String doi) {
        if (title == null || title.isBlank())
            throw new IllegalArgumentException("Paper title cannot be empty");
        if (authors == null || authors.isEmpty())
            throw new IllegalArgumentException("Authors list cannot be empty");
        if (journal == null || journal.isBlank())
            throw new IllegalArgumentException("Journal cannot be empty");
        if (pages <= 0)
            throw new IllegalArgumentException("Pages must be positive");
        if (citations < 0)
            throw new IllegalArgumentException("Citations cannot be negative");
        if (doi == null || doi.isBlank())
            throw new IllegalArgumentException("DOI cannot be empty");

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

    public int getLength() { return pages; }

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
        return Objects.equals(doi, ((ResearchPaper) o).doi);
    }

    @Override
    public int hashCode() { return Objects.hash(doi); }

    public String getTitle()         { return title; }
    public List<String> getAuthors() { return new ArrayList<>(authors); }
    public String getJournal()       { return journal; }
    public int getPages()            { return pages; }
    public Date getPublishDate()     { return publishDate; }
    public int getCitations()        { return citations; }
    public String getDoi()           { return doi; }

    public void setCitations(int citations) {
        if (citations < 0) throw new IllegalArgumentException("Citations cannot be negative");
        this.citations = citations;
    }
}
