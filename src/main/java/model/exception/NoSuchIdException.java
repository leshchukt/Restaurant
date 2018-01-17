package model.exception;

public class NoSuchIdException extends Exception {
    private String message;

    public NoSuchIdException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
