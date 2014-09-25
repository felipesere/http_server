package de.fesere.http.response;

import de.fesere.http.HttpVersion;

public class StatusLine {
  private final HttpVersion httpVersion = HttpVersion.HTTP_11;
  private final int status;
  private final String reason;

  public StatusLine(int status, String reason) {
    this.status = status;
    this.reason = reason;
  }

  public String printable() {
    return httpVersion.printable() + " " + status + " " + reason;
  }

  public static final StatusLine OK = new StatusLine(200, "OK");
  public static final StatusLine METHOD_NOT_ALLOWED = new StatusLine(405, "Method Not Allowed");
  public static final StatusLine NOT_FOUND = new StatusLine(404, "Not Found");
  public static final StatusLine CREATED = new StatusLine(201, "Created");

  public int getStatusCode() {
    return status;
  }
}
