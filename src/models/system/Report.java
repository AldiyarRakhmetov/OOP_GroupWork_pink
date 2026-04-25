package models.system;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Report implements Comparable<News>{
    private String title;
    private String content;
    private LocalDateTime createdAt;


    public Report(String title, String text){ //init
        this.title = title;
        this.content = text;
        this.createdAt = LocalDateTime.now();
    }
    public Report(String title, String text, LocalDateTime createdAt){
        this.title = title;
        this.content = text;
        this.createdAt = createdAt;
    }


    public String getTitle() { //getters and setters
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }


    public void generate(){
        //TO BE ADDED, no idea what it's supposed to do either :p
    }
    public void export(){
        //same situation. MAN I should've checked the diagramm earlier
    }


    @Override //toString
    public String toString() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return "[REPORT] " + title + ": " + content + "(Created at " + createdAt.format(format) + ")";
    }

    @Override //equals (requires same title and date)
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        News notThis = (News) obj;
        return Objects.equals(title, notThis.getTitle()) &&
        Objects.equals(createdAt, notThis.getCreatedAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, createdAt);
    }

    @Override //compares dates
    public int compareTo(News notThis) {
        return createdAt.compareTo(notThis.getCreatedAt());
    }
}
