package de.fesere.http.controllers;

import de.fesere.http.BaseController;
import de.fesere.http.request.HttpRequest;
import de.fesere.http.response.HttpResponse;
import de.fesere.http.response.StatusLine;

public class IndexController extends BaseController{
  @Override
  public HttpResponse doGet(HttpRequest request) {
    return new HttpResponse(new StatusLine(200, "OK"));
  }
}
