package models.users;
import models.employee.Employee;
import models.course.Course;
import models.course.CourseRegistration;
import models.enums.ManagerType;
import models.enums.RegistrationStatus;
import models.system.News;

import java.util.List;

public class Manager extends Employee {

    private ManagerType type;

    public Manager(int id, String username, String password,
                   String employeeId, double salary,
                   java.util.Date hireDate,
                   ManagerType type) {

        super(id, username, password, employeeId, salary, hireDate);
        this.type = type;
    }

    //  approve registration
    public void approveRegistration(CourseRegistration registration) throws Exception {

        if (registration.getStatus() != RegistrationStatus.PENDING) {
            throw new Exception("Registration already processed");
        }

        registration.approve();
    }

    //  reject
    public void rejectRegistration(CourseRegistration registration) {
        registration.reject();
    }

    //  add course for registration
    public void addCourse(List<Course> courses, Course course) {
        if (!courses.contains(course)) {
            courses.add(course);
        }
    }

    //  assign teacher
    public void assignTeacher(Course course, Teacher teacher) {
        course.addTeacher(teacher);
    }

    //  simple report
    public void createReport(List<Student> students) {
        double avg = students.stream()
                .mapToDouble(Student::getGpa)
                .average()
                .orElse(0);

        System.out.println("Average GPA: " + avg);
    }
    // list of students
    public void viewStudents(List<Student> students) {
        students.forEach(System.out::println);
    }

    public void viewStudentsByGPA(List<Student> students) {
        students.stream()
                .sorted((a, b) -> Double.compare(b.getGpa(), a.getGpa()))
                .forEach(System.out::println);
    }

    public void addNews(List<News> newsList, News news) {
        if (news == null) {
            throw new IllegalArgumentException("News cannot be null");
        }

        news.publish(); // используем метод из News
        newsList.add(news);
    }

    @Override
    public void viewProfile() {
        System.out.println("Manager: " + username + " | Type: " + type);
    }
}