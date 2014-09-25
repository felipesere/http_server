package de.fesere.http.response;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class HttpResponse {
  private List<String> lines = new LinkedList<>();

  public HttpResponse(StatusLine line, Map<String, String> headers, String body) {
    headers.put("Content-Length", "" + body.length());
    lines.add(line.printable());
    lines.addAll(flatten(headers));
    lines.add("");
    lines.add(body);

  }

  private List<String> flatten(Map<String, String> headers) {
    List<String> result = new LinkedList<>();
    for (Map.Entry<String, String> header : headers.entrySet()) {
      result.add(header.getKey() + ": " + header.getValue());
    }
    return result;
  }

  public String printable() {
    return merge(lines);
  }

  private String merge(List<String> lines) {
    String result = "";
    for (String line : lines) {
      result += line + "\n";
    }
    return result;
  }
}
