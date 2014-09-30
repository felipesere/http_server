package de.fesere.http.request;


import org.apache.commons.codec.binary.Base64;

import java.nio.charset.Charset;

public class Authentication {
  private HttpRequest request;

  public Authentication(HttpRequest request) {

    this.request = request;
  }

  public boolean canBeAuthenticated() {
    return request.getHeaders().containsKey("Authorization");
  }

  public boolean isAuthenticatedAs(String username, String password) {
    String encoded = request.getHeaders().get("Authorization").replaceFirst("Basic ","");
    String decoded = new String(Base64.decodeBase64(encoded), Charset.defaultCharset());
    String [] credentials = decoded.split(":");
    return username.equals(credentials[0]) && password.equals(credentials[1]);
  }
}
