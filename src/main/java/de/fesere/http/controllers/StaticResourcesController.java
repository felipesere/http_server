package de.fesere.http.controllers;

import de.fesere.http.request.HttpRequest;
import de.fesere.http.response.HttpResponse;
import de.fesere.http.response.StatusLine;
import de.fesere.http.vfs.VirtualFileSystem;

import java.util.HashMap;
import java.util.List;

import static de.fesere.http.response.StatusLine.NOT_FOUND;

public class StaticResourcesController extends Controller {

  private final VirtualFileSystem vfs;
  private final HttpResponse notFound = new HttpResponse(NOT_FOUND);

  public StaticResourcesController(VirtualFileSystem vfs) {
    this.vfs = vfs;
  }

  @Override
  public boolean canHandle(String path) {
    if(path.equals("/")) {
      return true;
    }
    String file = remainder(path);
    return vfs.listFiles().contains(file);
  }

  @Override
  public HttpResponse doPut(HttpRequest request) {
    return notFound;
  }

  @Override
  public HttpResponse doGet(HttpRequest request) {
    String path = request.getRequestLine().getPath();
    List<String> lines;
    if(remainder(path).equals("/")) {
      lines = vfs.listFiles();
    }
    else {
       lines = vfs.read(remainder(path));
    }
    return new HttpResponse(StatusLine.OK, new HashMap<>(), flatten(lines));
  }

  private String remainder(String path) {
    if(path.equals("/")) {
      return path;
    } else {
      int secondSlash = path.indexOf("/", 1);
      if(secondSlash > 0) {
        return path.substring(secondSlash,path.length());
      } else {
        return path;
      }
    }
  }

  private String flatten(List<String> read) {
    String result = "";
    for (String line : read) {
      result += line + "\n";
    }
    return result;
  }
}
