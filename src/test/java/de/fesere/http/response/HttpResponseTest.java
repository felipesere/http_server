package de.fesere.http.response;

import org.junit.Test;

import static de.fesere.http.response.HttpResponse.response;
import static de.fesere.http.response.StatusLine.CREATED;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class HttpResponseTest {

  @Test
  public void canFormatResponse() {
    String expected = "HTTP/1.1 201 Created\r\n" +
            "Cache-Control: private\r\n" +
            "Content-Length: 11\r\n" +
            "Content-Type: text/plain; charset=utf-8\r\n" +
            "\r\n" +
            "201 Created\r\n";

    HttpResponse response = response(CREATED).addHeader("Cache-Control", "private")
                                             .addHeader("Content-Type", "text/plain; charset=utf-8")
                                             .withBody("201 Created").build();

    assertThat(response.printable(), is(expected));
  }


  @Test
  public void canCreateAResponseWithBodyBasedOnBytes() {
    String expected = "HTTP/1.1 201 Created\r\n" +
            "Cache-Control: private\r\n" +
            "Content-Length: 11\r\n" +
            "Content-Type: text/plain; charset=utf-8\r\n" +
            "\r\n" +
            "201 Created\r\n";

    HttpResponse response = response(CREATED).addHeader("Cache-Control", "private")
                                             .addHeader("Content-Type", "text/plain; charset=utf-8")
                                             .withBody("201 Created".getBytes()).build();
    assertThat(response.printable(), is(expected));
    assertThat(response.toBytes(), is(equalTo(expected.getBytes())));
  }
}