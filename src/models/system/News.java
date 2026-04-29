package models.system;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class News implements Comparable<News>{
    private String title;
    private String text;
    private LocalDateTime createdAt;


    public News(String title, String text){ //init
        this.title = title;
        this.text = text;
        this.createdAt = LocalDateTime.now();
    }
    public News(String title, String text, LocalDateTime createdAt){
        this.title = title;
        this.text = text;
        this.createdAt = createdAt;
    }


    public String getTitle() { //getters and setters
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }


    public void publish(){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String message = "\nNEWS!\n" + title + "\n" + text + "\n(Date: " + 
                                                                createdAt.format(format) + ")\n";
        System.out.println(message);
    }


    @Override //toString
    public String toString() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return "[NEWS] " + title + "(Created at " + createdAt.format(format) + ")";
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
