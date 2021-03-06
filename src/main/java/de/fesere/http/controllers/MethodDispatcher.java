package de.fesere.http.controllers;

import de.fesere.http.request.HttpRequest;
import de.fesere.http.request.Method;
import de.fesere.http.response.HttpResponse;

public class MethodDispatcher {

  public HttpResponse dispatch(Controller controller, HttpRequest httpRequest) {
    Method method = httpRequest.getRequestLine().getMethod();
    switch (method) {
      case POST:
        return controller.doPost(httpRequest);
      case GET:
        return controller.doGet(httpRequest);
      case PUT:
        return controller.doPut(httpRequest);
      case DELETE:
        return controller.doDelete(httpRequest);
      case OPTIONS:
        return controller.doOptions(httpRequest);
      case HEAD:
        return controller.doHead(httpRequest);
      case PATCH:
        return controller.doPatch(httpRequest);
      case UNKNWON:
        return controller.unknown(httpRequest);
      default:
        throw new RuntimeException("Unexpected Method");
    }
  }
}
