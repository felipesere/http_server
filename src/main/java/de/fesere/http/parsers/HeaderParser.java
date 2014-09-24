package de.fesere.http.parsers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HeaderParser {

  public static final int NUMBER_OF_ELEMENTS = 2;

  public Map<String, String> read(List<String> lines) {
    List<String> headerLines = extractHeaders(lines);
    return convertToHeaders(headerLines);
  }

  protected Map<String, String> convertToHeaders(List<String> lines) {
    Map<String, String> result = new HashMap<String, String>();
    for (String line : lines) {
      String[] elements = splitIntoKeyValuePairs(line);
      result.put(elements[0].trim(), elements[1].trim());
    }
    return result;
  }

  private List<String> extractHeaders(List<String> lines) {
    return lines.subList(1, findSeparator(lines));
  }

  private String[] splitIntoKeyValuePairs(String line) {
    return line.split(":", NUMBER_OF_ELEMENTS);
  }

  private int findSeparator(List<String> lines) {
    for (int i = 0; i < lines.size(); i++) {
      if(lines.get(i).trim().equals("")) {
        return i;
      }
    }
    return lines.size();
  }
}
