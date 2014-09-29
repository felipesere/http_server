package de.fesere.http.controllers;

import de.fesere.http.request.HttpRequest;
import de.fesere.http.response.HttpResponse;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static de.fesere.http.response.HttpResponse.response;
import static de.fesere.http.response.StatusLine.OK;

public class ParameterController extends Controller {
  @Override
  public HttpResponse doGet(HttpRequest request) {
    Map<String, String> queryParams = request.getPath().getQueryParams();
    List<String> lines = flatten(queryParams);
    return response(OK).withBody(lines).build();
  }

  private List<String> flatten(Map<String, String> queryParams) {
    List<String> result = new LinkedList<>();
    for(Map.Entry<String, String> query : queryParams.entrySet()) {
      result.add(query.getKey() + " = " + query.getValue());
    }
    return result;
  }
}
