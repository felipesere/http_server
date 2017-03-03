package de.fesere.http.controllers;

import de.fesere.http.request.HttpRequest;
import de.fesere.http.response.HttpResponse;
import de.fesere.http.response.ResponseBuilder;
import de.fesere.http.response.StatusLine;

public class TeeController extends Controller {
    @Override
    public HttpResponse doGet(HttpRequest request) {
        return new ResponseBuilder(StatusLine.OK).build();
    }
}
