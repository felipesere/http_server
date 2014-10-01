package de.fesere.http.response;

import de.fesere.http.request.HttpRequest;
import org.junit.Test;

import static de.fesere.http.matchers.HttpResponseMatchers.hasBody;
import static de.fesere.http.matchers.HttpResponseMatchers.hasStatusCode;
import static de.fesere.http.request.HttpRequest.request;
import static de.fesere.http.request.Method.GET;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class RangeTest {

  private final Range range = new Range();

  @Test
  public void looksForRangeHeader() {
    HttpRequest request = requestWithRange("some range");

    assertThat(range.hasRangeHeader(request), is(true));
  }

  @Test
  public void truncateResponseByFourBytes() {
    HttpRequest request = requestWithRange("bytes=0-4");
    HttpResponse response = range.handleRangeRequest(request, "This has many chars");
    assertThat(response, hasStatusCode(206));
    assertThat(response, hasBody(equalTo("This")));
  }

  private HttpRequest requestWithRange(String value) {
    return request(GET, "/foo").addHeader("Range", value).build();
  }

  @Test
  public void malformedRangeResultsInBadRequest() {
    HttpRequest request = requestWithRange("by=0-4");
    HttpResponse response = range.handleRangeRequest(request, "This has many chars");
    assertThat(response, hasStatusCode(400));

    request = requestWithRange("bytes=0/4");
    response = range.handleRangeRequest(request, "This has many chars");
    assertThat(response, hasStatusCode(400));
  }

  @Test
  public void startPositionLargerThanAvailableResultsInRangeNotSatisfiable() {
    HttpRequest request = requestWithRange("bytes=10-14");
    HttpResponse response = range.handleRangeRequest(request, "This");
    assertThat(response, hasStatusCode(416));
  }

  @Test
  public void endPositionAfterEndTruncatesAsFarAsPossible() {
    HttpRequest request = requestWithRange("bytes=0-14");
    HttpResponse response = range.handleRangeRequest(request, "This");
    assertThat(response, hasStatusCode(206));
    assertThat(response, hasBody(equalTo("This")));
  }
}