package de.fesere.http.parsers;

import de.fesere.http.StatusLine;
import org.junit.Test;

import static de.fesere.http.HttpVersion.HTTP_11;
import static de.fesere.http.Method.GET;
import static de.fesere.http.Method.POST;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class StatusLineParserTest {

  private StatusLineParser parser = new StatusLineParser();

  @Test
  public void itCanParseAGETStatusLine() {
    StatusLine statusLine = parser.read("GET /logs HTTP/1.1\n");

    assertThat(statusLine.getMethod(), is(GET));
    assertThat(statusLine.getPath(), is("/logs"));
    assertThat(statusLine.getHttpVersion(), is(HTTP_11));
  }

  @Test
  public void itCanParseAPOSTStatusLine() {
    StatusLine statusLine = parser.read("POST /logs HTTP/1.1\n");

    assertThat(statusLine.getMethod(), is(POST));
  }
}
