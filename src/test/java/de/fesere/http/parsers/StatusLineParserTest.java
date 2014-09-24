package de.fesere.http.parsers;

import de.fesere.http.FileReaderTestBase;
import de.fesere.http.StatusLine;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class StatusLineParserTest extends FileReaderTestBase {

  private StatusLineParser parser = new StatusLineParser();

  @Test
  public void itParsesTheStatusLine() {
    String statusLineString = getStatusLineOf("/get.txt");

    StatusLine statusLine = parser.read(statusLineString);

    assertThat(statusLine.getMethod(), is("GET"));
    assertThat(statusLine.getPath(), is("/logs"));
    assertThat(statusLine.getHttpVersion(), is("HTTP/1.1"));
  }

  private String getStatusLineOf(String file) {
    String input = readFile(file);
    return input.split("\n")[0];
  }
}
