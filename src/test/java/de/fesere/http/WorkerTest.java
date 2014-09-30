package de.fesere.http;

import de.fesere.http.router.Router;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;

import static de.fesere.http.response.HttpResponse.response;
import static de.fesere.http.response.StatusLine.INTERNAL_SERVER_ERROR;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WorkerTest {

  @Test
  public void exceptionResultsInInternalServerErrorResponse() throws IOException {
    Socket mockClientSocket = mock(Socket.class);
    when(mockClientSocket.getInputStream()).thenThrow(new RuntimeException("I should not propagate!"));

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    when(mockClientSocket.getOutputStream()).thenReturn(baos);

    Worker worker = new Worker(mockClientSocket, new Router());
    worker.run();
    String expected = response(INTERNAL_SERVER_ERROR).withBody("I should not propagate!").build().printable();
    assertThat(baos.toString(), is(equalTo(expected)));
  }
}