package de.fesere.http.utils;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Utils {
  public static String flatten(List<String> input, String delimiter) {
     return input.stream().collect(Collectors.joining(delimiter)) + delimiter;
  }

  public static List<String> flatten(Map<String, String> input, String delimiter) {
    return input.entrySet().stream()
            .map(entry -> flat(entry, delimiter))
            .collect(Collectors.toCollection(LinkedList::new));
  }

  private static String flat(Map.Entry<String, String> entry, String delimiter) {
    return entry.getKey() + delimiter + entry.getValue();
  }
}
