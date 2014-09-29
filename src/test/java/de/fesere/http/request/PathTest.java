package de.fesere.http.request;

import org.junit.Test;

import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsMapContaining.hasEntry;

public class PathTest {
  @Test
  public void ignoresQueryParametersForBase() {
    Path path = new Path("/parameters?foo=bar");
    assertThat(path.getBase(), is("/parameters"));
  }

  @Test
  public void canExtractQueryParameter() {
    Path path = new Path("/parameters?foo=bar");
    Map<String, String> queryParameters = path.getQueryParams();
    assertThat(queryParameters, hasEntry(equalTo("foo"), equalTo("bar")));
  }

  @Test
  public void canExtractMultipleQueryParameters() {
    Path path = new Path("/parameters?foo=bar&go=fish");
    Map<String, String> queryParameters = path.getQueryParams();
    assertThat(queryParameters, hasEntry(equalTo("foo"), equalTo("bar")));
    assertThat(queryParameters, hasEntry(equalTo("go"), equalTo("fish")));
  }

  @Test
  public void doesProperDecodingForParamters() {
    Path path = new Path("/parameters?foo=%20%3C%2C");
    Map<String, String> queryParameters = path.getQueryParams();
    assertThat(queryParameters, hasEntry(equalTo("foo"), equalTo(" <,")));
  }
}