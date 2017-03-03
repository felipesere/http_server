package de.fesere.http.response;

import de.fesere.http.request.HttpRequest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static de.fesere.http.response.HttpResponse.response;
import static de.fesere.http.response.StatusLine.*;
import static java.lang.Integer.parseInt;

public class Range {
  private final Pattern twoSidedPattern = Pattern.compile("bytes=([\\d]+)-([\\d]+)");
  private final Pattern endedPattern = Pattern.compile("bytes=-([\\d]+)");
  private final Pattern openEnddedPattern = Pattern.compile("bytes=([\\d]+)-");

  public boolean hasRangeHeader(HttpRequest request) {
    return extractRangeHeader(request) != null;
  }

  public HttpResponse handleRangeRequest(HttpRequest request, String line) {
    String rangeHeader = extractRangeHeader(request);
    Matcher doubleSidedMatcher = twoSidedPattern.matcher(rangeHeader);
    Matcher endedMatcher = endedPattern.matcher(rangeHeader);
    Matcher openEndedMatcher = openEnddedPattern.matcher(rangeHeader);

    if (doubleSidedMatcher.find()) {
      return doubleSidedCrop(line, doubleSidedMatcher);
    } else if (endedMatcher.find()) {
      return cropFromTheEnd(line, endedMatcher);
    } else if (openEndedMatcher.find()) {
      return cropFromTheFront(line, openEndedMatcher);
    } else {
      return response(BAD_REQUEST).build();
    }
  }

  private HttpResponse cropFromTheFront(String line, Matcher openEndedMatcher) {
    int from = parseInt(openEndedMatcher.group(1));
    int to = line.length();

    return extractFrom(line, from, to);
  }

  private HttpResponse cropFromTheEnd(String line, Matcher endedMatcher) {
    int chars = parseInt(endedMatcher.group(1));
    int from = line.length() - chars;
    int to = line.length();

    return extractFrom(line, from, to);
  }

  private HttpResponse doubleSidedCrop(String line, Matcher range) {
    int from = parseInt(range.group(1));
    int to = Math.min(parseInt(range.group(2)) + 1, line.length());
    return extractFrom(line, from, to);
  }

  private HttpResponse extractFrom(String line, int from, int to) {
    if (from > line.length()) {
      return response(OUT_OF_RANGE).build();
    }
    String body = line.substring(from, to);
    return response(PARTIAL_CONTENT).withBody(body).build();
  }

  private String extractRangeHeader(HttpRequest request) {
    return request.getHeaders().get("Range");
  }
}
