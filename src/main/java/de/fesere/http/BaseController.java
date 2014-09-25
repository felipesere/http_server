package de.fesere.http;

import de.fesere.http.request.HttpRequest;
import de.fesere.http.response.HttpResponse;
import de.fesere.http.response.StatusLine;

public class BaseController implements Controller {

  private final HttpResponse methodNotAllowed = new HttpResponse(new StatusLine(405, "Method Not Allowed"));
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
