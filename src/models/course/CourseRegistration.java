package models.course;

import models.users.*;
import models.enums.*;
import models.exceptions.*;
import database.Database;

import java.io.Serializable;

public class CourseRegistration implements Serializable {
    private static final long serialVersionUID = 1L;
    private int registrationId;
    private Student student;
    private Course course;
    private RegistrationStatus status;

    public CourseRegistration(int registrationId, Student student, Course course) {
        if (registrationId <= 0) {
            throw new IllegalArgumentException("Registration ID must be positive");
        }
        if (student == null || course == null) {
            throw new IllegalArgumentException("Student and course cannot be null");
        }

        this.registrationId = registrationId;
        this.student = student;
        this.course = course;
        this.status = RegistrationStatus.PENDING;
    }

    public void approve() throws CreditLimitExceededException, AlreadyRegisteredException {

        if (status != RegistrationStatus.PENDING) {
            return;
        }

        if (student.getFailedCoursesCount() >= 3) {
            this.status = RegistrationStatus.REJECTED;

            Database.getInstance().log(
                    "Registration rejected: too many failed courses",
                    student.getUsername()
            );
            return;
        }

        student.registerForCourse(course);

        this.status = RegistrationStatus.APPROVED;

        Database.getInstance().log(
                "Course registered: " + course.getTitle(),
                student.getUsername()
        );
    }

    public void reject() {
        this.status = RegistrationStatus.REJECTED;

        Database.getInstance().log(
                "Registration rejected: " + course.getTitle(),
                student.getUsername()
        );
    }

    public RegistrationStatus getStatus() { return status; }
    public Course getCourse() { return course; }
    public Student getStudent() { return student; }

    @Override
    public String toString() {
        return String.format("RegID: %d | Student: %s | Course: %s | Status: %s",
                registrationId,
                student.getUsername(),
                course.getCode(),
                status);
    }
}