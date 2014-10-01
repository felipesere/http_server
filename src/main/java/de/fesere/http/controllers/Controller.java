package de.fesere.http.controllers;

import de.fesere.http.controllers.logger.Logger;
import de.fesere.http.request.HttpRequest;
import de.fesere.http.request.Path;
import de.fesere.http.response.HttpResponse;

import static de.fesere.http.response.HttpResponse.response;
import static de.fesere.http.response.StatusLine.METHOD_NOT_ALLOWED;

public class Controller {

  private final HttpResponse methodNotAllowed = response(METHOD_NOT_ALLOWED).build();
  private final MethodDispatcher dispatcher = new MethodDispatcher();

  public final HttpResponse dispatch(HttpRequest request) {
    Logger.log(request.getRequestLine().toString());
    return dispatcher.dispatch(this, request);
  }

  public HttpResponse doPost(HttpRequest request) {
    return methodNotAllowed;
  }

  public HttpResponse doPut(HttpRequest request) {
    return methodNotAllowed;
  }

  public HttpResponse doGet(HttpRequest request) {
    return methodNotAllowed;
  }

  public HttpResponse doDelete(HttpRequest request) {
    return methodNotAllowed;
  }

  public HttpResponse doHead(HttpRequest request) {
    return methodNotAllowed;
  }

  public HttpResponse doOptions(HttpRequest request) {
    return methodNotAllowed;
  }

  public HttpResponse doPatch(HttpRequest request) {
    return methodNotAllowed;
  }

  public boolean canHandle(Path path) {
    return true;
  }
}
