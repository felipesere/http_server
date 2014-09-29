package de.fesere.http.response;

import de.fesere.http.HttpVersion;

public enum  StatusLine {

  OK(200, "OK"),
  CREATED(201, "Created"),
  NOT_MODIFIED(204, "Not Modified"),
  PARTIAL_CONTENT(206, "Partial Content"),

  MOVED_PERMANENTLY(302, "Moved Permanently"),

  NOT_FOUND(404, "Not Found"),
  METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
  PRECONDITION_FAILED(412, "Precondition Failed");

  private final HttpVersion httpVersion = HttpVersion.HTTP_11;
  private final int status;
  private final String reason;

  private StatusLine(int status, String reason) {
    this.status = status;
    this.reason = reason;
  }

  public String printable() {
    return httpVersion.printable() + " " + status + " " + reason;
  }

  public int getStatusCode() {
    return status;
  }
}
