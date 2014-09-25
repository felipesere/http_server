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
}
