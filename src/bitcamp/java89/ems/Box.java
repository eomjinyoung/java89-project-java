package bitcamp.java89.ems;

public class Box  {
  Object value;
  Box next;

  public Box() {}

  public Box(Object value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return "Box(" + this.value.toString() + ")";
  }
}
