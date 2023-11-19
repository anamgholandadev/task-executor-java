package task;

public class Task {
  private int id;
  private double cost;
  private TaskType type;
  private int value;

  public Task(int id, double cost, TaskType type, int value) {
    this.id = id;
    this.cost = cost;
    this.type = type;
    this.value = value;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public double getCost() {
    return cost;
  }

  public void setCost(double cost) {
    this.cost = cost;
  }

  public TaskType getType() {
    return type;
  }

  public void setType(TaskType type) {
    this.type = type;
  }

  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }
}
