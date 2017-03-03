package de.fesere.http.controllers;

import de.fesere.http.request.HttpRequest;
import de.fesere.http.response.HttpResponse;
import de.fesere.http.response.ResponseBuilder;

import static de.fesere.http.response.StatusLine.OK;

public class CookieController extends Controller {

    @Override
    public HttpResponse doGet(HttpRequest request) {
        if( request.getHeaders().containsKey("Cookie")) {
            return new ResponseBuilder(OK).withBody("mmmm chocolate").build();
        }
        return new ResponseBuilder(OK).addHeader("Set-Cookie", "abc") .withBody("Eat") .build();
    }
}
