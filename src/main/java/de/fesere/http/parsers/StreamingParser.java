package de.fesere.http.parsers;

import de.fesere.http.HttpRequest;
import de.fesere.http.RequestLine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class StreamingParser {

  private BufferedReader reader;
  private RequestLineParser requestLineParser = new RequestLineParser();
  private HeaderParser headerParser = new HeaderParser();

  public StreamingParser(InputStream inputStream) {
    this.reader = new BufferedReader(new InputStreamReader(inputStream));
  }

  public HttpRequest read() {
    try {
      RequestLine requestLine = readRequestLine();
      Map<String, String> headers = readHeaders();
      List<String> body = readBody(headers);
      return new HttpRequest(requestLine, headers, body);

    } catch (IOException e) {
      throw new RuntimeException("Whaaaa", e);
    } finally {
      try {
        reader.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  private RequestLine readRequestLine() throws IOException {
    String rawRequestLine = reader.readLine();
    return requestLineParser.read(rawRequestLine);
  }

  private Map<String, String> readHeaders() throws IOException {
    Map<String, String> headers = new HashMap<>();
    boolean isFinished = false;
    while (!isFinished) {
      String line = reader.readLine();
      if (line.trim().equals("")) {
        isFinished = true;

      } else {
        String[] elements = headerParser.splitIntoKeyValuePairs(line);
        headers.put(elements[0].trim(), elements[1].trim());
      }
    }
    return headers;
  }

  private List<String> readBody(Map<String, String> headers) throws IOException {
    List<String> body;
    if (hasBody(headers)) {
      body = extractBody();
    } else {
      body = new LinkedList<>();
    }
    return body;
  }

  private boolean hasBody(Map<String, String> headers) {
    return headers.containsKey("Content-Length");
  }

  private List<String> extractBody() throws IOException {
    List<String> body = new LinkedList<>();
    boolean isFinished = false;
    while (!isFinished) {
      String line = reader.readLine();
      if (line.trim().equals("")) {
        isFinished = true;
      } else {
        body.add(line);
      }
    }
    return body;
  }
}
