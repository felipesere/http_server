package de.fesere.http.response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static de.fesere.http.Utils.flatten;

public class ResponseBuilder {
  private final StatusLine status;
  private final Map<String, String> header = new HashMap<>();
  private byte[] body = new byte[0];

  public ResponseBuilder(StatusLine status) {
    this.status = status;
  }

  public ResponseBuilder addHeader(String key, String value) {
    header.put(key, value);
    return this;
  }

  public ResponseBuilder withBody(String body) {
    return withBody(body.getBytes());
  }

  public ResponseBuilder withBody(List<String> body) {
    return withBody(flatten(body, "\n").getBytes());
  }

  public ResponseBuilder withBody(byte[] bytes) {
    this.body = bytes;
    return this;
  }

  public HttpResponse build() {
    return new HttpResponse(status, header, body);
  }
}
