package de.fesere.http.response;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static de.fesere.http.utils.Utils.flatten;

public class HttpResponse {

  private static final String CRLF = "\r\n";
  private final StatusLine statusLine;
  private final Map<String, String> headers;
  private final byte[] body;

  public static ResponseBuilder response(StatusLine line) {
    return new ResponseBuilder(line);
  }

  public HttpResponse(StatusLine statusLine, Map<String, String> headers, byte[] body) {
    this.statusLine = statusLine;
    this.headers = headers;
    this.body = body;
  }

  public int getStatusCode() {
    return statusLine.getStatusCode();
  }

  public String printable() {
    return new String(toBytes(), Charset.defaultCharset());
  }

  private void addContentLength() {
    if (hasBody()) {
      headers.put("Content-Length", "" + body.length);
    }
  }

  private boolean hasBody() {
    return body.length > 0;
  }

  private String merge(List<String> lines) {
    return flatten(lines, CRLF);
  }

  public String getBody() {
    return new String(body);
  }

  public byte[] toBytes() {
    addContentLength();
    byte[] statusBytes = statusLine.printable().getBytes();
    byte[] headerBytes = merge(flatten(headers, ": ")).getBytes();
    byte[] crlfBytes   = CRLF.getBytes();

    int bufferSize = statusBytes.length +
                     headerBytes.length +
                     body.length +
                     CRLF.length() * 3;
    ByteBuffer buffer = ByteBuffer.allocate(bufferSize);
    buffer.put(statusBytes);
    buffer.put(crlfBytes);
    buffer.put(headerBytes);
    buffer.put(crlfBytes);
    buffer.put(body);
    buffer.put(crlfBytes);
    buffer.flip();

    return buffer.array();
  }

  public Map<String, String> getHeader() {
    return new HashMap<>(headers);
  }
}
