package de.fesere.http.parsers;

import de.fesere.http.HttpRequest;
import de.fesere.http.StatusLine;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

public class RequestParser {
  public static final int FIRST = 0;
  private final StatusLineParser statusLineParser = new StatusLineParser();
  private final HeaderParser headerParser = new HeaderParser();

  public HttpRequest read(String input) {
    List<String> lines = splitIntoLines(input);
    StatusLine status = statusLineParser.read(lines.get(FIRST));
    Map<String, String> headers = headerParser.read(lines);
    List<String> body = extractBody(lines, headers);
    return new HttpRequest(status, headers, body);
  }

  private List<String> extractBody(List<String> lines, Map<String, String> headers) {
    if(hasContent(headers)) {
      int afterHeaders = headers.size() + 1;
      int withoutRequestEnd = lines.size() - 1;
      return lines.subList(afterHeaders, withoutRequestEnd);
    }
    return new LinkedList<String>();
  }

  private boolean hasContent(Map<String, String> headers) {
    return headers.containsKey("Content-Length");
  }

  private List<String> splitIntoLines(String request) {
    return new ArrayList<String>(asList(request.split("\n")));
  }
}
