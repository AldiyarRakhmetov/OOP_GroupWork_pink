package models.exceptions;

public class CourseRetakeLimitException extends RuntimeException {

    public CourseRetakeLimitException() {
        super("Student exceeded maximum retake limit (3)");
    }

    public CourseRetakeLimitException(String message) {
        super(message);
    }
}