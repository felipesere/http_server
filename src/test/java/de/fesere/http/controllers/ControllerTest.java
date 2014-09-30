package de.fesere.http.controllers;

import de.fesere.http.request.HttpRequest;
import de.fesere.http.response.HttpResponse;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static de.fesere.http.request.Method.GET;
import static de.fesere.http.matchers.HttpResponseMatchers.hasStatusCode;
import static de.fesere.http.request.HttpRequest.request;
import static org.hamcrest.CoreMatchers.is;
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

  @Test
  public void dispatchesRequestsToTheAppropiateMethod() {
    AtomicInteger counter = new AtomicInteger(0);
    Controller controller = new Controller() {
      @Override
      public HttpResponse doGet(HttpRequest request) {
        counter.getAndIncrement();
        return super.doGet(request);
      }
    };
    controller.dispatch(httpRequest);
    assertThat(counter.get(),is(1));
  }
}