package de.fesere.http;

import de.fesere.http.parsers.StreamingParser;
import de.fesere.http.request.HttpRequest;
import de.fesere.http.response.HttpResponse;
import de.fesere.http.response.StatusLine;
import de.fesere.http.vfs.VirtualFileSystem;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static de.fesere.http.Method.*;

public class Worker implements Runnable {
  private final Socket clientSocket;
  private VirtualFileSystem fileSystem;

  public Worker(Socket clientSocket, VirtualFileSystem fileSystem) {
    this.clientSocket = clientSocket;
    this.fileSystem = fileSystem;
  }

  @Override
  public void run() {
    try {
      PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
      StreamingParser parser = new StreamingParser(clientSocket.getInputStream());
      HttpRequest httpRequest = parser.read();


      HttpResponse response = null;
      if (httpRequest.getRequestLine().getMethod() == OPTIONS) {
        Map<String, String> header = new HashMap<>();
        header.put("Allow", "GET,HEAD,POST,PUT,OPTIONS");

        response = new HttpResponse(new StatusLine(200, "OK"),header, "" );
      } else if (httpRequest.getRequestLine().getPath().equals("/form")) {
        response = handleFormPath(httpRequest);
      }

      if(response == null) {
        response = new HttpResponse(new StatusLine(200, "OK"));
      }


      out.println(response.printable());
      clientSocket.close();
    } catch (IOException e) {
    }
  }

  private HttpResponse handleFormPath(HttpRequest httpRequest) {
    HttpResponse response = null;
    if (httpRequest.getRequestLine().getMethod() == POST) {
      fileSystem.delete("/form");
      fileSystem.create("/form");
      fileSystem.writeTo("/form", httpRequest.getBody());
      response = new HttpResponse(new StatusLine(200, "OK"));
    } else if (httpRequest.getRequestLine().getMethod() == GET) {
      List<String> read = new LinkedList<>();
      if(fileSystem.exists("/form")){
        read.addAll(fileSystem.read("/form"));
      }
      response = new HttpResponse(new StatusLine(200, "OK"), new HashMap<String, String>(), flatten(read));
    }
    else if (httpRequest.getRequestLine().getMethod() == PUT) {
      fileSystem.writeTo("/form", httpRequest.getBody());
      response = new HttpResponse(new StatusLine(200, "OK"));
    }
    else if(httpRequest.getRequestLine().getMethod() == DELETE) {
      fileSystem.delete("/form");
      response = new HttpResponse(new StatusLine(200, "OK"));
    }
    return response;
  }

  private String flatten(List<String> read) {
    String result = "";
    for (String line : read) {
      result += line + "\n";
    }
    return result;
  }
}
