package de.fesere.http.parsers;

import de.fesere.http.request.HttpVersion;
import de.fesere.http.request.Method;
import de.fesere.http.request.RequestLine;

public class RequestLineParser {

  private static final int METHOD = 0;
  private static final int PATH = 1;
  private static final int HTTP_VERSION = 2;

  public RequestLine read(String line) {
    String [] elements = line.split(" ");
    return new RequestLine(Method.fromString(elements[METHOD]),
                           elements[PATH],
                           parseHttpVersion(elements[HTTP_VERSION]));
  }

  private HttpVersion parseHttpVersion(String element) {
    return HttpVersion.fromString(element);
  }
}
