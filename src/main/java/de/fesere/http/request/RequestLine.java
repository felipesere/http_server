package de.fesere.http.request;

import de.fesere.http.HttpVersion;
import de.fesere.http.Method;

public class RequestLine {
  private final Method method;
  private final String path;
  private final HttpVersion httpVersion;

  public RequestLine(Method method, String path, HttpVersion httpVersion) {
    this.method = method;
    this.path = path;
    this.httpVersion = httpVersion;
  }

  public Method getMethod() {
    return method;
  }

  public String getPath() {
    return path;
  }

  public HttpVersion getHttpVersion() {
    return httpVersion;
  }
}
