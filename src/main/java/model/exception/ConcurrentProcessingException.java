package model.exception;

public class ConcurrentProcessingException extends RuntimeException {
    private String message;

    public ConcurrentProcessingException(String message) {
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
