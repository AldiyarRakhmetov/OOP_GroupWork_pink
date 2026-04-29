package models.system;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.List;
import models.users.Student;

import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;

public class Report implements Comparable<News>{
    private String title;
    private String content;
    private LocalDateTime createdAt;


    public Report(String title){ //init
        this.title = title;
        this.createdAt = LocalDateTime.now();
    }
    public Report(String title, LocalDateTime createdAt){
        this.title = title;
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


    public void generate(List<Student> students){
        int studentCount = students.size();
        double avg = students.stream()
                .mapToDouble(Student::getGpa)
                .average()
                .orElse(0);
        long failedStudentCount = students.stream()
                                    .filter(student -> student.getFailedCoursesCount() == 3).count();

        content = "Student amount: " + studentCount + "\nAverage gpa: " + avg + "\nFailed student amount: "
        + failedStudentCount;
    }
    public void export() throws IOException {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Path path = Path.of("report.txt");
        String proper = title + "\nCreated at: " + createdAt.format(format) + "\n" + content;
        Files.writeString(path, proper);
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
