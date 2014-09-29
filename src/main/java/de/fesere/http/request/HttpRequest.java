package de.fesere.http.request;

import de.fesere.http.Method;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static de.fesere.http.HttpVersion.HTTP_11;
import static java.util.Arrays.asList;

public class HttpRequest {
  private final RequestLine requestLine;
  private final Map<String, String> headers;
  private final List<String> body;

  public HttpRequest(RequestLine requestLine, Map<String, String> headers, List<String> body) {
    this.requestLine = requestLine;
    this.headers = headers;
    this.body = body;
  }

  public RequestLine getRequestLine() {
    return requestLine;
  }

  public Map<String, String> getHeaders() {
    return headers;
  }

  public List<String> getBody() {
    return body;
  }

  public Path getPath() {
    return new Path(requestLine.getPath());
  }

  public static RequestBuilder request(Method method, String path) {
    return new RequestBuilder(method, path);
  }

  public static class RequestBuilder {

    private final Method method;
    private final String path;
    private final Map<String, String> headers = new HashMap<>();
    private List<String> body;

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
      withBody(asList(body));
      return this;
    }
  }
}
