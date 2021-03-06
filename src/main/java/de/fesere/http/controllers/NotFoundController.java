package de.fesere.http.controllers;

import de.fesere.http.request.HttpRequest;
import de.fesere.http.response.HttpResponse;

import static de.fesere.http.response.HttpResponse.response;
import static de.fesere.http.response.StatusLine.NOT_FOUND;

public class NotFoundController extends Controller {
  private final HttpResponse notFound = response(NOT_FOUND).build();
  @Override
  public HttpResponse doPost(HttpRequest request) {
    return notFound;
  }

  @Override
  public HttpResponse doPut(HttpRequest request) {
    return notFound;
  }

  @Override
  public HttpResponse doGet(HttpRequest request) {
    return notFound;
  }

  @Override
  public HttpResponse doDelete(HttpRequest request) {
    return notFound;
  }

  @Override
  public HttpResponse doHead(HttpRequest request) {
    return notFound;
  }

  @Override
  public HttpResponse doOptions(HttpRequest request) {
    return notFound;
  }

  @Override
  public HttpResponse doPatch(HttpRequest request) {
    return notFound;
  }
}
