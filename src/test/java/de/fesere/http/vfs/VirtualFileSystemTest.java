package de.fesere.http.vfs;

import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

public class VirtualFileSystemTest {

  private final VirtualFileSystem vfs = new VirtualFileSystem();

  @Before
  public void setup() {
    vfs.clear();
  }

  @Test
  public void fileDoesNotExistBeforeCreating() {
    assertThat(vfs.exists("/sampleFile"), is(false));
  }

  @Test
  public void fileExistsAfterCreating() {
    vfs.create("/sampleFile");
    assertThat(vfs.exists("/sampleFile"), is(true));
  }

  @Test
  public void fileNoLongerExistsAfterDeleting() {
    vfs.create("/sampleFile");
    vfs.delete("/sampleFile");
    assertThat(vfs.exists("/sampleFile"), is(false));
  }

  @Test
  public void canReadLinesWrittenToFile() {
    vfs.create("/sampleFile");
    vfs.writeTo("/sampleFile", asList("First line", "Second line"));
    assertThat(vfs.read("/sampleFile"), hasSize(2));
  }

  @Test(expected = FileDoesNotExistException.class)
  public void canNotReadFromNonExistantFile() {
    vfs.read("/samplePath");
  }

  @Test(expected = FileDoesNotExistException.class)
  public void canNotWriteToNonExistingFile() {
    vfs.writeTo("/samplePath", new LinkedList<String>());
  }

  @Test
  public void canListExistingFiles() {
    vfs.create("/sample1");
    vfs.create("/sample2");
    vfs.create("/sample3");
    assertThat(vfs.listFiles(), hasItems("/sample1", "/sample2", "/sample3"));
  }


  @Test
  public void canBePreloadedFromFileSystem() {
    vfs.preload("src/test/resources/public");
    assertThat(vfs.listFiles(), hasItems("/file1.txt", "/file2.txt"));
  }
}