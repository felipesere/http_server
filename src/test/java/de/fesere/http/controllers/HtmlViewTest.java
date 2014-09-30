package de.fesere.http.controllers;

import org.junit.Test;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class HtmlViewTest {
  @Test
  public void rendersBasicHtmlDocument() {
    String expected = "<html><body></body></html>";

    HtmlView view = new HtmlView();
    assertThat(view.render(), is(expected));
  }

  @Test
  public void rendersSingleLink() {
    String expected = "<html><body><a href=\"/sample\">sample</a></body></html>";
    HtmlView view = new HtmlView();
    assertThat(view.render("/sample"), is(expected));
  }

  @Test
  public void rendersMultipleLinks() {
    String expected = "<html><body><a href=\"/sample\">sample</a><a href=\"/other\">other</a></body></html>";
    HtmlView view = new HtmlView();
    assertThat(view.render(asList("/sample", "/other")), is(expected));
  }
}