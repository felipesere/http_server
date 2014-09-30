package de.fesere.http.controllers;

import de.fesere.http.request.HttpRequest;
import de.fesere.http.response.HttpResponse;
import de.fesere.http.response.StatusLine;

import static de.fesere.http.response.HttpResponse.response;

public class LogController extends Controller {
  @Override
  public HttpResponse doGet(HttpRequest request) {
    return response(StatusLine.UNAUTHORIZED).withBody("Authentication required").addHeader("WWW-Authetniticate", "Basic real=\"littleServer\"").build();
  }
}
