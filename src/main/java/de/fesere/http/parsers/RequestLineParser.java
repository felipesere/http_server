package de.fesere.http.parsers;

import de.fesere.http.HttpVersion;
import de.fesere.http.Method;
import de.fesere.http.request.RequestLine;

public class RequestLineParser {

  private static final int METHOD = 0;
  private static final int PATH = 1;
  private static final int HTTP_VERSION = 2;

  public RequestLine read(String line) {
    String [] elements = line.split(" ");
    return new RequestLine(parseMethod(elements[METHOD]),
                           elements[PATH],
                           parseHttpVersion(elements[HTTP_VERSION]));
  }

  private Method parseMethod(String element) {
    return Method.valueOf(element.toUpperCase());
  }

  private HttpVersion parseHttpVersion(String element) {
    return HttpVersion.fromString(element);
  }
}
