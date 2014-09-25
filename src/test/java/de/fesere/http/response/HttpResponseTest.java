package de.fesere.http.response;

import de.fesere.http.FileReaderTestBase;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static de.fesere.http.response.StatusLine.CREATED;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class HttpResponseTest extends FileReaderTestBase {

  @Test
  public void canFormatResponse() {
    String expected = readFile("/responses/created.txt");

    Map<String, String> headers = new HashMap<>();
    headers.put("Cache-Control", "private");
    String body = "201 Created";
    HttpResponse response = new HttpResponse(CREATED, headers, body);

    assertThat(response.printable(), is(expected));
  }
}