import java.io.*;
import java.util.*;
import java.util.function.Function;

import executor.Executor;
import task.Task;
import task.TaskType;

public class TaskExecutor {

  public static void main(String[] args) {
    if (args.length < 1) {
      System.err.println("Informe um arquivo de entrada como argumento");
      System.err.println("Ex.: java -cp bin TaskExecutor dados/teste1.txt");
    }

    File argsFile = new File(args[0]);

    if (!argsFile.exists()) {
      System.err.println("Arquivo não existe!");
    }

    int N, T, E;

    try (Scanner argScanner = new Scanner(argsFile)) {
      argScanner.useDelimiter(",");
      N = argScanner.nextInt();
      T = argScanner.nextInt();
      E = argScanner.nextInt();
    } catch (FileNotFoundException e) {
      System.err.println("Arquivo com formato inválido!");
      return;
    }

    Queue<Task> taskQueue = loadTasks(N, E);

    Executor taskExecutor = new Executor(T, taskQueue, argsFile);

    taskExecutor.executeTasks();
  }

  private static Queue<Task> loadTasks(int N, int E) {
    Queue<Task> taskQueue = new LinkedList<>();

    Function<Double, TaskType> obterTaskType = (aleatorio) -> {
      if (aleatorio < E) {
        return TaskType.WRITE;
      }
      return TaskType.READ;
    };

    int totalTasks = Double.valueOf(Math.pow(10, N)).intValue();

    for (int i = 0; i < totalTasks; i++) {
      double cost = Math.random() * 0.01;
      int value = Double.valueOf(Math.random() * 10).intValue();

      TaskType type = obterTaskType.apply(Math.random() * 100);

      taskQueue.add(new Task(i + 1, cost, type, type == TaskType.WRITE ? value : 0));
    }

    return taskQueue;
  }
}
