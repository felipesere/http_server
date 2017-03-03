package de.fesere.http.request;

public enum Method {
  POST,
  PUT,
  GET,
  HEAD,
  DELETE,
  OPTIONS,
  PATCH,
  UNKNWON;

  public static  Method fromString(String element) {
    try {
      return Method.valueOf(element.toUpperCase());
    } catch (IllegalArgumentException e) {
      return Method.UNKNWON;
    }
  }
}
