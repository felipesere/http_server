package de.fesere.http.controllers;

import de.fesere.http.request.HttpRequest;
import de.fesere.http.request.RequestLine;
import de.fesere.http.response.HttpResponse;
import de.fesere.http.vfs.VirtualFileSystem;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static de.fesere.http.HttpVersion.HTTP_11;
import static de.fesere.http.Method.GET;
import static de.fesere.http.Method.PATCH;
import static de.fesere.http.matchers.HttpResponseMatchers.hasBody;
import static de.fesere.http.matchers.HttpResponseMatchers.hasStatusCode;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class StaticResourcesControllerTest {

  private final VirtualFileSystem vfs = new VirtualFileSystem();

  @Before
  public void setup() {
    vfs.clear();
  }
  @Test
  public void testReadDirectoryListings() {
    VirtualFileSystem vfs = new VirtualFileSystem();
    vfs.create("/file1");
    vfs.create("/file2");
    vfs.create("/file3");

    Controller controller = new StaticResourcesController(vfs);
    assertThat(controller.doGet(request("/")), hasBody(containsString("/file1"), containsString("/file2"), containsString("/file3")));
  }


  @Test
  public void testReadFileFromRootDirectory() {
    VirtualFileSystem vfs = new VirtualFileSystem();
    vfs.create("/file1");
    vfs.writeTo("/file1", asList("Foo", "Bar"));

    Controller controller = new StaticResourcesController(vfs);
    assertThat(controller.canHandle("/file1"), is(true));
    assertThat(controller.doGet(request("/file1")), hasBody(containsString("Foo"), containsString("Bar")));
  }

  @Test
  public void testPatchVerifiesChecksum() {
    vfs.create("/patch-file");
    vfs.writeTo("/patch-file", asList("FooBar"));

    Map<String, String> header = new HashMap<>();
    header.put("If-Match","23f34863c9bdba4c7126e6872f16969496c798f6");
    HttpRequest request = new HttpRequest(new RequestLine(PATCH, "/patch-file",HTTP_11),header, asList("BarCamp"));

    Controller controller = new StaticResourcesController(vfs);
    HttpResponse response = controller.doPatch(request);
    assertThat(response, hasStatusCode(204));
    assertThat(vfs.read("/patch-file"), hasItem("BarCamp"));
  }

  @Test
  public void returnsPreconditionFailedIfChecksumIsWrong() {
    vfs.create("/patch-file");
    vfs.writeTo("/patch-file", asList("FooBar"));

    Map<String, String> header = new HashMap<>();
    header.put("If-Match","23f34863c9bdba4c712");
    HttpRequest request = new HttpRequest(new RequestLine(PATCH, "/patch-file",HTTP_11),header, asList("BarCamp"));

    Controller controller = new StaticResourcesController(vfs);
    HttpResponse response = controller.doPatch(request);
    assertThat(response, hasStatusCode(412));
    assertThat(vfs.read("/patch-file"), hasItem("FooBar"));
  }

  private HttpRequest request(String path) {
    return new HttpRequest(new RequestLine(GET, path, HTTP_11), new HashMap<>(), new LinkedList<>());
  }
}