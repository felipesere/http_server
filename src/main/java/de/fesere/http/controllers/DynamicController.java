package de.fesere.http.controllers;

public interface DynamicController extends Controller {
  boolean canHandle(String path);
}
