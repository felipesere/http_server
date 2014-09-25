package de.fesere.http.parsers;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public class HeaderParserTest {
  private final HeaderParser parser = new HeaderParser();

  @Test
  public void splitsHeadersAtColon() throws IOException {
    StringReader input = new StringReader("Connection: close\n");
    Map<String, String> read = parser.read(new BufferedReader(input));
    assertThat(read.get("Connection"), is("close"));
  }

  @Test
  public void doesNotParseBeyondAnEmptyLine() throws IOException {
    StringReader input = new StringReader("Connection: close\nHost: localhost:5000\n\nShould-Not: Parse");
    Map<String, String> read = parser.read(new BufferedReader(input));
    assertThat(read.values(), hasSize(2));
    assertThat(read.get("Connection"), is("close"));
    assertThat(read.get("Host"), is("localhost:5000"));
  }
}