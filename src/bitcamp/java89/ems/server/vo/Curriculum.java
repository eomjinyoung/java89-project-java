package bitcamp.java89.ems.server.vo;

public class Curriculum {
  //인스턴스 변수
  String curriculumName;
  String introduce;
  String benefit;
  String target;
  String document;
  boolean levelTest;
  int limit;
  int time;
  int term;

  public Curriculum() {}

  public Curriculum(String curriculumName, String benefit, String document, int limit, int time) {
    this.curriculumName = curriculumName;
    this.benefit = benefit;
    this.document = document;
    this.limit = limit;
    this.time = time;
  }
}
