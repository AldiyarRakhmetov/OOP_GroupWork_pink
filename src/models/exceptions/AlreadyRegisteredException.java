package models.exceptions;

public class AlreadyRegisteredException extends Exception {
    public AlreadyRegisteredException() {
        super("Student is already registered for this course");
    }
}