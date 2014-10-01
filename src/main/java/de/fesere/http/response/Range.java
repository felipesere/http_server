package de.fesere.http.response;

import de.fesere.http.request.HttpRequest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static de.fesere.http.response.HttpResponse.response;
import static de.fesere.http.response.StatusLine.*;
import static java.lang.Integer.parseInt;

public class Range {
  private final Pattern pattern = Pattern.compile("bytes=([\\d]+)-([\\d]+)");

  public boolean hasRangeHeader(HttpRequest request) {
    return extractRangeHeader(request) != null;
  }

  public HttpResponse handleRangeRequest(HttpRequest request, String line) {
    String rangeHeader = extractRangeHeader(request);
    Matcher range = pattern.matcher(rangeHeader);

    if (range.find()) {
      int from = parseInt(range.group(1));
      int to = Math.min(parseInt(range.group(2)), line.length());
      if (from > line.length()) {
        return response(OUT_OF_RANGE).build();
      }
      String body = line.substring(from, to);
      return response(PARTIAL_CONTENT).withBody(body).build();
    } else {
      return response(BAD_REQUEST).build();
    }
  }

  private String extractRangeHeader(HttpRequest request) {
    return request.getHeaders().get("Range");
  }
}
