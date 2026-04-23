package models.exceptions;

public class AuthenticationException extends Exception {
    public AuthenticationException() {
        super("Invalid username or password");
    }
}