package de.fesere.http.parsers;

import de.fesere.http.RequestLine;
import org.junit.Test;

import static de.fesere.http.HttpVersion.HTTP_11;
import static de.fesere.http.Method.GET;
import static de.fesere.http.Method.POST;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class RequestLineParserTest {

  private RequestLineParser parser = new RequestLineParser();

  @Test
  public void itCanParseAGETStatusLine() {
    RequestLine requestLine = parser.read("GET /logs HTTP/1.1\n");

    assertThat(requestLine.getMethod(), is(GET));
    assertThat(requestLine.getPath(), is("/logs"));
    assertThat(requestLine.getHttpVersion(), is(HTTP_11));
  }

  @Test
  public void itCanParseAPOSTStatusLine() {
    RequestLine requestLine = parser.read("POST /logs HTTP/1.1\n");

    assertThat(requestLine.getMethod(), is(POST));
  }
}
