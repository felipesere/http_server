package de.fesere.http;

import de.fesere.http.request.HttpRequest;
import org.junit.Test;

import static de.fesere.http.Method.GET;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class RangeTest {

  @Test
  public void looksForRangeHeader() {
    Range range = new Range();
    HttpRequest request = HttpRequest.request(GET, "/foo").addHeader("Range", "some range").build();

    assertThat(range.hasRangeHeader(request), is(true));
  }
}