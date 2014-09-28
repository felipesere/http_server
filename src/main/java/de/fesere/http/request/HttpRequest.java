package de.fesere.http.request;

import java.util.List;
import java.util.Map;

public class HttpRequest {
  private final RequestLine requestLine;
  private final Map<String, String> headers;
  private final List<String> body;

  public HttpRequest(RequestLine requestLine, Map<String, String> headers, List<String> body) {
    this.requestLine = requestLine;
    this.headers = headers;
    this.body = body;
  }
  public RequestLine getRequestLine() {
    return requestLine;
  }

  public Map<String, String> getHeaders() {
    return headers;
  }

  public List<String> getBody() {
    return body;
  }

  public Path getPath() {
    return new Path(requestLine.getPath());
  }
}
