package de.fesere.http;

import de.fesere.http.controllers.Controller;
import de.fesere.http.parsers.StreamingParser;
import de.fesere.http.request.HttpRequest;
import de.fesere.http.response.HttpResponse;
import de.fesere.http.router.MethodDispatcher;
import de.fesere.http.router.Router;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Worker implements Runnable {
  private final Router router;
  private final Socket clientSocket;
  private final MethodDispatcher dispatcher;

  public Worker(Socket clientSocket, Router router) {
    this.clientSocket = clientSocket;
    this.router = router;
    this.dispatcher = new MethodDispatcher();
  }

  @Override
  public void run() {
    try {
      StreamingParser parser = new StreamingParser(clientSocket.getInputStream());
      HttpRequest httpRequest = parser.read();

      HttpResponse response = processController(httpRequest);

      BufferedOutputStream bos = new BufferedOutputStream(clientSocket.getOutputStream());
      bos.write(response.toBytes());
      bos.flush();

      clientSocket.close();
    } catch (IOException e) {
    }
  }

  private HttpResponse processController(HttpRequest httpRequest) {
    Controller controller = router.controllerFor(httpRequest.getPath());
    return dispatcher.dispatch(controller, httpRequest);
  }
}
