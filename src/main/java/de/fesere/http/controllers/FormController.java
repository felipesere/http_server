package de.fesere.http.controllers;

import de.fesere.http.request.HttpRequest;
import de.fesere.http.response.HttpResponse;
import de.fesere.http.vfs.VirtualFileSystem;

import static de.fesere.http.response.HttpResponse.response;
import static de.fesere.http.response.StatusLine.OK;

public class FormController extends Controller {

  private final VirtualFileSystem fileSystem;

  public FormController(VirtualFileSystem fileSystem) {
    this.fileSystem = fileSystem;
  }

  @Override
  public HttpResponse doPost(HttpRequest request) {
    fileSystem.create("/form");
    fileSystem.writeTo("/form", request.getBody());
    return response(OK).build();
  }

  @Override
  public HttpResponse doGet(HttpRequest request) {
    if (fileSystem.exists("/form")) {
      return response(OK).withBody(fileSystem.read("/form")).build();
    }
    return response(OK).build();
  }

  @Override
  public HttpResponse doPut(HttpRequest request) {
    fileSystem.writeTo("/form", request.getBody());
    return response(OK).build();
  }

  @Override
  public HttpResponse doDelete(HttpRequest request) {
    fileSystem.delete("/form");
    return response(OK).build();
  }
}
