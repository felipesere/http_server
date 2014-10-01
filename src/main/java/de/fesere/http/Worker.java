package de.fesere.http;

import de.fesere.http.controllers.Controller;
import de.fesere.http.parsers.StreamingParser;
import de.fesere.http.request.HttpRequest;
import de.fesere.http.response.HttpResponse;
import de.fesere.http.router.Router;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

import static de.fesere.http.response.HttpResponse.response;
import static de.fesere.http.response.StatusLine.INTERNAL_SERVER_ERROR;

public class Worker implements Runnable {
  private final Router router;
  private final Socket clientSocket;

  public Worker(Socket clientSocket, Router router) {
    this.clientSocket = clientSocket;
    this.router = router;
  }

  @Override
  public void run() {
    try {
      StreamingParser parser = new StreamingParser(clientSocket.getInputStream());

      HttpRequest httpRequest = parser.readRequest();
      HttpResponse response = process(httpRequest);

      sendResponse(response);

      clientSocket.close();
    } catch (Exception e) {
      trySend(response(INTERNAL_SERVER_ERROR).withBody(e.getMessage()).build());
    }
  }

  private void trySend(HttpResponse response) {
    try {
      sendResponse(response);
    } catch (IOException e1) {
      throw new RuntimeException(e1);
    }
  }

  private void sendResponse(HttpResponse response) throws IOException {
    BufferedOutputStream bos = new BufferedOutputStream(clientSocket.getOutputStream());
    bos.write(response.toBytes());
    bos.flush();
  }

  private HttpResponse process(HttpRequest httpRequest) {
    Controller controller = router.controllerFor(httpRequest.getPath());
    return controller.dispatch(httpRequest);
  }
}
