package de.fesere.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
      BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      for (int i = 0; i < 10; i++) {
        String line = reader.readLine();
        if (line.trim().equals("")) {
          break;
        }
      }

      out.println("HTTP/1.1 200 OK");
      clientSocket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
