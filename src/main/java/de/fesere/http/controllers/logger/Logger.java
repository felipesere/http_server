package de.fesere.http.controllers.logger;

import java.util.LinkedList;
import java.util.List;

public class Logger {

  private static final List<String> logs = new LinkedList<>();
  public static void log(String line) {
    logs.add(line);
  }

  public static List<String> read() {
    return new LinkedList<>(logs);
  }
}
