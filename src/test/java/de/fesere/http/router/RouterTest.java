package de.fesere.http.router;

import de.fesere.http.controllers.Controller;
import de.fesere.http.controllers.NotFoundController;
import de.fesere.http.request.Path;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.function.Function;

import static de.fesere.http.request.Path.path;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class RouterTest {

  private final Controller controller = new Controller() {};
  private final Router router = new Router();

  @Test
  public void itCanRegisterANewController() {
    router.register("/sample", controller);
    assertThat(router.controllerFor(path("/sample")), is(controller));
  }

  @Test(expected = ControllerAlreadyExistsException.class)
  public void itCanNotRegisterControllerTwice() {
    router.register("/sample", controller);
    router.register("/sample", controller);
  }

  @Test
  public void invalidPathResultsInNotFoundController() {
    Controller notFodund = router.controllerFor(path("/sample"));
    assertTrue(notFodund instanceof NotFoundController);
  }

  @Test
  public void dynamicControllerFoundToHandleSubpath() {
    Controller handlePath = controller(s -> s.getFullpath().startsWith("/sample"));
    router.rootCoontroler(handlePath);
    assertThat(router.controllerFor(path("/sample/subpath")), is(handlePath));
    assertThat(router.controllerFor(path("/other")), is(Matchers.<Object>instanceOf(NotFoundController.class)));
  }

  private Controller controller(Function<Path, Boolean> canHandle) {
    return new Controller() {
      @Override
      public boolean canHandle(Path path) {
        return canHandle.apply(path);
      }
    };
  }
}