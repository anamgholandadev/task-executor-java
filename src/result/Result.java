package result;

public class Result {
  private int id;
  private int resultValue;
  private long time;

  public Result(int id, int resultValue, long time) {
    this.id = id;
    this.resultValue = resultValue;
    this.time = time;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getResultValue() {
    return resultValue;
  }

  public void setResultValue(int resultValue) {
    this.resultValue = resultValue;
  }

  public long getTime() {
    return time;
  }

  public void setTime(long time) {
    this.time = time;
  }
}
