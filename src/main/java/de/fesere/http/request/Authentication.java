package de.fesere.http.request;


import org.apache.commons.codec.binary.Base64;

import java.nio.charset.Charset;

public class Authentication {
  private final HttpRequest request;

  public Authentication(HttpRequest request) {
    this.request = request;
  }

  public boolean canBeAuthenticated() {
    return request.getHeaders().containsKey("Authorization");
  }

  public boolean isAuthenticatedAs(String username, String password) {
    String[] credentials = extractCredentials();
    return username.equals(credentials[0]) && password.equals(credentials[1]);
  }

  private String[] extractCredentials() {
    String encoded = request.getHeaders().get("Authorization").replaceFirst("Basic ","");
    String decoded = decodeBase64(encoded);
    return decoded.split(":");
  }

  private String decodeBase64(String encoded) {
    return new String(Base64.decodeBase64(encoded), Charset.defaultCharset());
  }
}
