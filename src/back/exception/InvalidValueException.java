package back.exception;

public class InvalidValueException extends Exception {
    public InvalidValueException(String message) {
        super(message);
    }

    public InvalidValueException() {
        this("Invalid values!!!");
    }
}
