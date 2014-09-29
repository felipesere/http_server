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

    Controller controller = new StaticResourcesController(vfs);
    assertThat(controller.doGet(request("/")), hasBody(allOf(containsString("/file1"), containsString("/file2"), containsString("/file3"))));
  }

  @Test
  public void testReadFileFromRootDirectory() {
    vfs.create("/file1");
    vfs.writeTo("/file1", asList("Foo", "Bar"));

    assertThat(controller.canHandle(path("/file1")), is(true));
    assertThat(controller.doGet(request("/file1")), hasBody(allOf(containsString("Foo"), containsString("Bar"))));
  }

  @Test
  public void testPatchVerifiesChecksum() {
    vfs.create("/patch-file");
    vfs.writeTo("/patch-file", asList("FooBar"));

    Map<String, String> header = new HashMap<>();
    header.put("If-Match","23f34863c9bdba4c7126e6872f16969496c798f6");
    HttpRequest request = new HttpRequest(new RequestLine(PATCH, "/patch-file",HTTP_11), header, asList("BarCamp"));

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
    HttpRequest request = new HttpRequest(new RequestLine(PATCH, "/patch-file",HTTP_11), header, asList("BarCamp"));

    HttpResponse response = controller.doPatch(request);
    assertThat(response, hasStatusCode(412));
    assertThat(vfs.read("/patch-file"), hasItem("FooBar"));
  }

  @Test
  public void understadsRangeHeader() {
    vfs.create("/partial-content.txt");
    vfs.writeTo("/partial-content.txt", asList("This is a file that contains text to read part of in order to fulfill a 206."));


    Map<String, String> header = new HashMap<>();
    header.put("Range","bytes=0-4");
    HttpRequest request = new HttpRequest(new RequestLine(GET, "/partial-content.txt",HTTP_11), header, asList(""));

    HttpResponse response = controller.doGet(request);
    assertThat(response, hasStatusCode(206));
    assertThat(response, hasBody(equalTo("This\n")));
  }

  private HttpRequest request(String path) {
    return new HttpRequest(new RequestLine(GET, path, HTTP_11), new HashMap<>(), new LinkedList<>());
  }
}