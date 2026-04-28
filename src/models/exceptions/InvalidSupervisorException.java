package models.exceptions;
public class InvalidSupervisorException extends Exception {

    private final int actualHIndex;

    public InvalidSupervisorException(String supervisorName, int hIndex) {
        super("Invalid supervisor: \"" + supervisorName +
                "\" has h-index=" + hIndex + ", but minimum required is 3.");
        this.actualHIndex = hIndex;
    }
    public InvalidSupervisorException(String message) {
        super(message);
        this.actualHIndex = -1;
    }

    public int getActualHIndex() { return actualHIndex; }
}
