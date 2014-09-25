package de.fesere.http.vfs;

public class FileDoesNotExistException extends RuntimeException {
  public FileDoesNotExistException(String filename) {
    super("Did not find file:" + filename);
  }
}
