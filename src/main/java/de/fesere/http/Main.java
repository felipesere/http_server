package de.fesere.http;

import de.fesere.http.controllers.FormController;
import de.fesere.http.controllers.MethodOptionsController;
import de.fesere.http.controllers.RedirectController;
import de.fesere.http.controllers.StaticResourcesController;
import de.fesere.http.router.Router;
import de.fesere.http.vfs.VirtualFileSystem;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
  public static void main(String[] args) {
    Map<String, String> arguments = parseArgs(args);
    VirtualFileSystem vfs = new VirtualFileSystem();
    if(arguments.containsKey("-d")) {
      vfs.preload(arguments.get("-d"));
    }
    Router router = new Router();
    //router.register("/", new IndexController());
    router.register("/form", new FormController(vfs));
    router.register("/method_options", new MethodOptionsController());
    router.register("/redirect", new RedirectController());
    router.registerDynamic("/", new StaticResourcesController(vfs));

    try {
      final ExecutorService executorService = Executors.newFixedThreadPool(30);
      ServerSocket server = new ServerSocket(5000);
      while (true) {
        Socket clientSocket = server.accept();
        executorService.submit(new Worker(clientSocket,router));
      }
    } catch (IOException e) {
      System.exit(-1);
    }
  }

  private static Map<String, String> parseArgs(String[] args) {
    Map<String, String> result = new HashMap<>();
    for (int i = 0; i < args.length; i += 2) {
     result.put(args[i], args[i+1]);
    }
    return result;
  }
}
