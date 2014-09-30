package de.fesere.http;

import java.util.List;

public class Utils {
  public static String flatten(List<String> input, String delimiter) {
    String result = "";
    for (String line : input) {
      result += line + delimiter;
    }
    return result;
  }
}
