package de.fesere.http;

import de.fesere.http.router.Router;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {
  private final int port;
  private final Router router;
  final ExecutorService executorService = Executors.newFixedThreadPool(30);

  public HttpServer(int port, Router router) {
    this.port = port;
    this.router = router;
  }

  public void start() {
    System.out.println("Starting the server on port " + port);
    try {
      ServerSocket server = new ServerSocket(port);
      while (true) {
        Socket clientSocket = server.accept();
        executorService.submit(new Worker(clientSocket,router));
      }
    } catch (IOException e) {
      stop();
      System.exit(-1);
    }
  }

  public void stop() {
    System.out.println("Stopping the server...");
    executorService.shutdownNow();
  }
}
