package de.fesere.http.vfs;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class VirtualFileSystem {
  private final Map<String, List<String>> files = Collections.synchronizedMap(new HashMap<>());
  private final String folderPath;

  public VirtualFileSystem(String root) {
    this.folderPath = root;
  }
  public boolean exists(String path) {
    return files.containsKey(path);
  }

  public void create(String path) {
    files.put(path, new LinkedList<>());
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
    if (!files.containsKey(path)) {
      throw new FileDoesNotExistException(path);
    }
  }

  public void clear() {
    files.clear();
  }

  public List<String> listFiles() {
    return new LinkedList<>(files.keySet());
  }

  public void preload() {
    File directory = resolveFile(folderPath);
    for (File file : directory.listFiles()) {
      tryAddFile(file);
    }
  }

  private void tryAddFile(File file) {
    try {
      List<String> lines = read(file);
      files.put("/" + file.getName(), lines);
    } catch (IOException e) {
      throw new RuntimeException("Could not add file: " + file.getName(), e);
    }
  }

  private File resolveFile(String folderPath) {
    if (!folderPath.startsWith("/")) {
      Path workingDirectory = getWorkingDirectory();
      return workingDirectory.resolve(folderPath).toFile();
    } else {
      return Paths.get(folderPath).toFile();
    }
  }

  private Path getWorkingDirectory() {
    return Paths.get("").toAbsolutePath();
  }

  private Path getRootFolder() {
    return getWorkingDirectory().resolve(folderPath);
  }

  private List<String> read(File file) throws IOException {
    return IOUtils.readLines(new FileInputStream(file));
  }

  public byte[] getRawBytes(String path) {
    Path root = getRootFolder();
    path = makeRelative(path);
    try {
      Path targetPath = root.resolve(path);
      return Files.readAllBytes(targetPath);
    } catch (IOException e) {
     throw new RuntimeException(e);
    }
  }

  private String makeRelative(String path) {
    return path.replaceFirst("/","");
  }
}
