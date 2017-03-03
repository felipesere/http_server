package de.fesere.http.controllers;

import de.fesere.http.request.HttpRequest;
import de.fesere.http.response.HttpResponse;
import de.fesere.http.response.ResponseBuilder;

import static de.fesere.http.response.StatusLine.TEAPOT;

public class CoffeeController extends Controller {

    @Override
    public HttpResponse doGet(HttpRequest request) {
        return new ResponseBuilder(TEAPOT).withBody("I'm a teapot").build();
    }
}
