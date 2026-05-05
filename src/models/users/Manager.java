package models.users;
import models.course.Course;
import models.course.CourseRegistration;
import models.enums.ManagerType;
import models.enums.RegistrationStatus;
import models.system.News;
import models.system.Report;
import database.Database;

import java.util.List;

import java.io.IOException; //for report export

public class Manager extends Employee {

    private ManagerType type;

    public Manager(int id, String username, String password,
                   String employeeId, double salary,
                   java.time.LocalDateTime hireDate, boolean isResearcher,
                   ManagerType type) {

        super(id, username, password, employeeId, salary, hireDate, isResearcher);
        this.type = type;
    }

    //  approve registration
    public void approveRegistration(CourseRegistration registration) throws Exception {

        if (registration.getStatus() != RegistrationStatus.PENDING) {
            throw new Exception("Registration already processed");
        }

        registration.approve();

        Database.getInstance().log(
                "Registration approved",
                this.username
        );
    }

    //  reject
    public void rejectRegistration(CourseRegistration registration) {
        registration.reject();

        Database.getInstance().log(
                "Registration rejected",
                this.username
        );
    }

    //  add course for registration
    public void addCourse(Course course) {
        Database.getInstance().addCourse(course);

        Database.getInstance().log(
                "Course added: " + course.getCode(),
                this.username
        );
    }

    //  assign teacher
    public void assignTeacher(Course course, Teacher teacher) {
        course.addTeacher(teacher);

        Database.getInstance().log(
                "Teacher assigned to course: " + course.getCode(),
                this.username
        );
    }

    //  simple report
    public void createReport(String title, List<Student> students) throws IOException {
        Report report = new Report(title);
        report.generate(students);
        report.export();
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

        news.publish();
        newsList.add(news);

        Database.getInstance().log(
                "News added: " + news.getTitle(),
                this.username
        );
    }

    @Override
    public void viewProfile() {
        System.out.println("Manager: " + username + " | Type: " + type);
    }
}