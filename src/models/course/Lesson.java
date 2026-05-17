package models.course;

import models.enums.LessonType;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Lesson implements Serializable {
    private static final long serialVersionUID = 1L;
    private String lessonId;
    private String topic;
    private Date date;
    private LessonType type;

    public Lesson(String lessonId, String topic, LessonType type) {
        if (lessonId == null || lessonId.isBlank()) {
            throw new IllegalArgumentException("Lesson ID cannot be empty");
        }
        if (topic == null || topic.isBlank()) {
            throw new IllegalArgumentException("Lesson topic cannot be empty");
        }
        if (type == null) {
            throw new IllegalArgumentException("Lesson type cannot be null");
        }

        this.lessonId = lessonId;
        this.topic = topic;
        this.type = type;
        this.date = new Date();
    }

    public void displayLessonInfo() {
        System.out.println(this.toString());
    }

    @Override
    public String toString() {
        return String.format("[%s] ID: %s | Topic: %s | Date: %s", type, lessonId, topic, date);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lesson)) return false;
        Lesson lesson = (Lesson) o;
        return Objects.equals(lessonId, lesson.lessonId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lessonId);
    }
}
