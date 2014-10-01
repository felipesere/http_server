package de.fesere.http.controllers.filesystem;

import de.fesere.http.request.Path;
import de.fesere.http.response.HttpResponse;
import de.fesere.http.vfs.VirtualFileSystem;

import static de.fesere.http.response.HttpResponse.response;
import static de.fesere.http.response.StatusLine.OK;

public class ImageInteractor {

  private VirtualFileSystem vfs;

  public ImageInteractor(VirtualFileSystem vfs) {
    this.vfs = vfs;
  }

  public HttpResponse responseForImage(Path path) {
    byte[] fileContent = vfs.getRawBytes(path.getFullpath());
    return response(OK).withBody(fileContent).addHeader("Content-Type", getContentTypeForImage(path)).build();
  }
  private String getContentTypeForImage(Path path) {
    String fullPath = path.getFullpath();
    String ending = fullPath.substring(fullPath.lastIndexOf(".")+1, fullPath.length());

    return "image/"+ending;
  }
}
