package de.fesere.http.router;

import de.fesere.http.controllers.Controller;
import de.fesere.http.controllers.NotFoundController;
import de.fesere.http.request.Path;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Test;

import java.util.function.Function;

import static de.fesere.http.request.Path.path;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

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
    assertThat(router.controllerFor(path("/sample")), is(a(NotFoundController.class)));
  }

  @Test
  public void dynamicControllerFoundToHandleSubpath() {
    Controller handlePath = controller(s -> s.getFullpath().startsWith("/sample"));
    router.rootController(handlePath);
    assertThat(router.controllerFor(path("/sample/subpath")), is(handlePath));
    assertThat(router.controllerFor(path("/other")), is(a(NotFoundController.class)));
  }

  private Matcher<Controller> a(Class<? extends Controller> item) {
    return new BaseMatcher<Controller>() {
      public void describeTo(Description description) {
        description.appendText(" instnace of ").appendValue(item.getCanonicalName());
      }

      @Override
      public boolean matches(Object obj) {
        return obj.getClass().isAssignableFrom(item);
      }

      @Override
      public void describeMismatch(Object item, Description description) {
        description.appendText("instance of ").appendValue(item.getClass().getCanonicalName());
      }
    };
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