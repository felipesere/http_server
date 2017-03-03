package de.fesere.http.controllers;

import de.fesere.http.request.HttpRequest;
import de.fesere.http.response.HttpResponse;

import static de.fesere.http.response.HttpResponse.response;
import static de.fesere.http.response.StatusLine.MOVED_PERMANENTLY;

public class RedirectController extends Controller {
  @Override
  public HttpResponse doGet(HttpRequest request) {
    return response(MOVED_PERMANENTLY).addHeader("Location", "/").build();
  }
}
