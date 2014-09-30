package de.fesere.http;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Utils {
  public static String flatten(List<String> input, String delimiter) {
    String result = "";
    for (String line : input) {
      result += line + delimiter;
    }
    return result;
  }

  public static List<String> flatten(Map<String, String> intput, String delimiter) {
    List<String> result = new LinkedList<>();
    for (Map.Entry<String, String> entry : intput.entrySet()) {
      result.add(entry.getKey() + delimiter + entry.getValue());
    }
    return result;
  }
}
