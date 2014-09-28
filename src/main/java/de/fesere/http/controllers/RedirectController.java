package de.fesere.http.controllers;

import de.fesere.http.request.HttpRequest;
import de.fesere.http.response.HttpResponse;

import java.util.HashMap;
import java.util.Map;

import static de.fesere.http.response.StatusLine.MOVED_PERMANENTLY;

public class RedirectController extends Controller {
  @Override
  public HttpResponse doGet(HttpRequest request) {
    Map<String, String> headers = new HashMap<>();
    headers.put("Location", "http://localhost:5000/");
    return new HttpResponse(MOVED_PERMANENTLY, headers, "");
  }
}
