package model.exception;

public class EmailAlreadyExistsException extends Exception {
    String message;

    public EmailAlreadyExistsException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
