package executor;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import readandwrite.ReadWrite;
import result.Result;
import task.Task;
import worker.Worker;

public class Executor {
  private Queue<Task> taskQueue;
  private Queue<Result> resultQueue;
  private Worker[] workers;
  private File file;

  public Executor(int T, Queue<Task> taskQueue, File file) {
    this.taskQueue = new ConcurrentLinkedQueue<>(taskQueue);
    this.resultQueue = new ConcurrentLinkedQueue<>();
    this.workers = new Worker[T];
    this.file = file;
    ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    ReadWrite readWrite = new ReadWrite("resultados/shared/" + file.getName(), lock);

    try {
      readWrite.write(0);
    } catch (IOException e) {
      System.out.println("Nâo foi possível escrever no arquivo compartilhado");
      e.printStackTrace();
    }

    for (int i = 0; i < T; i++) {
      this.workers[i] = new Worker(this.taskQueue, this.resultQueue, readWrite);
    }

  }

  public void executeTasks() {
    long startTime = System.currentTimeMillis();

    for (Worker worker : this.workers) {
      worker.start();
    }

    for (Worker worker : this.workers) {
      try {
        worker.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    long endTime = System.currentTimeMillis();

    long executionTime = endTime - startTime;

    File outputFile = new File("resultados/total/" + file.getName());

    String text = "Tempo gasto na etapa de Processamento: " + executionTime + "ms";
    System.out.println(text);

    try (PrintStream output = new PrintStream(outputFile)) {
      output.println(text);
    } catch (FileNotFoundException e) {
      System.out.println("Nâo foi possível escrever no arquivo de tempo total");
      e.printStackTrace();
    }

  }

}