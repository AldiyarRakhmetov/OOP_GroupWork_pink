package models.exceptions;

public class StudentNotFoundException extends Exception {
    public StudentNotFoundException() {
        super("Student not registered in this course");
    }
}