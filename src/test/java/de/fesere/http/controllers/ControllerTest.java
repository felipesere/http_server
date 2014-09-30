package de.fesere.http.controllers;

import de.fesere.http.request.HttpRequest;
import de.fesere.http.response.HttpResponse;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static de.fesere.http.matchers.HttpResponseMatchers.hasStatusCode;
import static de.fesere.http.request.HttpRequest.request;
import static de.fesere.http.request.Method.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ControllerTest {

  private final HttpRequest get = request(GET, "/foo").build();
  private final HttpRequest post = request(POST, "/foo").build();
  private final HttpRequest put = request(PUT, "/foo").build();
  private final HttpRequest delete = request(DELETE, "/foo").build();
  private final HttpRequest head = request(HEAD, "/foo").build();
  private final HttpRequest patch = request(PATCH, "/foo").build();
  private final HttpRequest options = request(OPTIONS, "/foo").build();

  @Test
  public void returnsMethodNotAllowedForAllMethods() {
    Controller controller = new Controller();
    assertThat(controller.dispatch(get), hasStatusCode(405));
    assertThat(controller.dispatch(post), hasStatusCode(405));
    assertThat(controller.dispatch(put), hasStatusCode(405));
    assertThat(controller.dispatch(delete), hasStatusCode(405));
    assertThat(controller.dispatch(head), hasStatusCode(405));
    assertThat(controller.dispatch(patch), hasStatusCode(405));
    assertThat(controller.dispatch(options), hasStatusCode(405));
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
    controller.dispatch(get);
    assertThat(counter.get(),is(1));
  }
}