package de.fesere.http.router;

import de.fesere.http.Controller;

import java.util.HashMap;
import java.util.Map;

public class Router {
  private Map<String, Controller> controllers = new HashMap<>();
  public Controller controllerFor(String path) {
    if(controllers.containsKey(path)) {
      return controllers.get(path);
    } else {
      return new NotFoundController();
    }
  }

  public void register(String path, Controller controller) {
    failWhenControllerExists(path);
    controllers.put(path, controller);
  }

  private void failWhenControllerExists(String path) {
    if(controllers.containsKey(path)) {
      throw new ControllerAlreadyExistsException(path);
    }
  }

  void reset() {

  }
}
