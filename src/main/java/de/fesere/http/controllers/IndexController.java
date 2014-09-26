package de.fesere.http.controllers;

import de.fesere.http.request.HttpRequest;
import de.fesere.http.response.HttpResponse;

import static de.fesere.http.response.StatusLine.OK;

public class IndexController extends Controller {
  @Override
  public HttpResponse doGet(HttpRequest request) {
    return new HttpResponse(OK);
  }
}
