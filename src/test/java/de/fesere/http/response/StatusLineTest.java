package de.fesere.http.response;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class StatusLineTest {

  @Test
  public void correctlyFormatedOutput() {
    StatusLine line = new StatusLine(200, "OK");
    assertThat(line.printable(), is("HTTP/1.1 200 OK"));
  }
}