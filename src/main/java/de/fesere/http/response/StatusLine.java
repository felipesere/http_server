package de.fesere.http.response;

import de.fesere.http.request.HttpVersion;

public enum  StatusLine {

  OK(200, "OK"),
  CREATED(201, "Created"),
  NOT_MODIFIED(204, "Not Modified"),
  PARTIAL_CONTENT(206, "Partial Content"),

  MOVED_PERMANENTLY(302, "Moved Permanently"),

  BAD_REQUEST(400, "Bad Request"),
  UNAUTHORIZED(401,"Unauthorized"),
  NOT_FOUND(404, "Not Found"),
  METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
  PRECONDITION_FAILED(412, "Precondition Failed"),
  OUT_OF_RANGE(416,"Range not satisfiable"),
  INTERNAL_SERVER_ERROR(500, "Internal Server Error");


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
