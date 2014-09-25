package de.fesere.http;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import static org.apache.commons.io.IOUtils.readLines;

public class FileReaderTestBase {
  protected String readFile(String path) {

    try {
      List<String> lines = readLines(getClass().getResourceAsStream(path), Charset.forName("UTF-8").toString());
      return mergeLines(lines);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private String mergeLines(List<String> lines) {
    StringBuilder builder = new StringBuilder();
    for (String line : lines) {
      builder.append(line).append("\r\n");
    }

    return builder.toString();
  }
}
