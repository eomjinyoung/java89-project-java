package bitcamp.java89.ems.server.vo;
public class Textbook {
  String title;
  String author;
  String press;
  int page;
  int price;
  String dayofissue;

  public Textbook() {}

  public Textbook(String title, String author, String press) {
    this.title = title;
    this.author = author;
    this.press = press;
  }
}
