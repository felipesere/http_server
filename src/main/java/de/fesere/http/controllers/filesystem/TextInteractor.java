package de.fesere.http.controllers.filesystem;

import de.fesere.http.request.HttpRequest;
import de.fesere.http.request.Path;
import de.fesere.http.response.HttpResponse;
import de.fesere.http.response.Range;
import de.fesere.http.vfs.VirtualFileSystem;

import java.util.List;

import static de.fesere.http.response.HttpResponse.response;
import static de.fesere.http.response.StatusLine.OK;
import static de.fesere.http.utils.Utils.flatten;

public class TextInteractor {

  private final VirtualFileSystem vfs;
  private final Range range = new Range();
  public TextInteractor(VirtualFileSystem vfs) {
    this.vfs = vfs;
  }

  public HttpResponse responseForText(HttpRequest request, Path path) {
    List<String> lines;
    lines = vfs.read(path.getRemainder());
    if (range.hasRangeHeader(request)) {
      return range.handleRangeRequest(request, flatten(lines, "\n"));
    }
    return response(OK).withBody(lines).build();
  }
}
