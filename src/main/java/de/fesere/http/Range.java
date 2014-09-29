package de.fesere.http;

import de.fesere.http.request.HttpRequest;
import de.fesere.http.response.HttpResponse;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static de.fesere.http.response.HttpResponse.response;
import static de.fesere.http.response.StatusLine.OK;
import static de.fesere.http.response.StatusLine.PARTIAL_CONTENT;
import static java.util.Arrays.asList;

public class Range {
  private Pattern pattern = Pattern.compile("bytes=([\\d]+)-([\\d]+)");

  public boolean hasRangeHeader(HttpRequest request) {
    return request.getHeaders().containsKey("Range");
  }

  public HttpResponse handleRangeRequest(String range, List<String> lines) {
    Matcher m = pattern.matcher(range);
    if (m.find()) {
      int from = Integer.parseInt(m.group(1));
      int to = Integer.parseInt(m.group(2));
      String line = flatten(lines).substring(from, to);
      lines = asList(line);
      return response(PARTIAL_CONTENT).withBody(lines).build();
    } else {
      return response(OK).withBody(lines).build();
    }
  }

  private String flatten(List<String> read) {
    String result = "";
    for (String line : read) {
      result += line + "\n";
    }
    return result;
  }
}
