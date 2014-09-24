package de.fesere.http.parsers;

import de.fesere.http.StatusLine;

public class StatusLineParser {

  public StatusLine read(String line) {
    String [] elements = line.split(" ");
    return new StatusLine(elements[0], elements[1], elements[2]);
  }
}
