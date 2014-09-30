package de.fesere.http.controllers.filesystem;

import java.util.List;

public class HtmlView {
  private static final String START = "<html><body>";
  private static final String END = "</body></html>";
  private static final String LINEBREAK = "</br>";

  public String render(List<String> paths) {
    return START + createLinks(paths) + END;
  }

  private String createLinks(List<String> paths) {
    String links = "";
    for(String path : paths) {
      links += createLink(path) + LINEBREAK;
    }
    return links;
  }

  private String createLink(String fullPath) {
    return "<a href=\""+fullPath+"\">"+ relative(fullPath) + "</a>";
  }

  private String relative(String fullPath) {
    if(fullPath.startsWith("/")) {
      return fullPath.replaceFirst("/", "");
    } else {
      return fullPath;
    }
  }
}
