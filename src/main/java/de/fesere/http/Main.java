package de.fesere.http;

import de.fesere.http.controllers.FormController;
import de.fesere.http.controllers.MethodOptionsController;
import de.fesere.http.controllers.ParameterController;
import de.fesere.http.controllers.RedirectController;
import de.fesere.http.controllers.filesystem.StaticResourcesController;
import de.fesere.http.controllers.logger.LogController;
import de.fesere.http.router.Router;
import de.fesere.http.vfs.VirtualFileSystem;

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

    HttpServer server = new HttpServer(port, router);
    onShutdown(server::stop);
    server.start();
  }

  private static void onShutdown(Runnable runnable) {
    Runtime.getRuntime().addShutdownHook(new Thread(runnable));
  }
}
