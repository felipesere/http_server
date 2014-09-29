package de.fesere.http.response;

import de.fesere.http.request.HttpRequest;
import de.fesere.http.response.HttpResponse;
import de.fesere.http.response.Range;
import org.junit.Test;

import static de.fesere.http.request.Method.GET;
import static de.fesere.http.matchers.HttpResponseMatchers.hasBody;
import static de.fesere.http.matchers.HttpResponseMatchers.hasStatusCode;
import static de.fesere.http.request.HttpRequest.request;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class RangeTest {

  private Range range = new Range();

  @Test
  public void looksForRangeHeader() {
    HttpRequest request = request(GET, "/foo").addHeader("Range", "some range").build();

    assertThat(range.hasRangeHeader(request), is(true));
  }

  @Test
  public void truncateResponseByFourBytes() {

    HttpResponse response = range.handleRangeRequest("bytes=0-4", "This has many chars");
    assertThat(response, hasStatusCode(206));
    assertThat(response, hasBody(equalTo("This")));
  }

  @Test
  public void malformedRangeResultsInBadRequest() {
    HttpResponse response = range.handleRangeRequest("bes=0-4", "This has many chars");
    assertThat(response, hasStatusCode(400));

    response = range.handleRangeRequest("bytes=0/4", "This has many chars");
    assertThat(response, hasStatusCode(400));
  }

  @Test
  public void startPositionLargerThanAvailableResultsInRangeNotSatisfiable() {
    HttpResponse response = range.handleRangeRequest("bytes=10-14", "This");
    assertThat(response, hasStatusCode(416));
  }

  @Test
  public void endPositionAfterEndTruncatesAsFarAsPossible() {
    HttpResponse response = range.handleRangeRequest("bytes=0-14", "This");
    assertThat(response, hasStatusCode(206));
    assertThat(response, hasBody(equalTo("This")));
  }
}