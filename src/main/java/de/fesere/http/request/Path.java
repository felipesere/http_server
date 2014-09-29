package de.fesere.http.request;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Path {

  private final String path;

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
    Matcher matcher = Pattern.compile("^(/[^/\\?]+)").matcher(path);
    if (!matcher.find()) {
      return path;
    } else {
      return matcher.group(0);
    }
  }

  public String getFullpath() {
    return path;
  }

  public Map<String, String> getQueryParams() {
    Pattern pattern = Pattern.compile(".*\\?([^/]+)");
    Matcher m = pattern.matcher(path);
    Map<String, String> result = new HashMap<>();
    if(m.find() ) {
      return extractQueryParams(m.group(1));
    }
    return result;
  }

  private Map<String, String> extractQueryParams(String queryParams) {
    Pattern p  = Pattern.compile("([^&=]+)=([^&=]+)");
    Matcher m = p.matcher(queryParams);
    Map<String, String> result = new HashMap<>();
    while(m.find()) {
      result.put(m.group(1), decode(m.group(2)));
    }
    return result;
  }

  private String decode(String input) {
    try {
      return URLDecoder.decode(input, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException("No UTF-8 was available");
    }
  }
}
