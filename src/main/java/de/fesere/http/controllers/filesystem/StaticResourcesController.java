package de.fesere.http.controllers.filesystem;

import de.fesere.http.controllers.Controller;
import de.fesere.http.request.HttpRequest;
import de.fesere.http.request.Path;
import de.fesere.http.response.HttpResponse;
import de.fesere.http.vfs.VirtualFileSystem;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.List;

import static de.fesere.http.response.HttpResponse.response;
import static de.fesere.http.response.StatusLine.*;
import static de.fesere.http.utils.Utils.flatten;

public class StaticResourcesController extends Controller {

  private final VirtualFileSystem vfs;
  private final ImageInteractor imageInteractor;
  private final TextInteractor textInteractor;

  public StaticResourcesController(VirtualFileSystem vfs) {
    this.vfs = vfs;
    this.imageInteractor = new ImageInteractor(vfs);
    this.textInteractor = new TextInteractor(vfs);
  }

  @Override
  public boolean canHandle(Path path) {
    return path.isRoot() || fileExists(path);
  }

  private boolean fileExists(Path path) {
    return vfs.listFiles().contains(path.getRemainder());
  }

  @Override
  public HttpResponse doGet(HttpRequest request) {
    Path path = request.getPath();
    if (path.isRoot()) {
      return responseForAllLines();
    } else if (isImage(path)) {
      return imageInteractor.responseForImage(path);
    } else {
      return textInteractor.responseForText(request,path);
    }
  }

  private HttpResponse responseForAllLines() {
    List<String> body = vfs.listFiles();
    HtmlView view = new HtmlView();
    return response(OK).withBody(view.render(body)).build();
  }

  private boolean isImage(Path path) {
    String fullPath = path.getFullpath();
    return fullPath.endsWith(".jpeg") || fullPath.endsWith(".png") || fullPath.endsWith(".gif");
  }

  @Override
  public HttpResponse doPatch(HttpRequest request) {
    if (sha1matchesCurrentContent(request)) {
      vfs.writeTo(request.getPath().getFullpath(), request.getBody());
      return response(NOT_MODIFIED).build();
    } else {
      return response(PRECONDITION_FAILED).build();
    }
  }

  public boolean sha1matchesCurrentContent(HttpRequest request) {
    String inSha1 = request.getHeaders().get("If-Match");
    String filePath = request.getPath().getFullpath();
    String file = flatten(vfs.read(filePath), "\n");
    return calculateSHA1(file).equals(inSha1);
  }

  private String calculateSHA1(String input) {
    return DigestUtils.sha1Hex(input);
  }
}
