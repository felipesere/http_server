package de.fesere.http.parsers;

import org.junit.Test;

import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public class HeaderParserTest {
  private final HeaderParser parser = new HeaderParser();

  @Test
  public void ignoresTheStatusLine() {
    List<String> lines = asList("StatusLine", "Connection: close", "Host: localhost:5000");
    Map<String, String> read = parser.read(lines);
    assertThat(read.values(), hasSize(2));
  }

  @Test
  public void splitsHeadersAtColon() {
    List<String> lines = asList("Connection: close", "Host: localhost:5000");
    Map<String, String> read = parser.convertToHeaders(lines);
    assertThat(read.get("Host"), is("localhost:5000"));
    assertThat(read.get("Connection"), is("close"));
  }

  @Test
  public void doesNotParseBeyondAnEmptyLine() {
    List<String> lines = asList("StatusLine","Connection: close", "Host: localhost:5000", "\n", "Should-Not: Parse");
    Map<String, String> read = parser.read(lines);
    assertThat(read.values(), hasSize(2));
    assertThat(read.get("Connection"), is("close"));
    assertThat(read.get("Host"), is("localhost:5000"));
  }
}