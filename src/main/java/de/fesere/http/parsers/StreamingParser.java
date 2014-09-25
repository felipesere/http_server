package de.fesere.http.parsers;

import de.fesere.http.request.HttpRequest;
import de.fesere.http.request.RequestLine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

public class StreamingParser {

  public static final String CONTENT_LENGTH_HEADER = "Content-Length";
  private BufferedReader reader;
  private RequestLineParser requestLineParser = new RequestLineParser();
  private HeaderParser headerParser = new HeaderParser();

  public StreamingParser(InputStream inputStream) {
    this.reader = new BufferedReader(new InputStreamReader(inputStream));
  }

  public HttpRequest read() {
    try {
      RequestLine requestLine = readRequestLine();
      Map<String, String> headers = readHeaders(reader);
      List<String> body = readBody(headers);
      return new HttpRequest(requestLine, headers, body);
    } catch (IOException e) {
      throw new RuntimeException("could not parse HTTP Request", e);
    }
  }

  private Map<String, String> readHeaders(BufferedReader reader) throws IOException {
    return headerParser.read(reader);
  }

  private RequestLine readRequestLine() throws IOException {
    String rawRequestLine = reader.readLine();
    return requestLineParser.read(rawRequestLine);
  }

  private List<String> readBody(Map<String, String> headers) throws IOException {
    List<String> body;
    if (hasBody(headers)) {
      int chars = declaredBodyLength(headers);
      body = extractBody(chars);
    } else {
      body = new LinkedList<>();
    }
    return body;
  }

  private int declaredBodyLength(Map<String, String> headers) {
    return Integer.parseInt(headers.get(CONTENT_LENGTH_HEADER));
  }

  private boolean hasBody(Map<String, String> headers) {
    return headers.containsKey(CONTENT_LENGTH_HEADER);
  }

  private List<String> extractBody(int chars) throws IOException {
    CharBuffer buffer = CharBuffer.allocate(chars);
    int readTotal = 0;
    while(readTotal < chars) {
      int currentRead = reader.read(buffer);
      if(streamEnded(currentRead)) {
        break;
      }
      readTotal += currentRead;
    }
    return convertToLines(buffer);
  }

  private List<String> convertToLines(CharBuffer buffer) {
    String body = new String(buffer.array());
    return new ArrayList<>(asList(body.split("\n")));
  }

  private boolean streamEnded(int currentRead) {
    return currentRead == -1;
  }
}
