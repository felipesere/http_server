package de.fesere.http.controllers;

import de.fesere.http.request.HttpRequest;
import de.fesere.http.response.HttpResponse;
import de.fesere.http.vfs.VirtualFileSystem;

import java.util.LinkedList;
import java.util.List;

import static de.fesere.http.response.StatusLine.OK;

public class FormController extends Controller {

  private final VirtualFileSystem fileSystem;

  public FormController(VirtualFileSystem fileSystem) {
    this.fileSystem = fileSystem;
  }

  @Override
  public HttpResponse doPost(HttpRequest request) {

    fileSystem.delete("/form");
    fileSystem.create("/form");
    fileSystem.writeTo("/form", request.getBody());
    return new HttpResponse(OK);
  }

  @Override
  public HttpResponse doGet(HttpRequest request) {
    List<String> read = new LinkedList<>();
    if (fileSystem.exists("/form")) {
      read.addAll(fileSystem.read("/form"));
    }
    return new HttpResponse(OK, flatten(read));
  }

  @Override
  public HttpResponse doPut(HttpRequest request) {
    fileSystem.writeTo("/form", request.getBody());
    return new HttpResponse(OK);
  }

  @Override
  public HttpResponse doDelete(HttpRequest request) {
    fileSystem.delete("/form");
    return new HttpResponse(OK);
  }

  private String flatten(List<String> read) {
    String result = "";
    for (String line : read) {
      result += line + "\n";
    }
    return result;
  }
}
