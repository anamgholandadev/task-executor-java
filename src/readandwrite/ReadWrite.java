package readandwrite;

import java.io.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWrite {
  private String path;
  private ReadWriteLock lockFile;

  public ReadWrite(String path, ReentrantReadWriteLock lockFile) {
    this.path = path;
    this.lockFile = lockFile;
  }

  public int read() throws IOException {
    lockFile.readLock().lock();
    try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
      String line = reader.readLine();
      if (line == null || line.isEmpty()) {
        System.out.println("Arquivo vazio ou não contém dados suficientes para leitura.");
        return -1;
      }
      return Integer.parseInt(line);
    } finally {
      lockFile.readLock().unlock();
    }
  }

  public void write(int value) throws IOException {
    lockFile.writeLock().lock();
    try (PrintWriter writer = new PrintWriter(new FileWriter(path, false))) {
      writer.println(value);
    } finally {
      lockFile.writeLock().unlock();
    }
  }

  public ReadWriteLock getLock() {
    return lockFile;
  }
}
