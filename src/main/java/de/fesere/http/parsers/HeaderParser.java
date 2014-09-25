package de.fesere.http.parsers;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HeaderParser {

  private static final int NUMBER_OF_ELEMENTS = 2;

  public Map<String, String> read(BufferedReader reader) throws IOException {
    Map<String, String> headers = new HashMap<>();
    String line = reader.readLine();
    while (!isLast(line)) {
      String [] elements = splitIntoKeyValuePairs(line);
      headers.put(elements[0], elements[1]);
      line = reader.readLine();
    }

    return headers;
  }

  private boolean isLast(String line) {
    return line == null || line.trim().equals("");
  }

  public String[] splitIntoKeyValuePairs(String line) {
    String[] elements = line.split(":", NUMBER_OF_ELEMENTS);
    return clean(elements);
  }

  private String[] clean(String[] elements) {
    int numberOfElements = elements.length;
    String[] result = new String[numberOfElements];
    for (int i = 0; i < numberOfElements; i++) {
      result[i] = elements[i].trim();
    }
    return result;
  }
}
