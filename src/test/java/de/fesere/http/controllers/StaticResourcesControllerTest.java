package de.fesere.http.controllers;

import de.fesere.http.request.HttpRequest;
import de.fesere.http.request.RequestLine;
import de.fesere.http.response.HttpResponse;
import de.fesere.http.vfs.VirtualFileSystem;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedList;

import static de.fesere.http.HttpVersion.HTTP_11;
import static de.fesere.http.Method.GET;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class StaticResourcesControllerTest {

  @Test
  public void testReadFileFromRootDirectory() {
    VirtualFileSystem vfs = new VirtualFileSystem();
    vfs.create("/file1");
    vfs.writeTo("/file1", asList("Foo", "Bar"));

    Controller controller = new StaticResourcesController(vfs);
    assertThat(controller.canHandle("/file1"), is(true));
    assertThat(controller.doGet(request("/file1")), hasBody(containsString("Foo"), containsString("Bar")));
  }

  private Matcher<HttpResponse> hasBody(Matcher<String>... matchers) {
    return new TypeSafeMatcher<HttpResponse>() {
      public Matcher<String> failedMatcher = null;

      @Override
      protected boolean matchesSafely(HttpResponse item) {
        if (item.getBody().isEmpty()) {
          return false;
        } else {
          for(Matcher<String> matcher : matchers) {
           if(!matcher.matches(item.getBody())) {
             failedMatcher = matcher;
             return false;
           }
          }
        }
        return true;
      }


      @Override
      public void describeTo(Description description) {
        description.appendText("Expected body of request to match ");
        for(Matcher<String> matcher : matchers) {
          matcher.describeTo(description);
        }
      }

    };
  }

  private HttpRequest request(String path) {
    return new HttpRequest(new RequestLine(GET, path, HTTP_11), new HashMap<>(), new LinkedList<>());
  }
}