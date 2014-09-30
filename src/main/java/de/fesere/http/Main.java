package de.fesere.http;

import de.fesere.http.controllers.FormController;
import de.fesere.http.controllers.MethodOptionsController;
import de.fesere.http.controllers.ParameterController;
import de.fesere.http.controllers.RedirectController;
import de.fesere.http.controllers.filesystem.StaticResourcesController;
import de.fesere.http.controllers.logger.LogController;
import de.fesere.http.router.Router;
import de.fesere.http.vfs.VirtualFileSystem;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
  public static void main(String[] args) {
    Arguments arguments = new Arguments(args);
    int port = arguments.getInteger("-p");
    String root = arguments.getString("-d");

    VirtualFileSystem vfs = new VirtualFileSystem(root);
    vfs.preload();

    Router router = new Router();
    router.rootCoontroler(new StaticResourcesController(vfs));
    router.register("/form", new FormController(vfs));
    router.register("/logs", new LogController());
    router.register("/method_options", new MethodOptionsController());
    router.register("/redirect", new RedirectController());
    router.register("/parameters", new ParameterController());

    try {
      final ExecutorService executorService = Executors.newFixedThreadPool(30);
      ServerSocket server = new ServerSocket(port);
      while (true) {
        Socket clientSocket = server.accept();
        executorService.submit(new Worker(clientSocket,router));
      }
    } catch (IOException e) {
      System.exit(-1);
    }
  }

}
