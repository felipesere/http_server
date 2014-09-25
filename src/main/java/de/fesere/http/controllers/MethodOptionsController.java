package de.fesere.http.controllers;

import de.fesere.http.BaseController;
import de.fesere.http.request.HttpRequest;
import de.fesere.http.response.HttpResponse;
import de.fesere.http.response.StatusLine;

import java.util.HashMap;
import java.util.Map;

public class MethodOptionsController extends BaseController {
  @Override
  public HttpResponse doOptions(HttpRequest request) {
    Map<String, String> header = new HashMap<>();
    header.put("Allow", "GET,HEAD,POST,PUT,OPTIONS");

    return new HttpResponse(new StatusLine(200, "OK"),header, "" );
  }
}
