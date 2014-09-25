package de.fesere.http;

import de.fesere.http.request.HttpRequest;
import de.fesere.http.response.HttpResponse;

import static de.fesere.http.response.StatusLine.METHOD_NOT_ALLOWED;

public class BaseController implements Controller {

  private final HttpResponse methodNotAllowed = new HttpResponse(METHOD_NOT_ALLOWED);
  @Override
  public HttpResponse doPost(HttpRequest request) {
    return methodNotAllowed;
  }

  @Override
  public HttpResponse doPut(HttpRequest request) {
    return methodNotAllowed;
  }

  @Override
  public HttpResponse doGet(HttpRequest request) {
    return methodNotAllowed;
  }

  @Override
  public HttpResponse doDelete(HttpRequest request) {
    return methodNotAllowed;
  }

  @Override
  public HttpResponse doHead(HttpRequest request) {
    return methodNotAllowed;
  }

  @Override
  public HttpResponse doOptions(HttpRequest request) {
    return methodNotAllowed;
  }

  @Override
  public HttpResponse doPatch(HttpRequest request) {
    return methodNotAllowed;
  }
}
