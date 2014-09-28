package de.fesere.http.matchers;

import de.fesere.http.response.HttpResponse;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

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
}
