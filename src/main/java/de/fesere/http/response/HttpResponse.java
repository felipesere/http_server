package de.fesere.http.response;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class HttpResponse {

  public static final String CRLF = "\r\n";
  public static final String EMPTY_LINE = "";
  private final StatusLine statusLine;
  private final Map<String, String> headers;
  private final String body;

  public static ResponseBuilder response(StatusLine line) {
    return new ResponseBuilder(line);
  }
  private HttpResponse(StatusLine statusLine, Map<String, String> headers, String body) {
    this.statusLine = statusLine;
    this.headers = headers;
    this.body = body;
  }

  public int getStatusCode() {
    return statusLine.getStatusCode();
  }

  public String printable() {
    List<String> lines = new LinkedList<>();
    if(hasBody()) {
      addContentLength();
    }
    lines.add(statusLine.printable());
    lines.addAll(flatten(headers));
    lines.add(EMPTY_LINE);
    lines.add(body);
    return merge(lines);
  }

  private String addContentLength() {
    return headers.put("Content-Length", "" + body.length());
  }

  private boolean hasBody() {
    return body.length() > 0;
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
      result += line + CRLF;
    }
    return result;
  }

  public String getBody() {
    return body;
  }

  public static class ResponseBuilder {
    private final StatusLine status;
    private final Map<String, String> header = new HashMap<>();
    private String body = "";

    public ResponseBuilder(StatusLine status) {
      this.status = status;
    }

    public ResponseBuilder addHeader(String key, String value) {
      header.put(key,value);
      return this;
    }

    public ResponseBuilder withBody(String body) {
     this.body = body;
      return this;
    }

    public ResponseBuilder withBody(List<String> body) {
      this.body = flatten(body);
      return this;
    }

    public HttpResponse build() {
     return new HttpResponse(status, header, body);
    }

    private String flatten(List<String> read) {
      String result = "";
      for (String line : read) {
        result += line + "\n";
      }
      return result;
    }
  }
}
