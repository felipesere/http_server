package de.fesere.http.router;

import de.fesere.http.Controller;
import de.fesere.http.request.HttpRequest;
import de.fesere.http.response.HttpResponse;
import de.fesere.http.response.StatusLine;

public class NotFoundController implements Controller {
  private final HttpResponse notFound = new HttpResponse(new StatusLine(404, "Not Found"));
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
