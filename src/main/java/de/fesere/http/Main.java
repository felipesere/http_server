package de.fesere.http;

import de.fesere.http.controllers.FormController;
import de.fesere.http.router.Router;
import de.fesere.http.vfs.VirtualFileSystem;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
  public static void main(String[] args) {
    VirtualFileSystem vfs = new VirtualFileSystem();
    Router router = new Router();
    router.register("/form", new FormController(vfs));

    try {
      final ExecutorService executorService = Executors.newFixedThreadPool(30);
      ServerSocket server = new ServerSocket(5000);
      while (true) {
        Socket clientSocket = server.accept();
        executorService.submit(new Worker(clientSocket,router, vfs));
      }
    } catch (IOException e) {
      System.exit(-1);
    }
  }
}
