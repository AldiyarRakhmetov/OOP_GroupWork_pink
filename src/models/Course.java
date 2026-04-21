package models;

import java.io.Serializable;
import java.util.*;

public class Course implements Serializable {
    private static final long serialVersionUID = 1L;
    private String code;
    private String title;
    private int credits;
    private String major;
    private int yearOfStudy;
    private List<Lesson> lessons = new ArrayList<>();
    private List<Teacher> teachers = new ArrayList<>();

    public Course(String code, String title, int credits, String major, int yearOfStudy) {
        this.code = code;
        this.title = title;
        this.credits = credits;
        this.major = major;
        this.yearOfStudy = yearOfStudy;
    }

    
    public void addTeacher(Teacher teacher) {
        if (teacher != null && !teachers.contains(teacher)) {
            teachers.add(teacher);
        }
    }

    public void removeTeacher(Teacher teacher) {
        teachers.remove(teacher);
    }

    public void addLesson(Lesson lesson) {
        if (lesson != null) {
            lessons.add(lesson);
        }
    }

   
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public int getCredits() { return credits; }
    public void setCredits(int credits) { this.credits = credits; }
    public String getMajor() { return major; }
    public void setMajor(String major) { this.major = major; }
    public int getYearOfStudy() { return yearOfStudy; }
    public void setYearOfStudy(int yearOfStudy) { this.yearOfStudy = yearOfStudy; }

    @Override
    public String toString() {
        return code + ": " + title;
    }
}
