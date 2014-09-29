package de.fesere.http.response;

import de.fesere.http.FileReaderTestBase;
import org.junit.Test;

import static de.fesere.http.response.HttpResponse.response;
import static de.fesere.http.response.StatusLine.CREATED;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class HttpResponseTest extends FileReaderTestBase {

  @Test
  public void canFormatResponse() {
    String expected = readFile("/responses/created.txt");

    String body = "201 Created";
    HttpResponse response = response(CREATED).addHeader("Cache-Control", "private").withBody(body).build();

    assertThat(response.printable(), is(expected));
  }
}