package de.fesere.http.controllers;

import de.fesere.http.request.HttpRequest;
import de.fesere.http.response.HttpResponse;

import java.util.Map;

import static de.fesere.http.response.HttpResponse.response;
import static de.fesere.http.response.StatusLine.OK;
import static de.fesere.http.utils.Utils.flatten;

public class ParameterController extends Controller {
  @Override
  public HttpResponse doGet(HttpRequest request) {
    Map<String, String> queryParams = request.getPath().getQueryParams();
    return response(OK).withBody(flatten(queryParams, " = ")).build();
  }
}
