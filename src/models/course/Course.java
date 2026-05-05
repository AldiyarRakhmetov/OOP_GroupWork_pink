package models.course;
import models.users.*;
import java.io.Serializable;
import java.util.*;
import models.exceptions.*;

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
    public void addStudent(Student student) {
        if (student != null && !students.contains(student)) {
            students.add(student);
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
    public void addMark(Student student, Mark mark) throws StudentNotFoundException {
        if (!students.contains(student)) {
            throw new StudentNotFoundException();
        }
        marks.put(student, mark);
    }

    public Mark getMark(Student student) {
        return marks.get(student);
    }
    public List<Student> getStudents() {
        return students;
    }
    public List<Teacher> getTeachers() {
        return teachers;
    }

    public String getCode() { return code; }
    public String getTitle() { return title; }
    public int getCredits() { return credits; }
    public String getMajor() { return major; }
    public int getYearOfStudy() { return yearOfStudy; }

    public void setCode(String code) {
        this.code = code;
    }
    public void setCredits(int credits) {
        this.credits = credits;
    }
    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }
    public void setMajor(String major) {
        this.major = major;
    }
    public void setYearOfStudy(int yearOfStudy) {
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
