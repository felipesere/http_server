package de.fesere.http;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

public class FileReaderTestBase {
  public String readFile(String path) {

    try {
      List<String> lines = IOUtils.readLines(getClass().getResourceAsStream(path), Charset.forName("UTF-8").toString());
      return mergeLines(lines);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private String mergeLines(List<String> lines) {
    StringBuilder builder = new StringBuilder();
    for (String line : lines) {
      builder.append(line).append("\n");
    }

    return builder.toString();
  }
}
