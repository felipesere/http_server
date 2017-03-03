package de.fesere.http.matchers;

import de.fesere.http.response.HttpResponse;
import org.hamcrest.Description;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.Map;

import static org.hamcrest.core.IsEqual.equalTo;

public class HttpResponseMatchers {
  public static Matcher<HttpResponse> hasStatusCode(int statusCode) {
    return new FeatureMatcher<HttpResponse, Integer>(equalTo(statusCode), "statusCode", "statusCode") {
      @Override
      protected Integer featureValueOf(HttpResponse actual) {
        return actual.getStatusCode();
      }
    };
  }

  public static Matcher<HttpResponse> hasBody(Matcher<String> matcher) {
    return new FeatureMatcher<HttpResponse, String>(matcher, "body", "body" ) {
      @Override
      protected String featureValueOf(HttpResponse actual) {
        return actual.getBody();
      }
    };
  }

  public static Matcher<HttpResponse> hasHeader(String header, String value) {
    return new TypeSafeMatcher<HttpResponse>() {
      @Override
      protected boolean matchesSafely(HttpResponse item) {
        Map<String, String> responseHeaders = item.getHeader();
        if(responseHeaders.containsKey(header)) {
          return responseHeaders.get(header).equals(value);
        }
        return false;
      }

      @Override
      public void describeTo(Description description) {
          description.appendText("Did not find header " + header + " with value " + value);
      }
    };
  }
}
