package de.fesere.http.router;

import de.fesere.http.controllers.BaseController;
import de.fesere.http.controllers.Controller;
import de.fesere.http.controllers.DynamicController;
import de.fesere.http.controllers.NotFoundController;
import de.fesere.http.request.HttpRequest;
import de.fesere.http.response.HttpResponse;
import org.junit.Test;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class RouterTest {

  private final Controller controller = new BaseController() {};
  private final DynamicController dynamicController = new DynamicController() {
    @Override
    public boolean canHandle(String path) {
      return path.equals("/sample");
    }

    @Override
    public HttpResponse doPost(HttpRequest request) {
      return null;
    }

    @Override
    public HttpResponse doPut(HttpRequest request) {
      return null;
    }

    @Override
    public HttpResponse doGet(HttpRequest request) {
      return null;
    }

    @Override
    public HttpResponse doDelete(HttpRequest request) {
      return null;
    }

    @Override
    public HttpResponse doHead(HttpRequest request) {
      return null;
    }

    @Override
    public HttpResponse doOptions(HttpRequest request) {
      return null;
    }

    @Override
    public HttpResponse doPatch(HttpRequest request) {
      return null;
    }
  };
  private final Router router = new Router();

  @Test
  public void itCanRegisterANewController() {
    router.register("/sample", controller);
    assertThat(router.controllerFor("/sample"), is(controller));
  }

  @Test
  public void itCanRegisterANewDynamicController() {
    router.registerDynamic("/sample", dynamicController);
    assertThat(router.controllerFor("/sample"),is(instanceOf(DynamicController.class)));
  }

  @Test(expected = ControllerAlreadyExistsException.class)
  public void itCanNotRegisterControllerTwice() {
    router.register("/sample", controller);
    router.register("/sample", controller);
  }

  @Test
  public void invalidPathResultsInNotFoundController() {
    Controller notFodund = router.controllerFor("/sample");
    assertThat(notFodund, is(instanceOf(NotFoundController.class)));
  }
}