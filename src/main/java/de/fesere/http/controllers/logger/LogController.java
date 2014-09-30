package de.fesere.http.controllers.logger;

import de.fesere.http.controllers.Controller;
import de.fesere.http.request.Authentication;
import de.fesere.http.request.HttpRequest;
import de.fesere.http.response.HttpResponse;

import java.util.List;

import static de.fesere.http.response.HttpResponse.response;
import static de.fesere.http.response.StatusLine.OK;
import static de.fesere.http.response.StatusLine.UNAUTHORIZED;

public class LogController extends Controller {
  @Override
  public HttpResponse doGet(HttpRequest request) {
    List<String> logs = Logger.read();
    if(authorized(request)) {
      return response(OK).withBody(logs).build();
    }
    else {
      return response(UNAUTHORIZED).withBody("Authentication required")
              .addHeader("WWW-Authetniticate", "Basic real=\"littleServer\"")
              .build();
    }
  }

  private boolean authorized(HttpRequest request) {
    Authentication authentication = new Authentication(request);
    return authentication.canBeAuthenticated() && authentication.isAuthenticatedAs("admin", "hunter2") ;
  }
}
