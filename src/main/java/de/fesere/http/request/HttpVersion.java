package de.fesere.http.request;

public enum HttpVersion {
  HTTP_11;

  public static HttpVersion fromString(String element) {
    if(element.contains("1.1")){
      return HTTP_11;
    }
    throw new RuntimeException("Unsupported HTTP version: " + element);
  }

  public String printable() {
    if(this == HTTP_11) {
      return "HTTP/1.1";
    }
    throw new RuntimeException("Unsupported HTTP version: " + this);
  }
}
