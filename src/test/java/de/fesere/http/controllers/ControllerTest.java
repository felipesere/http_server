package de.fesere.http.controllers;

import de.fesere.http.request.HttpRequest;
import de.fesere.http.request.RequestLine;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedList;

import static de.fesere.http.HttpVersion.HTTP_11;
import static de.fesere.http.Method.GET;
import static de.fesere.http.matchers.HttpResponseMatchers.respondsWithStatus;
import static org.hamcrest.MatcherAssert.assertThat;

public class ControllerTest {

  private final HttpRequest request = new HttpRequest(new RequestLine(GET, "/foo", HTTP_11), new HashMap<String, String>(), new LinkedList<String>());

  @Test
  public void returnsMethodNotAllowedForAllMethods() {
    Controller controller = new Controller();
    assertThat(controller.doGet(request), respondsWithStatus(405));
    assertThat(controller.doPost(request), respondsWithStatus(405));
    assertThat(controller.doPut(request), respondsWithStatus(405));
    assertThat(controller.doDelete(request), respondsWithStatus(405));
    assertThat(controller.doHead(request), respondsWithStatus(405));
    assertThat(controller.doPatch(request), respondsWithStatus(405));
    assertThat(controller.doOptions(request), respondsWithStatus(405));
  }
}