package bitcamp.java89.ems;
import java.util.Scanner;
public class TextbookController {
  Scanner scan;
  Textbook[] textbooks = new Textbook[100];
  int length = 0;

  public TextbookController(Scanner scan) {
    this.scan = scan;
  }
  public void service() {
    loop:
    while (true) {
      System.out.print("교재관리> ");
      String command = scan.nextLine().toLowerCase();

      switch (command) {
      case "add": this.doAdd(); break;
      case "list": this.doList(); break;
      case "view": this.doView(); break;
      case "delete": this.doDelete(); break;
      case "update": this.doUpdate(); break;
      case "main":
        break loop;
      default:
        System.out.println("지원하지 않는 명령어입니다.");
      }
    }
  }

  public void doAdd() {
    for (int i = 0; i < textbooks.length; i++) {
      Textbook textbook = new Textbook();
      System.out.print("교재명 : ");
      textbook.title = scan.nextLine();
      System.out.print("저자 : ");
      textbook.author = scan.nextLine();
      System.out.print("출판사 : ");
      textbook.press = scan.nextLine();
      System.out.print("페이지수 : ");
      textbook.page = Integer.parseInt(scan.nextLine());
      System.out.print("가격 : ");
      textbook.price = Integer.parseInt(scan.nextLine());
      System.out.print("발행일 : ");
      textbook.dayofissue = scan.nextLine();
      textbooks[length++] = textbook;

      System.out.print("계속 입력하시겠습니까 (y/n) ? ");
      if (!scan.nextLine().equals("y"))
        break;
    }
  }

  public void doList() {
    Textbook textbook = new Textbook();
    for (int i = 0; i < length; i++) {
      textbook = textbooks[i];
      System.out.printf("%s %s %s %d %d %s\n",
      textbook.title, textbook.author, textbook.press,
      textbook.page, textbook.price, textbook.dayofissue);
    }
  }

  public void doView() {
    System.out.print("조회할 교재명을 입력하세요 : ");
    String inputtitle = scan.nextLine().toLowerCase();
    for (int i =0; i < length; i++) {
      if (inputtitle.equals(textbooks[i].title)) {
        System.out.printf("교재명 : %s\n", textbooks[i].title);
        System.out.printf("저자 : %s\n", textbooks[i].author);
        System.out.printf("출판사 : %s\n", textbooks[i].press);
        System.out.printf("페이지수 : %d\n", textbooks[i].page);
        System.out.printf("가격 : %d\n", textbooks[i].price);
        System.out.printf("발행일 : %s\n", textbooks[i].dayofissue);
        return;
      }
    }
    System.out.printf("%s 교재는 없습니다.\n", inputtitle);
  }

  public void doDelete() {
    System.out.print("삭제할 교재명을 입력하세요 :");
    String inputtitle = scan.nextLine().toLowerCase();
    for (int i = 0; i < length; i++) {
      if (textbooks[i].title.equals(inputtitle)) {
        for (int x = i + 1; x < length; x++, i++) {
          textbooks[i] = textbooks[x];
        }
        textbooks[--length] = null;
        System.out.printf("%s 교재 정보를 삭제하였습니다.\n", inputtitle);
        return;
      }
    }
    System.out.printf("%s 교재는 없습니다.\n", inputtitle);
  }

  public void doUpdate() {
    System.out.print("변경할 교재명을 입력하세요 : ");
    String inputtitle = scan.nextLine().toLowerCase();
    for (int i = 0; i < length; i++) {
      if (textbooks[i].title.equals(inputtitle)) {
        Textbook textbook = new Textbook();
        System.out.print("교재명 : ");
        textbook.title = scan.nextLine();
        System.out.print("저자 : ");
        textbook.author = scan.nextLine();
        System.out.print("출판사 : ");
        textbook.press = scan.nextLine();
        System.out.print("페이지수 : ");
        textbook.page = Integer.parseInt(scan.nextLine());
        System.out.print("가격 : ");
        textbook.price = Integer.parseInt(scan.nextLine());
        System.out.print("발행일 : ");
        textbook.dayofissue = scan.nextLine();
        System.out.print("변경 사항을 저장하시겠습니까 (y/n)?");
        if (scan.nextLine().equals("y")) {
          textbooks[i] = textbook;
          System.out.printf("%s 교재 정보를 변경하였습니다.\n", inputtitle);
          return;
        } else {
          System.out.println("변경을 취소하였습니다.");
          return;
        }
      }
    }
    System.out.printf("%s 교재는 없습니다.\n", inputtitle);
  }
}
