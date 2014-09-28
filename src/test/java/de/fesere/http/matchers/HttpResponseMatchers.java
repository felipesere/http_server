package de.fesere.http.matchers;

import de.fesere.http.response.HttpResponse;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class HttpResponseMatchers {
  public static Matcher<HttpResponse> hasStatusCode(int statusCode) {
   return new TypeSafeMatcher<HttpResponse>() {
     @Override
     protected boolean matchesSafely(HttpResponse item) {
       return item.getStatusCode() == statusCode;
     }

     @Override
     public void describeTo(Description description) {
      description.appendText("response to have had status code ").appendValue(statusCode);
     }

     @Override
     protected void describeMismatchSafely(HttpResponse item, Description mismatchDescription) {
       mismatchDescription.appendText("was ").appendValue(item.getStatusCode());
     }
   };
  }
  public static Matcher<HttpResponse> hasBody(Matcher<String>... matchers) {

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
}
