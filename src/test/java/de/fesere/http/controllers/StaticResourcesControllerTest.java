package de.fesere.http.controllers;

import de.fesere.http.request.HttpRequest;
import de.fesere.http.response.HttpResponse;
import de.fesere.http.vfs.VirtualFileSystem;
import org.junit.Before;
import org.junit.Test;

import static de.fesere.http.Method.GET;
import static de.fesere.http.Method.PATCH;
import static de.fesere.http.matchers.HttpResponseMatchers.hasBody;
import static de.fesere.http.matchers.HttpResponseMatchers.hasStatusCode;
import static de.fesere.http.request.HttpRequest.*;
import static de.fesere.http.request.Path.path;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class StaticResourcesControllerTest {

  private final VirtualFileSystem vfs = new VirtualFileSystem();
  private final Controller controller = new StaticResourcesController(vfs);

  @Before
  public void setup() {
    vfs.clear();
  }

  @Test
  public void testReadDirectoryListings() {
    vfs.create("/file1");
    vfs.create("/file2");
    vfs.create("/file3");

    HttpRequest httpRequest = request(GET, "/").build();
    assertThat(controller.doGet(httpRequest), hasBody(allOf(containsString("/file1"), containsString("/file2"), containsString("/file3"))));
  }

  @Test
  public void testReadFileFromRootDirectory() {
    vfs.create("/file1");
    vfs.writeTo("/file1", asList("Foo", "Bar"));

    HttpRequest httpRequest = request(GET, "/file1").build();

    assertThat(controller.canHandle(path("/file1")), is(true));
    assertThat(controller.doGet(httpRequest), hasBody(allOf(containsString("Foo"), containsString("Bar"))));
  }

  @Test
  public void testPatchVerifiesChecksum() {
    vfs.create("/patch-file");
    vfs.writeTo("/patch-file", asList("FooBar"));

    HttpRequest httpRequest = request(PATCH, "/patch-file").addHeader("If-Match", "23f34863c9bdba4c7126e6872f16969496c798f6").withBody("BarCamp").build();

    HttpResponse response = controller.doPatch(httpRequest);
    assertThat(response, hasStatusCode(204));
    assertThat(vfs.read("/patch-file"), hasItem("BarCamp"));
  }

  @Test
  public void returnsPreconditionFailedIfChecksumIsWrong() {
    vfs.create("/patch-file");
    vfs.writeTo("/patch-file", asList("FooBar"));

    HttpRequest httpRequest = request(PATCH, "/patch-file").addHeader("If-Match", "92874bckdgf3y9e23ydbcsdkj").withBody("BarCamp").build();

    HttpResponse response = controller.doPatch(httpRequest);
    assertThat(response, hasStatusCode(412));
    assertThat(vfs.read("/patch-file"), hasItem("FooBar"));
  }

  @Test
  public void understadsRangeHeader() {
    vfs.create("/partial-content.txt");
    vfs.writeTo("/partial-content.txt", asList("This is a file that contains text to read part of in order to fulfill a 206."));

    HttpRequest httpRequest = request(GET, "/partial-content.txt").addHeader("Range", "bytes=0-4").build();

    HttpResponse response = controller.doGet(httpRequest);
    assertThat(response, hasStatusCode(206));
    assertThat(response, hasBody(equalTo("This")));
  }
}