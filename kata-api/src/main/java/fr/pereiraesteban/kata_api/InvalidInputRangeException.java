package fr.pereiraesteban.kata_api;

public class InvalidInputRangeException extends RuntimeException{
  public InvalidInputRangeException(String message) {
    super(message);
  }

  public InvalidInputRangeException(Throwable cause) {
    super(cause);
  }

  public InvalidInputRangeException(String message, Throwable cause) {
    super(message, cause);
  }
}
