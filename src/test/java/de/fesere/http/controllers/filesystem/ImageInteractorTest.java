package de.fesere.http.controllers.filesystem;

import de.fesere.http.response.HttpResponse;
import de.fesere.http.vfs.VirtualFileSystem;
import org.junit.Test;

import static de.fesere.http.matchers.HttpResponseMatchers.hasHeader;
import static de.fesere.http.matchers.HttpResponseMatchers.hasStatusCode;
import static de.fesere.http.request.Path.path;
import static org.hamcrest.MatcherAssert.assertThat;

public class ImageInteractorTest {

  @Test
  public void canReadImageFromFilesystem() {
    VirtualFileSystem vfs = new VirtualFileSystem("src/test/resources/public");
    ImageInteractor interactor = new ImageInteractor(vfs);
    HttpResponse response = interactor.responseForImage(path("/server_sample_image.jpeg"));
    assertThat(response, hasStatusCode(200));
    assertThat(response, hasHeader("Content-Type", "image/jpeg"));
  }
}