package de.fesere.http.controllers;

import de.fesere.http.request.HttpRequest;
import de.fesere.http.response.HttpResponse;

import static de.fesere.http.response.HttpResponse.response;
import static de.fesere.http.response.StatusLine.OK;

public class MethodOptions2Controller extends Controller {

    @Override
    public HttpResponse doOptions(HttpRequest request) {
        return response(OK).addHeader("Allow","GET,OPTIONS").build();
    }
}
