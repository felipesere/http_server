package de.fesere.http.parsers;

import de.fesere.http.request.RequestLine;
import org.junit.Test;

import static de.fesere.http.request.HttpVersion.HTTP_11;
import static de.fesere.http.request.Method.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class RequestLineParserTest {

  private final RequestLineParser parser = new RequestLineParser();

  @Test
  public void itCanParseAGET() {
    RequestLine requestLine = parser.read("GET /logs HTTP/1.1\n");

    assertThat(requestLine.getMethod(), is(GET));
    assertThat(requestLine.getPath(), is("/logs"));
    assertThat(requestLine.getHttpVersion(), is(HTTP_11));
  }

  @Test
  public void itCanParseAPOST() {
    RequestLine requestLine = parser.read("POST /logs HTTP/1.1\n");
    assertThat(requestLine.getMethod(), is(POST));
  }

  @Test
  public void itCanParseAPUT() {
    RequestLine requestLine = parser.read("PUT /logs HTTP/1.1\n");
    assertThat(requestLine.getMethod(), is(PUT));
  }

  @Test
  public void itCanParseADELETE() {
    RequestLine requestLine = parser.read("DELETE /logs HTTP/1.1\n");
    assertThat(requestLine.getMethod(), is(DELETE));
  }

  @Test
  public void itCanParseAHEAD() {
    RequestLine requestLine = parser.read("HEAD /logs HTTP/1.1\n");
    assertThat(requestLine.getMethod(), is(HEAD));
  }

  @Test
  public void itCanParseAOPTIONS() {
    RequestLine requestLine = parser.read("OPTIONS /logs HTTP/1.1\n");
    assertThat(requestLine.getMethod(), is(OPTIONS));
  }

  @Test
  public void itCanParseAPATCH() {
    RequestLine requestLine = parser.read("PATCH /logs HTTP/1.1\n");
    assertThat(requestLine.getMethod(), is(PATCH));
  }
}
