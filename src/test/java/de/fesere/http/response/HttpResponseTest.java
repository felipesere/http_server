package de.fesere.http.response;

import de.fesere.http.FileReaderTestBase;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class HttpResponseTest extends FileReaderTestBase {

  @Test
  public void test() {
    String expected = readFile("/responses/created.txt");

    StatusLine line = new StatusLine(201, "Created");
    Map<String, String> headers = new HashMap<>();
    headers.put("Cache-Control", "private");
    String body = "201 Created";
    HttpResponse response = new HttpResponse(line, headers, body);

    assertThat(response.printable(), is(expected));
  }
}