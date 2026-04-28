package models.course;

import models.users.*;
import models.enums.*;
import models.exceptions.*;

import java.io.Serializable;

public class CourseRegistration implements Serializable {
    private static final long serialVersionUID = 1L;
    private int registrationId;
    private Student student;
    private Course course;
    private RegistrationStatus status;

    public CourseRegistration(int registrationId, Student student, Course course) {
        this.registrationId = registrationId;
        this.student = student;
        this.course = course;
        this.status = RegistrationStatus.PENDING;
    }

    public void approve() throws CreditLimitExceededException, AlreadyRegisteredException {

        //  если уже обработано — выходим
        if (status != RegistrationStatus.PENDING) {
            return;
        }

        try {
            student.registerForCourse(course);

            this.status = RegistrationStatus.APPROVED;

        } catch (AlreadyRegisteredException | CreditLimitExceededException e) {

            this.status = RegistrationStatus.REJECTED;
            throw e;
        }

        // проверка провалов
        if (student.getFailedCoursesCount() >= 3) {
            this.status = RegistrationStatus.REJECTED;
        }
    }

    public void reject() {
        this.status = RegistrationStatus.REJECTED;
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