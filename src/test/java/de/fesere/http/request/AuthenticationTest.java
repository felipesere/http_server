package de.fesere.http.request;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import static de.fesere.http.request.HttpRequest.request;
import static de.fesere.http.request.Method.GET;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class AuthenticationTest {

  @Test
  public void canNotBeAuthenticatedWithoutHeader() {
    HttpRequest request = request(GET, "/foo").build();
    Authentication authentication = new Authentication(request);
    assertThat(authentication.canBeAuthenticated(), is(false));
  }

  @Test
  public void canBeAuthenticatedWithHeader() {
    HttpRequest request = request(GET, "/foo").addHeader("Authorization", "sentinel").build();
    Authentication authentication = new Authentication(request);
    assertThat(authentication.canBeAuthenticated(), is(true));
  }

  @Test
  public void isNotAuthetnicatedAsAdmin() {
    String username = "felipe";
    String password = "sere";
    String encodedCredentials = Base64.encodeBase64String((username + ":" + password).getBytes());

    HttpRequest request = request(GET, "/foo").addHeader("Authorization", "Basic " + encodedCredentials).build();
    Authentication authentication = new Authentication(request);

    assertThat(authentication.isAuthenticatedAs("admin", "hunter2"), is(false));

  }

  @Test
  public void isAuthenticatedWhenUsernameAndPasswordMatch() {
    String username = "felipe";
    String password = "sere";
    String encodedCredentials = Base64.encodeBase64String((username + ":" + password).getBytes());

    HttpRequest request = request(GET, "/foo").addHeader("Authorization", "Basic " + encodedCredentials).build();
    Authentication authentication = new Authentication(request);

    assertThat(authentication.isAuthenticatedAs("felipe", "sere"), is(true));

  }
}