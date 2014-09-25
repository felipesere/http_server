package de.fesere.http.router;

public class ControllerAlreadyExistsException extends RuntimeException {
  public ControllerAlreadyExistsException(String path) {
    super("A controller for " + path + " already exists");
  }
}
