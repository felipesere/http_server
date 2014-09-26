package de.fesere.http.vfs;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class VirtualFileSystem {
  private Map<String, List<String>> files = Collections.synchronizedMap(new HashMap<String, List<String>>());
  public boolean exists(String path) {
    return files.containsKey(path);
  }

  public void create(String path) {
    files.put(path, new LinkedList<String>());
  }

  public void delete(String path) {
    files.remove(path);
  }

  public void writeTo(String path, List<String> content) {
    failWhenFileMissing(path);
    files.put(path, new ArrayList<>(content));
  }

  public List<String> read(String path) {
    failWhenFileMissing(path);
    return files.get(path);
  }

  private void failWhenFileMissing(String path) {
    if(!files.containsKey(path)) {
      throw new FileDoesNotExistException(path);
    }
  }

  public void clear() {
    files.clear();
  }

  public List<String> listFiles() {
    return new LinkedList<>(files.keySet());
  }

  public void preload(String folderPath) {
    File directory = resolveFile(folderPath);
    for(File file : directory.listFiles()) {
      tryAddFile(file);
    }
  }

  private void tryAddFile(File file) {
    try {
      List<String> lines = read(file);
      files.put("/" + file.getName(), lines);
    } catch (IOException e) {
    }
  }

  private File resolveFile(String folderPath) {
    if(!folderPath.startsWith("/")) {
      Path workingDirectory = Paths.get("").toAbsolutePath();
      return workingDirectory.resolve(folderPath).toFile();
    } else {
      return Paths.get(folderPath).toFile();
    }
  }

  private List<String> read(File file) throws IOException {
    return IOUtils.readLines(new FileInputStream(file));
  }
}
