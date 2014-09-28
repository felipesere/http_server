package de.fesere.http.router;

import de.fesere.http.controllers.Controller;
import de.fesere.http.controllers.NotFoundController;
import de.fesere.http.request.Path;

import java.util.HashMap;
import java.util.Map;

public class Router {
  private Map<String, Controller> controllers = new HashMap<>();
  private Controller rootController = new NotFoundController();

  public Controller controllerFor(Path path) {
    String basePath = path.getBase();
    if (controllers.containsKey(basePath)) {
      return controllers.get(basePath);
    }
    if(rootController.canHandle(path)) {
      return rootController;
    }
    return new NotFoundController();
  }


  public void register(String path, Controller controller) {
    failWhenControllerExists(path);
    controllers.put(path, controller);
  }

  private void failWhenControllerExists(String path) {
    if (controllers.containsKey(path)) {
      throw new ControllerAlreadyExistsException(path);
    }
  }

  public void rootCoontroler(Controller handlePath) {
    this.rootController = handlePath;
  }
}
