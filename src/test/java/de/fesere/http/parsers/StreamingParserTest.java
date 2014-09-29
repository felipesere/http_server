package de.fesere.http.parsers;

import de.fesere.http.request.HttpRequest;
import org.junit.Test;

import java.io.InputStream;

import static de.fesere.http.HttpVersion.HTTP_11;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

public class StreamingParserTest {

  @Test
  public void canReadAGETrequest() {
    InputStream inputStream = getClass().getResourceAsStream("/requests/get.txt");
    StreamingParser parser = new StreamingParser(inputStream);
    HttpRequest request = parser.read();

    assertThat(request.getRequestLine().getHttpVersion(), is(HTTP_11));
    assertThat(request.getHeaders().values(), hasSize(2));
  }

  @Test
  public void canReadAPostRequest() {
    InputStream inputStream = getClass().getResourceAsStream("/requests/post.txt");
    StreamingParser parser = new StreamingParser(inputStream);
    HttpRequest request = parser.read();

    assertThat(request.getRequestLine().getHttpVersion(), is(HTTP_11));
    assertThat(request.getHeaders().values(), hasSize(4));
    assertThat(request.getBody(), hasSize(3));
  }
}