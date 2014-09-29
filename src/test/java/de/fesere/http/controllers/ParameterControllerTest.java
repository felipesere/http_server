package de.fesere.http.controllers;

import de.fesere.http.request.HttpRequest;
import org.junit.Test;

import static de.fesere.http.matchers.HttpResponseMatchers.hasStatusCode;
import static de.fesere.http.request.HttpRequest.*;
import static de.fesere.http.request.Method.GET;
import static org.hamcrest.MatcherAssert.assertThat;

public class ParameterControllerTest {

  @Test
  public void respondsToGET() {
    HttpRequest request = request(GET, "/paramters").build();

    Controller controller = new ParameterController();
    assertThat(controller.doGet(request), hasStatusCode(200));
  }
}
