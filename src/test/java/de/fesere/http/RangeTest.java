package de.fesere.http;

import de.fesere.http.request.HttpRequest;
import de.fesere.http.request.RequestLine;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static de.fesere.http.HttpVersion.HTTP_11;
import static de.fesere.http.Method.GET;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class RangeTest {

  @Test
  public void looksForRangeHeader() {
    Range range = new Range();
    Map<String, String> headers = new HashMap<>();
    headers.put("Range", "Some range");
    HttpRequest request = new HttpRequest(new RequestLine(GET,"/foo", HTTP_11),headers, Collections.emptyList());

    assertThat(range.hasRangeHeader(request), is(true));
  }
}