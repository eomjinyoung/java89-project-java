package bitcamp.java89.ems;

public class LinkedList<T> {
  Box<T> head;
  Box<T> tail;
  int length;

  public LinkedList() {
    head = new Box<T>();
    tail = head;
  }

  public void add(T value) {
    tail.value = value;
    tail.next = new Box<T>();
    tail = tail.next;
    length++;
  }

  public T get(int index) {
    if (index < 0 || index >= length) {
      System.out.println("인덱스가 유효하지 않습니다.");
      return null;
    }

    Box<T> cursor = head;
    for (int i = 0; i < index; i++) {
      cursor = cursor.next;
    }
    return cursor.value;
  }

  public T set(int index, T newValue) {
    if (index < 0 || index >= length) {
      System.out.println("인덱스가 유효하지 않습니다.");
      return null;
    }

    Box<T> cursor = head;
    for (int i = 0; i < index; i++) {
      cursor = cursor.next;
    }

    T oldValue = cursor.value;
    cursor.value = newValue;
    return oldValue;
  }

  public T remove(int index) {
    if (index < 0 || index >= length) {
      System.out.println("인덱스가 유효하지 않습니다.");
      return null;
    }

    T oldValue = null;
    if (index == 0) {
      oldValue = head.value;
      head = head.next;
    } else {
      Box<T> cursor = head;
      for (int i = 0; i < (index - 1); i++) {
        cursor = cursor.next;
      }
      oldValue = cursor.next.value;
      cursor.next = cursor.next.next;
    }
    length--;
    return oldValue;
  }

  public int size() {
    return length;
  }

  private class Box<T>  {
    T value;
    Box<T> next;

    public Box() {}

    public Box(T value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return "Box<T>(" + this.value + ")";
    }
  }
}
