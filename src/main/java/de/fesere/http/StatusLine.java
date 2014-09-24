package de.fesere.http;

public class StatusLine {
  private final Method method;
  private final String path;
  private final HttpVersion httpVersion;

  public StatusLine(Method method, String path, HttpVersion httpVersion) {
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
