package de.fesere.http.parsers;

import de.fesere.http.FileReaderTestBase;
import de.fesere.http.HttpRequest;
import org.junit.Test;

import static de.fesere.http.HttpVersion.HTTP_11;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

public class RequestParserTest extends FileReaderTestBase {

  @Test
  public void parseASimpleGETWithTwoHeaders() {
    RequestParser parser = new RequestParser();
    HttpRequest request = parser.read(readFile("/get.txt"));
    assertThat(request.getStatusLine().getHttpVersion(), is(HTTP_11));
    assertThat(request.getHeaders().values(), hasSize(2));
  }

  @Test
  public void parseAPOSTwithABody() {
    RequestParser parser = new RequestParser();
    HttpRequest request = parser.read(readFile("/post.txt"));
    assertThat(request.getStatusLine().getHttpVersion(), is(HTTP_11));
    assertThat(request.getHeaders().values(), hasSize(4));
    assertThat(request.getBody(), hasSize(3));
  }
}