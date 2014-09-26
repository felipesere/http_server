package de.fesere.http.router;

import de.fesere.http.controllers.Controller;
import de.fesere.http.controllers.NotFoundController;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Router {
  private Map<String, Controller> controllers = new HashMap<>();
  private Controller rootController = new NotFoundController();

  public Controller controllerFor(String path) {
    String basePath = getBase(path);
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

  private String getBase(String path) {
    if (path.equals("/")) {
      return path;
    } else {
      Pattern p = Pattern.compile("^(/[^/]+)");
      Matcher m = p.matcher(path);
      m.find();
      return m.group(0);
    }
  }

  public void rootCoontroler(Controller handlePath) {
    this.rootController = handlePath;
  }
}
