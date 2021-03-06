package de.fesere.http.request;

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

  @Override
  public String toString() {
    return method.toString()  + " " + path + " " + httpVersion.printable();
  }
}
