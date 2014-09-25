package de.fesere.http;

import de.fesere.http.parsers.StreamingParser;
import de.fesere.http.request.HttpRequest;
import de.fesere.http.response.HttpResponse;
import de.fesere.http.response.StatusLine;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import static de.fesere.http.Method.OPTIONS;

public class Worker implements Runnable {
  private Socket clientSocket;

  public Worker(Socket clientSocket) {
    this.clientSocket = clientSocket;
  }

  @Override
  public void run() {
    try {
      PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
      StreamingParser parser = new StreamingParser(clientSocket.getInputStream());
      HttpRequest httpRequest = parser.read();

      StatusLine status = new StatusLine(200, "OK");
      Map<String, String> header = new HashMap<>();

      if(httpRequest.getRequestLine().getMethod() == OPTIONS) {
        header.put("Allow", "GET,HEAD,POST,PUT,OPTIONS");
      }
      HttpResponse response = new HttpResponse(status,header, "");
      out.println(response.printable());
      clientSocket.close();
    } catch (IOException e) {
    }
  }
}
