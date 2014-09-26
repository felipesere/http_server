package de.fesere.http.router;

import de.fesere.http.controllers.Controller;
import de.fesere.http.controllers.NotFoundController;
import org.junit.Test;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class RouterTest {

  private final Controller controller = new Controller() {};
  private final Router router = new Router();

  @Test
  public void itCanRegisterANewController() {
    router.register("/sample", controller);
    assertThat(router.controllerFor("/sample"), is(controller));
  }

  @Test
  public void itCanRegisterANewDynamicController() {
    router.registerDynamic("/sample", controller);
    assertThat(router.controllerFor("/sample"),is(controller));
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