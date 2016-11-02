package bitcamp.java89.ems;
public class Classroom {
  // 인스턴스 변수
  int roomNo;
  int capacity;
  String className;
  int startTime;
  int endTime;
  String classTime;
  boolean projector;
  boolean locker;
  // 생성자
  public Classroom() {}

  public Classroom(int roomNo, String className, String classTime) {
    this.roomNo = roomNo;
    this.className = className;
    this.classTime = classTime;
  }
}
