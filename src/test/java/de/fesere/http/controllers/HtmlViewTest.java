package de.fesere.http.controllers;

import org.junit.Test;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class HtmlViewTest {

  @Test
  public void rendersMultipleLinks() {
    String expected = "<html><body><a href=\"/sample\">sample</a></br><a href=\"/other\">other</a></br></body></html>";
    HtmlView view = new HtmlView();
    assertThat(view.render(asList("/sample", "/other")), is(expected));
  }
}