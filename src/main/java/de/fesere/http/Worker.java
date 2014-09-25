package de.fesere.http;

import de.fesere.http.parsers.StreamingParser;
import de.fesere.http.request.HttpRequest;
import de.fesere.http.response.HttpResponse;
import de.fesere.http.response.StatusLine;
import de.fesere.http.router.Router;
import de.fesere.http.vfs.VirtualFileSystem;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import static de.fesere.http.Method.OPTIONS;

public class Worker implements Runnable {
  private final Router router;
  private final Socket clientSocket;
  private final VirtualFileSystem fileSystem;

  public Worker(Socket clientSocket,Router router, VirtualFileSystem fileSystem) {
    this.clientSocket = clientSocket;
    this.router = router;
    this.fileSystem = fileSystem;
  }

  @Override
  public void run() {
    try {
      PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
      StreamingParser parser = new StreamingParser(clientSocket.getInputStream());
      HttpRequest httpRequest = parser.read();


      HttpResponse response = null;
      if (httpRequest.getRequestLine().getMethod() == OPTIONS) {
        Map<String, String> header = new HashMap<>();
        header.put("Allow", "GET,HEAD,POST,PUT,OPTIONS");

        response = new HttpResponse(new StatusLine(200, "OK"),header, "" );
      } else if (httpRequest.getRequestLine().getPath().equals("/form")) {
        response = handleFormPath(httpRequest);
      }

      if(response == null) {
        response = new HttpResponse(new StatusLine(200, "OK"));
      }


      out.println(response.printable());
      clientSocket.close();
    } catch (IOException e) {
    }
  }

  private HttpResponse handleFormPath(HttpRequest httpRequest) {
    Controller controller = router.controllerFor(httpRequest.getRequestLine().getPath());
    Method method = httpRequest.getRequestLine().getMethod();
    switch (method) {
      case POST:
        return controller.doPost(httpRequest);
      case GET:
        return controller.doGet(httpRequest);
      case PUT:
        return controller.doPut(httpRequest);
      case DELETE:
        return controller.doDelete(httpRequest);
      default:
        throw new RuntimeException("Unexpected Method");
    }
  }
}
