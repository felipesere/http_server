package de.fesere.http.controllers;

import de.fesere.http.request.HttpRequest;
import de.fesere.http.response.HttpResponse;

import java.util.List;
import java.util.Map;

import static de.fesere.http.Utils.flatten;
import static de.fesere.http.response.HttpResponse.response;
import static de.fesere.http.response.StatusLine.OK;

public class ParameterController extends Controller {
  @Override
  public HttpResponse doGet(HttpRequest request) {
    Map<String, String> queryParams = request.getPath().getQueryParams();
    List<String> lines = flatten(queryParams, " = ");
    return response(OK).withBody(lines).build();
  }
}
