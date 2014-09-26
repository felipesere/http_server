package de.fesere.http.controllers;

import de.fesere.http.request.HttpRequest;
import de.fesere.http.response.HttpResponse;
import de.fesere.http.response.StatusLine;

import java.util.HashMap;
import java.util.Map;

public class RedirectController extends BaseController{
  @Override
  public HttpResponse doGet(HttpRequest request) {
    Map<String, String> headers = new HashMap<>();
    headers.put("Location", "http://localhost:5000/");
    return new HttpResponse(new StatusLine(302, "Moved Permanently"), headers, "");
  }
}
