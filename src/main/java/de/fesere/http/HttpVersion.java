package de.fesere.http;

public enum HttpVersion {
  HTTP_11;

  public static HttpVersion fromString(String element) {
    if(element.contains("1.1")){
      return HTTP_11;
    }
    throw new RuntimeException("Unsupported HTTP version: " + element);
  }
}
