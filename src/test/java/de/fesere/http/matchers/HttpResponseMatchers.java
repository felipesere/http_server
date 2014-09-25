package de.fesere.http.matchers;

import de.fesere.http.response.HttpResponse;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class HttpResponseMatchers {
  public static Matcher<HttpResponse> respondsWithStatus(final int statusCode) {
    return new TypeSafeMatcher<HttpResponse>() {
      @Override
      protected boolean matchesSafely(HttpResponse item) {
        return item.getStatusCode() == statusCode;
      }

      @Override
      public void describeTo(Description description) {
        description.appendText("Expected HttpResponse with status code " + statusCode);
      }
    };
  }
}
