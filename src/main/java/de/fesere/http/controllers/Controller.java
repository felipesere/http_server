package de.fesere.http.controllers;

import de.fesere.http.request.HttpRequest;
import de.fesere.http.response.HttpResponse;

public interface Controller {
  HttpResponse doPost(HttpRequest request);
  HttpResponse doPut(HttpRequest request);
  HttpResponse doGet(HttpRequest request);
  HttpResponse doDelete(HttpRequest request);
  HttpResponse doHead(HttpRequest request);
  HttpResponse doOptions(HttpRequest request);
  HttpResponse doPatch(HttpRequest request);
}
