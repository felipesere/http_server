package de.fesere.http.controllers;

import de.fesere.http.request.HttpRequest;
import org.junit.Test;

import static de.fesere.http.Method.GET;
import static de.fesere.http.matchers.HttpResponseMatchers.hasStatusCode;
import static de.fesere.http.request.HttpRequest.request;
import static org.hamcrest.MatcherAssert.assertThat;

public class ControllerTest {

  private final HttpRequest httpRequest = request(GET, "/foo").build();

  @Test
  public void returnsMethodNotAllowedForAllMethods() {
    Controller controller = new Controller();
    assertThat(controller.doGet(httpRequest), hasStatusCode(405));
    assertThat(controller.doPost(httpRequest), hasStatusCode(405));
    assertThat(controller.doPut(httpRequest), hasStatusCode(405));
    assertThat(controller.doDelete(httpRequest), hasStatusCode(405));
    assertThat(controller.doHead(httpRequest), hasStatusCode(405));
    assertThat(controller.doPatch(httpRequest), hasStatusCode(405));
    assertThat(controller.doOptions(httpRequest), hasStatusCode(405));
  }
}