package de.fesere.http.controllers;

import de.fesere.http.BaseController;
import de.fesere.http.request.HttpRequest;
import de.fesere.http.response.HttpResponse;

import java.util.HashMap;
import java.util.Map;

import static de.fesere.http.response.StatusLine.OK;

public class MethodOptionsController extends BaseController {
  @Override
  public HttpResponse doOptions(HttpRequest request) {
    Map<String, String> header = new HashMap<>();
    header.put("Allow", "GET,HEAD,POST,PUT,OPTIONS");

    return new HttpResponse(OK,header, "" );
  }
}
