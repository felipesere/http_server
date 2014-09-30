package de.fesere.http;

import de.fesere.http.controllers.Controller;
import de.fesere.http.parsers.StreamingParser;
import de.fesere.http.request.HttpRequest;
import de.fesere.http.response.HttpResponse;
import de.fesere.http.router.Router;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Worker implements Runnable {
  private final Router router;
  private final Socket clientSocket;
  private StreamingParser parser;
  private BufferedOutputStream bos;

  public Worker(Socket clientSocket, Router router) {
    this.clientSocket = clientSocket;
    this.router = router;
  }

  @Override
  public void run() {
    try {
      parser = new StreamingParser(clientSocket.getInputStream());

      HttpRequest httpRequest = parser.readRequest();
      HttpResponse response = process(httpRequest);

      sendResponse(response);

      clientSocket.close();
    } catch (IOException e) {
    }
  }

  private void sendResponse(HttpResponse response) throws IOException {
    bos = new BufferedOutputStream(clientSocket.getOutputStream());
    bos.write(response.toBytes());
    bos.flush();
  }

  private HttpResponse process(HttpRequest httpRequest) {
    Controller controller = router.controllerFor(httpRequest.getPath());
    return controller.dispatch(httpRequest);
  }
}
