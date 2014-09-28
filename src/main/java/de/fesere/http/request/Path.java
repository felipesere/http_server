package de.fesere.http.request;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Path {

  private String path;

  public static Path path(String path) {
    return new Path(path);
  }

  public Path(String path) {
    this.path = path;
  }

  public boolean isRoot() {
    return path.equals("/");
  }

  public String remainder() {
    if(isRoot()) {
      return path;
    } else {
      int secondSlash = path.indexOf("/", 1);
      if(secondSlash > 0) {
        return path.substring(secondSlash,path.length());
      } else {
        return path;
      }
    }
  }

  public String getBase() {
    Matcher matcher = Pattern.compile("^(/[^/]+)").matcher(path);
    if (!matcher.find()) {
      return path;
    } else {
      return matcher.group(0);
    }
  }

  public String getFullpath() {
    return path;
  }
}
