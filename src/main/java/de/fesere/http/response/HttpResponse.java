package de.fesere.http.response;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class HttpResponse {

  private final StatusLine statusLine;
  private final Map<String, String> headers;
  private final String body;

  public  HttpResponse(StatusLine statusLine) {
    this(statusLine, new HashMap<String, String>(), "");
  }
  public HttpResponse(StatusLine statusLine, Map<String, String> headers, String body) {
    this.statusLine = statusLine;
    this.headers = headers;
    this.body = body;
  }

  public int getStatusCode() {
    return statusLine.getStatusCode();
  }

  public String printable() {
    LinkedList<String> lines = new LinkedList<>();
    if(body.length() > 0) {
      headers.put("Content-Length", "" + body.length());
    }
    lines.add(statusLine.printable());
    lines.addAll(flatten(headers));
    lines.add("");
    lines.add(body);
    return merge(lines);
  }

  private List<String> flatten(Map<String, String> headers) {
    List<String> result = new LinkedList<>();
    for (Map.Entry<String, String> header : headers.entrySet()) {
      result.add(header.getKey() + ": " + header.getValue());
    }
    return result;
  }

  private String merge(List<String> lines) {
    String result = "";
    for (String line : lines) {
      result += line + "\r\n";
    }
    return result;
  }
}
