package models.course;

import models.users.*;
import models.exceptions.*;

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
    private List<Student> students = new ArrayList<>();
    private Map<Student, Mark> marks = new HashMap<>();

    public Course(String code, String title, int credits, String major, int yearOfStudy) {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Course code cannot be empty");
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Course title cannot be empty");
        }
        if (credits <= 0 || credits > 21) {
            throw new IllegalArgumentException("Credits must be between 1 and 21");
        }
        if (major == null || major.isBlank()) {
            throw new IllegalArgumentException("Major cannot be empty");
        }
        if (yearOfStudy < 1 || yearOfStudy > 4) {
            throw new IllegalArgumentException("Year of study must be between 1 and 4");
        }

        this.code = code;
        this.title = title;
        this.credits = credits;
        this.major = major;
        this.yearOfStudy = yearOfStudy;
    }

    public void addTeacher(Teacher teacher) {
        if (teacher == null) {
            throw new IllegalArgumentException("Teacher cannot be null");
        }

        if (!teachers.contains(teacher)) {
            teachers.add(teacher);
        }
    }

    public void addStudent(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }

        if (!students.contains(student)) {
            students.add(student);
        }
    }

    public void removeTeacher(Teacher teacher) {
        teachers.remove(teacher);
    }

    public void addLesson(Lesson lesson) {
        if (lesson == null) {
            throw new IllegalArgumentException("Lesson cannot be null");
        }

        if (lessons.contains(lesson)) {
            throw new IllegalArgumentException("Lesson already exists");
        }

        lessons.add(lesson);
    }

    public void addMark(Student student, Mark mark) throws StudentNotFoundException {
        if (student == null || mark == null) {
            throw new IllegalArgumentException("Student and mark cannot be null");
        }

        if (!students.contains(student)) {
            throw new StudentNotFoundException();
        }

        marks.put(student, mark);
    }

    public Mark getMark(Student student) {
        return marks.get(student);
    }

    public List<Teacher> getTeachers() {
        return new ArrayList<>(teachers);
    }

    public List<Student> getStudents() {
        return new ArrayList<>(students);
    }

    public List<Lesson> getLessons() {
        return new ArrayList<>(lessons);
    }

    public String getCode() { return code; }
    public String getTitle() { return title; }
    public int getCredits() { return credits; }
    public String getMajor() { return major; }
    public int getYearOfStudy() { return yearOfStudy; }

    public void setCode(String code) {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Course code cannot be empty");
        }
        this.code = code;
    }

    public void setCredits(int credits) {
        if (credits <= 0 || credits > 21) {
            throw new IllegalArgumentException("Credits must be between 1 and 21");
        }
        this.credits = credits;
    }

    public void setLessons(List<Lesson> lessons) {
        if (lessons == null) {
            throw new IllegalArgumentException("Lessons cannot be null");
        }
        this.lessons = new ArrayList<>(lessons);
    }

    public void setMajor(String major) {
        if (major == null || major.isBlank()) {
            throw new IllegalArgumentException("Major cannot be empty");
        }
        this.major = major;
    }

    public void setYearOfStudy(int yearOfStudy) {
        if (yearOfStudy < 1 || yearOfStudy > 4) {
            throw new IllegalArgumentException("Year of study must be between 1 and 4");
        }
        this.yearOfStudy = yearOfStudy;
    }

    @Override
    public String toString() {
        return code + ": " + title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        Course c = (Course) o;
        return Objects.equals(code, c.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}