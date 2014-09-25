package de.fesere.http.response;

import org.junit.Test;

import static de.fesere.http.response.StatusLine.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class StatusLineTest {

  @Test
  public void correctlyFormatedOutput() {
    StatusLine line = new StatusLine(123, "Sentinel Value");
    assertThat(line.printable(), is("HTTP/1.1 123 Sentinel Value"));
  }

  @Test
  public void formatConstants() {
    assertThat(OK.printable(), is("HTTP/1.1 200 OK"));
    assertThat(CREATED.printable(), is("HTTP/1.1 201 Created"));
    assertThat(NOT_FOUND.printable(), is("HTTP/1.1 404 Not Found"));
    assertThat(METHOD_NOT_ALLOWED.printable(), is("HTTP/1.1 405 Method Not Allowed"));
  }
}