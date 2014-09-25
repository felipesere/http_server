package de.fesere.http.vfs;

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
}
