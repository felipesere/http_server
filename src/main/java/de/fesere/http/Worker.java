package de.fesere.http;

import de.fesere.http.parsers.StreamingParser;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

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
      HttpRequest read = parser.read();

      out.println("HTTP/1.1 200 OK");
      clientSocket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
