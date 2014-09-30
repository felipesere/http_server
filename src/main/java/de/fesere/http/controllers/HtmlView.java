package de.fesere.http.controllers;

import java.util.List;

public class HtmlView {
  private static final String START = "<html><body>";
  private static final String END = "</body></html>";

  public String render() {
    return START + END;
  }

  public String render(String fullPath) {
    String link = createLink(fullPath);
    return START + link + END;
  }

  public String render(List<String> paths) {
    String result = START;
    for(String path : paths) {
      result += createLink(path);
    }
    result += END;
    return result;
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
