package de.fesere.http.request;

import java.util.List;
import java.util.Map;

public class HttpRequest {
  private RequestLine requestLine;
  private Map<String, String> headers;
  private List<String> body;

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
}
