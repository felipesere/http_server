package de.fesere.http.parsers;

import de.fesere.http.HttpVersion;
import de.fesere.http.Method;
import de.fesere.http.StatusLine;

public class StatusLineParser {

  public static final int METHOD = 0;
  public static final int PATH = 1;
  public static final int HTTP_VERSION = 2;

  public StatusLine read(String line) {
    String [] elements = line.split(" ");
    return new StatusLine(parseMethod(elements[METHOD]), elements[PATH], parseHttpVersion(elements[HTTP_VERSION]));
  }

  private Method parseMethod(String element) {
    return Method.valueOf(element.toUpperCase());
  }

  private HttpVersion parseHttpVersion(String element) {
    return HttpVersion.fromString(element);
  }
}
