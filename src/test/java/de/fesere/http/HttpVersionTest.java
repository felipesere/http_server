package de.fesere.http;

import org.junit.Test;

import static de.fesere.http.HttpVersion.HTTP_11;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class HttpVersionTest {

  @Test
  public void validHttp11conversion() {
    assertThat(HttpVersion.fromString("HTTP/1.1"), is(HTTP_11));
  }

  @Test(expected = RuntimeException.class)
  public void invalidConversion() {
    HttpVersion.fromString("HTTP/2.0");
  }
}