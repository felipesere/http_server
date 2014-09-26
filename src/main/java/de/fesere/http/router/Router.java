package de.fesere.http.router;

import de.fesere.http.controllers.Controller;
import de.fesere.http.controllers.NotFoundController;

import java.util.HashMap;
import java.util.Map;

public class Router {
  private Map<String, Controller> controllers = new HashMap<>();
  private Map<String, Controller> dynamicControllers = new HashMap<>();

  public Controller controllerFor(String path) {
    String basePath = getBase(path);
    if (controllers.containsKey(basePath)) {
      return controllers.get(basePath);
    }
    if(dynamicControllers.containsKey(basePath)) {
       String remainingPath = getRemaining(path);
       if(dynamicControllers.get(basePath).canHandle(remainingPath)) {
         return dynamicControllers.get(basePath);
       }
    }
    return new NotFoundController();
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

  private String getBase(String path) {
    if(path.equals("/")) {
      return path;
    } else {
      int secondSlash = path.indexOf("/", 1);
      if(secondSlash > 0) {
        return path.substring(0, secondSlash);
      } else {
        return path;
      }
    }
  }

  private String getRemaining(String path) {
    if(path.equals("/")) {
      return path;
    } else {
      int secondSlash = path.indexOf("/", 1);
      if(secondSlash > 0) {
        return path.substring(secondSlash,path.length());
      } else {
        return path;
      }
    }
  }

  public void registerDynamic(String path, Controller controller) {
    dynamicControllers.put(path,controller);
  }
}
