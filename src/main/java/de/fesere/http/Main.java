package de.fesere.http;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
  public static void main(String[] args) {

    try {
      final ExecutorService executorService = Executors.newFixedThreadPool(30);
      while(true) {
        Socket clientSocket = new ServerSocket(5000).accept();
        executorService.submit(new Worker(clientSocket));
      }
    } catch (IOException e) {
      System.exit(-1);
    }
  }
}
