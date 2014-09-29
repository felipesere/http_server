package de.fesere.http.controllers;

import de.fesere.http.Range;
import de.fesere.http.request.HttpRequest;
import de.fesere.http.request.Path;
import de.fesere.http.response.HttpResponse;
import de.fesere.http.vfs.VirtualFileSystem;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.List;

import static de.fesere.http.response.HttpResponse.response;
import static de.fesere.http.response.StatusLine.*;

public class StaticResourcesController extends Controller {

  private final VirtualFileSystem vfs;

  public StaticResourcesController(VirtualFileSystem vfs) {
    this.vfs = vfs;
  }

  @Override
  public boolean canHandle(Path path) {
    return path.isRoot() || fileExists(path);
  }

  private boolean fileExists(Path path) {
    return vfs.listFiles().contains(path.remainder());
  }

  @Override
  public HttpResponse doGet(HttpRequest request) {
    Path path = request.getPath();
    List<String> lines;
    if (path.isRoot()) {
      lines = vfs.listFiles();
    } else {
      lines = vfs.read(path.remainder());
      Range range = new Range();
      if (range.hasRangeHeader(request)) {
        return range.handleRangeRequest(request.getHeaders().get("Range"), lines);
      }
    }
    return response(OK).withBody(lines).build();
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
    String file = flatten(vfs.read(filePath));
    return calculateSHA1(file).equals(inSha1);
  }

  private String calculateSHA1(String input) {
    return DigestUtils.sha1Hex(input);
  }

  private String flatten(List<String> read) {
    String result = "";
    for (String line : read) {
      result += line + "\n";
    }
    return result;
  }
}
