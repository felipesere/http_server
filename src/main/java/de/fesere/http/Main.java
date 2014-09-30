package de.fesere.http;

import de.fesere.http.controllers.*;
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
    if(!arguments.containsKey("-d")) {
      throw new RuntimeException("No root folder provided!");
    }
    VirtualFileSystem vfs = new VirtualFileSystem(arguments.get("-d"));
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
