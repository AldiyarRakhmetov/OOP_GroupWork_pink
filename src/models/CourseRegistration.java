package models;

import enums.*;
import exceptions.*;
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

    public void approve() throws CreditLimitExceededException {
        // 1. Проверка на дубликат 
    	
        if (student.getCourses() != null && student.getCourses().contains(this.course)) {
            System.out.println("Registration Rejected: Student is already enrolled in " + course.getCode());
            this.status = RegistrationStatus.REJECTED;
            return;
        }

        // 2. Проверка лимита в 21 кредит
        
        if (student.getTotalCredits() + course.getCredits() > 21) {
            throw new CreditLimitExceededException("Credit limit (21) exceeded! Attempted: " 
                + (student.getTotalCredits() + course.getCredits()));
        }

        // 3. Проверка лимита провалов (3 и более F)
        
        if (student.getFailedCoursesCount() >= 3) {
            System.out.println("Registration Rejected: Student has 3 or more failed courses.");
            this.status = RegistrationStatus.REJECTED;
            return;
        }

        // Если все проверки пройдены
        
        this.status = RegistrationStatus.APPROVED;
        student.addCourse(this.course); 
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
            registrationId, student.getUsername(), course.getCode(), status);
    }
}