package de.fesere.http.request;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static de.fesere.http.request.HttpVersion.HTTP_11;
import static java.util.Arrays.asList;

public class RequestBuilder {

  private final Method method;
  private final String path;
  private final Map<String, String> headers = new HashMap<>();
  private List<String> body = new LinkedList<>();

  public RequestBuilder(Method method, String path) {
    this.method = method;
    this.path = path;
  }

  public RequestBuilder addHeader(String key, String value) {
    headers.put(key, value);
    return this;
  }

  public RequestBuilder withBody(List<String> body) {
    this.body = body;
    return this;
  }

  public HttpRequest build() {
    return new HttpRequest(new RequestLine(method, path, HTTP_11), headers, body);
  }

  public RequestBuilder withBody(String body) {
    return  withBody(asList(body));
  }
}
