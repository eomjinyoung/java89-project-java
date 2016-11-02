package bitcamp.java89.ems;

import java.util.Scanner;

public class StudentController {
  private Box head;
  private Box tail;
  private int length;
  private Scanner keyScan;

  public StudentController(Scanner keyScan) {
    head = new Box();
    tail = head;
    length = 0;
    this.keyScan = keyScan;
  }

  public void service() {
    loop:
    while (true) {
      System.out.print("학생관리> ");
      String command = keyScan.nextLine().toLowerCase();

      switch (command) {
      case "add": this.doAdd(); break;
      case "list": this.doList(); break;
      case "view": this.doView(); break;
      case "delete": this.doDelete(); break;
      //case "update": this.doUpdate(); break;
      case "main":
        break loop;
      default:
        System.out.println("지원하지 않는 명령어입니다.");
      }
    }
  }

  // 아래 doXXX() 메서드들은 오직 service()에서만 호출하기 때문에
  // private으로 접근을 제한한다.
  private void doList() {
    Box currentBox = head;
    while (currentBox != null && currentBox != tail) {
      Student student = (Student)currentBox.value;
      System.out.printf("%s,%s,%s,%s,%s,%s,%d,%s\n",
        student.userId,
        student.password,
        student.name,
        student.tel,
        student.email,
        ((student.working)?"yes":"no"),
        student.birthYear,
        student.school);
      currentBox = currentBox.next;
    }
  }

  /*
  private void doUpdate() {
    System.out.print("변경할 학생의 아이디는? ");
    String userId = this.keyScan.nextLine().toLowerCase();
    for (int i = 0; i < this.length; i++) {
      if (this.students[i].userId.toLowerCase().equals(userId)) {
        Student student = new Student();

        student.userId = this.students[i].userId;

        System.out.print("암호(예:1111)? ");
        student.password = this.keyScan.nextLine();

        System.out.print("이름(예:홍길동)? ");
        student.name = this.keyScan.nextLine();

        System.out.print("전화(예:010-1111-2222)? ");
        student.tel = this.keyScan.nextLine();

        System.out.print("이메일(예:hong@test.com)? ");
        student.email = this.keyScan.nextLine();

        System.out.print("재직중(y/n)? ");
        student.working = (this.keyScan.nextLine().equals("y")) ? true : false;

        System.out.print("태어난해(예:1980)? ");
        student.birthYear = Integer.parseInt(this.keyScan.nextLine());

        System.out.print("최종학교(예:비트고등학교)? ");
        student.school = this.keyScan.nextLine();

        System.out.print("저장하시겠습니까(y/n)? ");
        if (keyScan.nextLine().toLowerCase().equals("y")) {
          this.students[i] = student;
          System.out.println("저장하였습니다.");
        } else {
          System.out.println("변경을 취소하였습니다.");
        }
        return;
      }
    }
    System.out.printf("%s 이라는 학생이 없습니다.", userId);
  }
  */
  private void doAdd() {
    // 반복 해서 입력 받는다.
    while (true) {
      Student student = new Student();
      System.out.print("아이디(:hong)? ");
      student.userId = this.keyScan.nextLine();

      System.out.print("암호(예:1111)? ");
      student.password = this.keyScan.nextLine();

      System.out.print("이름(예:홍길동)? ");
      student.name = this.keyScan.nextLine();

      System.out.print("전화(예:010-1111-2222)? ");
      student.tel = this.keyScan.nextLine();

      System.out.print("이메일(예:hong@test.com)? ");
      student.email = this.keyScan.nextLine();

      System.out.print("재직중(y/n)? ");
      student.working = (this.keyScan.nextLine().equals("y")) ? true : false;

      System.out.print("태어난해(예:1980)? ");
      student.birthYear = Integer.parseInt(this.keyScan.nextLine());

      System.out.print("최종학교(예:비트고등학교)? ");
      student.school = this.keyScan.nextLine();

      // tail이 가리키는 빈 상자에 Student 인스턴스의 주소를 담는다.
      // 그리고 새 상자를 만든 다음 현재 상자에 연결한다.
      // tail은 다시 맨 마지막 빈 상자를 가리킨다.
      tail.value = student;
      tail.next = new Box();
      tail = tail.next;
      length++;

      System.out.print("계속 입력하시겠습니까(y/n)? ");
      if (!this.keyScan.nextLine().equals("y"))
        break;
    }
  }

  private void doView() {
    System.out.print("학생의 인덱스? ");
    int index = Integer.parseInt(this.keyScan.nextLine());

    if (index < 0 || index >= length) {
      System.out.println("인덱스가 유효하지 않습니다.");
      return;
    }

    Box currentBox = head;
    for (int i = 0; i < index; i++) {
      currentBox = currentBox.next;
    }

    Student student = (Student)currentBox.value;

    System.out.printf("아이디: %s\n", student.userId);
    System.out.printf("암호: (***)\n");
    System.out.printf("이름: %s\n", student.name);
    System.out.printf("전화: %s\n", student.tel);
    System.out.printf("이메일: %s\n", student.email);
    System.out.printf("재직중: %s\n", (student.working) ? "Yes" : "No");
    System.out.printf("태어난 해: %d\n", student.birthYear);
    System.out.printf("학교: %s\n", student.school);
  }

  private void doDelete() {
    System.out.print("삭제할 학생의 인덱스? ");
    int index = Integer.parseInt(keyScan.nextLine());

    if (index < 0 || index >= length) {
      System.out.println("인덱스가 유효하지 않습니다.");
      return;
    }

    Student deletedStudent = null;
    if (index == 0) {
      deletedStudent = (Student)head.value;
      head = head.next;
    } else {
      Box currentBox = head;
      for (int i = 0; i < (index - 1); i++) {
        currentBox = currentBox.next;
      }
      deletedStudent = (Student)currentBox.next.value;
      currentBox.next = currentBox.next.next;
    }

    length--;
    System.out.printf("%s 학생 정보를 삭제하였습니다.\n", deletedStudent.userId);
  }
}
