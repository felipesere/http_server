package de.fesere.http.controllers;

import de.fesere.http.matchers.HttpResponseMatchers;
import de.fesere.http.request.HttpRequest;
import de.fesere.http.request.Method;
import de.fesere.http.request.RequestBuilder;
import de.fesere.http.response.HttpResponse;
import org.junit.Test;

import static de.fesere.http.matchers.HttpResponseMatchers.hasBody;
import static de.fesere.http.matchers.HttpResponseMatchers.hasHeader;
import static de.fesere.http.matchers.HttpResponseMatchers.hasStatusCode;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

public class CookieControllerTest {

    @Test
    public void creatsACookie() {
        HttpRequest request = new RequestBuilder(Method.GET, "/cookie?type=chocolate").build();
        CookieController controller = new CookieController();
        HttpResponse httpResponse = controller.doGet(request);
        assertThat(httpResponse, hasStatusCode(200));
        assertThat(httpResponse, hasHeader("Set-Cookie", "abc"));
        assertThat(httpResponse, hasBody(containsString("Eat")));
    }

    @Test
    public void detectsACookieAndChangesAnswer() {
        HttpRequest request = new RequestBuilder(Method.GET, "/cookie").addHeader("Cookie", "abc").build();
        CookieController controller = new CookieController();
        HttpResponse httpResponse = controller.doGet(request);
        assertThat(httpResponse, hasStatusCode(200));
        assertThat(httpResponse, hasBody(containsString("mmmm chocolate")));
    }
}