package de.fesere.http.controllers;

import de.fesere.http.request.HttpRequest;
import de.fesere.http.request.RequestBuilder;
import de.fesere.http.response.HttpResponse;
import org.junit.Test;

import static de.fesere.http.matchers.HttpResponseMatchers.hasBody;
import static de.fesere.http.matchers.HttpResponseMatchers.hasStatusCode;
import static de.fesere.http.request.Method.GET;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class CoffeeControllerTest {

    @Test
    public void itIsATeapot() {
        HttpRequest request = new RequestBuilder(GET, "/coffee").build();
        CoffeeController controller = new CoffeeController();
        HttpResponse response = controller.doGet(request);

        assertThat(response, hasBody(equalTo("I'm a teapot")));
        assertThat(response, hasStatusCode(418));
    }

}