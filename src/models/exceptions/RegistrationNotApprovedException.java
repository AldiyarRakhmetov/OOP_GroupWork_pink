package models.exceptions;

public class RegistrationNotApprovedException extends Exception {
    public RegistrationNotApprovedException() {
        super("Registration is not approved yet");
    }
}