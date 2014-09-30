package de.fesere.http.controllers;

import de.fesere.http.request.HttpRequest;
import org.junit.Test;

import static de.fesere.http.matchers.HttpResponseMatchers.hasBody;
import static de.fesere.http.matchers.HttpResponseMatchers.hasStatusCode;
import static de.fesere.http.request.HttpRequest.*;
import static de.fesere.http.request.Method.GET;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;

public class ParameterControllerTest {

  @Test
  public void respondsToGET() {
    HttpRequest request = request(GET, "/paramters").build();

    Controller controller = new ParameterController();
    assertThat(controller.doGet(request), hasStatusCode(200));
  }

  @Test
  public void returnsQueryParamsInBody() {
    HttpRequest request = request(GET, "/paramters?foo=bar").build();

    Controller controller = new ParameterController();
    assertThat(controller.doGet(request), hasBody(allOf(containsString("foo"), containsString("bar"))));
  }
}
