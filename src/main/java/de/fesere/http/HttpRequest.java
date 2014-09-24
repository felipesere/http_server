package de.fesere.http;

import java.util.List;
import java.util.Map;

public class HttpRequest {
  private StatusLine statusLine;
  private Map<String, String> headers;
  private List<String> body;

  public HttpRequest(StatusLine statusLine, Map<String, String> headers, List<String> body) {
    this.statusLine = statusLine;
    this.headers = headers;
    this.body = body;
  }
  public StatusLine getStatusLine() {
    return statusLine;
  }

  public Map<String, String> getHeaders() {
    return headers;
  }

  public List<String> getBody() {
    return body;
  }
}
