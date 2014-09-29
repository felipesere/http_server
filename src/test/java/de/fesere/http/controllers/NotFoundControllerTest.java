package de.fesere.http.controllers;

import de.fesere.http.request.HttpRequest;
import org.junit.Test;

import static de.fesere.http.Method.GET;
import static de.fesere.http.matchers.HttpResponseMatchers.hasStatusCode;
import static de.fesere.http.request.HttpRequest.request;
import static org.hamcrest.MatcherAssert.assertThat;

public class NotFoundControllerTest {

  private final HttpRequest httpRequest = request(GET, "/foo").build();

  @Test
  public void returnsMethodNotAllowedForAllMethods() {
    Controller controller = new NotFoundController();
    assertThat(controller.doGet(httpRequest), hasStatusCode(404));
    assertThat(controller.doPost(httpRequest), hasStatusCode(404));
    assertThat(controller.doPut(httpRequest), hasStatusCode(404));
    assertThat(controller.doDelete(httpRequest), hasStatusCode(404));
    assertThat(controller.doHead(httpRequest), hasStatusCode(404));
    assertThat(controller.doPatch(httpRequest), hasStatusCode(404));
    assertThat(controller.doOptions(httpRequest), hasStatusCode(404));
  }
}