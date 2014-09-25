package de.fesere.http.router;

import de.fesere.http.BaseController;
import de.fesere.http.Controller;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class RouterTest {

  private Controller c = new BaseController() {};
  private Router router = new Router();

  @Before
  public void setup() {
    router.reset();
  }

  @Test
  public void itCanRegisterAnewRouter() {
    router.register("/sample", c);
    assertThat(router.controllerFor("/sample"),is(c));
  }

  @Test(expected = ControllerAlreadyExistsException.class)
  public void itCanNotRegisterControllerTwice() {
    router.register("/sample", c);
    router.register("/sample", c);
  }

  @Test
  public void invalidPathResultsInNotFoundController() {
    Controller notFodund = router.controllerFor("/someRandomPath");
    assertThat(notFodund, is(instanceOf(NotFoundController.class)));
  }
}