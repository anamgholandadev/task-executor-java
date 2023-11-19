package worker;

import java.io.IOException;
import java.util.Queue;
import readandwrite.ReadWrite;
import result.Result;
import task.Task;

public class Worker extends Thread {
  private Queue<Task> tasks;
  private Queue<Result> results;
  private ReadWrite readWrite;
  private boolean busy;

  public Worker(Queue<Task> tasks, Queue<Result> results, ReadWrite readWrite) {

    this.results = results;
    this.tasks = tasks;
    this.readWrite = readWrite;
    this.busy = false;
  }

  @Override
  public void run() {
    while (true) {
      try {
        Task task = tasks.poll();
        if (task == null || task.getId() == -1) {
          break;
        }

        Result result = executeTask(task);
        results.add(result);
      } catch (RuntimeException e) {
        e.printStackTrace();
      }
    }
  }

  public boolean isBusy() {
    return busy;
  }

  public void addTask(Task newTask) {
    tasks.add(newTask);
  }

  public void setBusy(boolean busy) {
    this.busy = busy;
  }

  private Result executeTask(Task task) {
    try {
      if (task.getId() == -1) {
        return new Result(-1, 0, 0);
      }

      setBusy(true);

      switch (task.getType()) {
        case READ: {
          long startTime = System.currentTimeMillis();
          sleep((long) task.getCost());

          int currentValue = readWrite.read();

          long endTime = System.currentTimeMillis();
          long executionTime = endTime - startTime;

          return new Result(task.getId(), currentValue, executionTime);
        }
        case WRITE: {
          long startTimeWrite = System.currentTimeMillis();
          sleep((long) task.getCost());

          readWrite.getLock().writeLock().lock();

          int currentValue = readWrite.read();
          int newValue = currentValue + task.getValue();
          readWrite.write((int) newValue);

          readWrite.getLock().writeLock().unlock();

          long endTime = System.currentTimeMillis();
          long executionTime = endTime - startTimeWrite;
          return new Result(task.getId(), newValue, executionTime);
        }

        default:
          throw new IllegalArgumentException("Tipo de tarefa inválido: " + task.getType());
      }
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException("Erro ao executar a tarefa", e);
    } finally {
      setBusy(false);
    }
  }

}
