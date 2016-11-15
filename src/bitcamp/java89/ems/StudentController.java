/* 작업내용: 저장 기능 추가
- changed 변수 추가
- isChanged() 추가
- save() 추가 
*/
package bitcamp.java89.ems;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.DataInputStream;
import java.io.EOFException;

public class StudentController {
  private String filename = "student.data";
  private ArrayList<Student> list;
  private boolean changed;
  private Scanner keyScan;

  public StudentController(Scanner keyScan) {
    list = new ArrayList<Student>();
    this.keyScan = keyScan;

    this.load(); // 기존의 데이터 파일을 읽어서 ArrayList에 학생 정보를 로딩한다.
  }

  public boolean isChanged() {
    return changed;
  }

  private void load() {
    FileInputStream in0 = null;
    DataInputStream in = null;
    
    try {
      in0 = new FileInputStream(this.filename);
      in = new DataInputStream(in0);

      while (true) {
        Student student = new Student(); // 학생 데이터를 저장할 빈 객체 생성
        student.userId = in.readUTF(); // 학생 데이터 저장 
        student.password = in.readUTF();
        student.name = in.readUTF();
        student.email = in.readUTF();
        student.tel = in.readUTF();
        student.working = in.readBoolean();
        student.birthYear = in.readInt();
        student.school = in.readUTF();
        this.list.add(student); // 목록에 학생 객체 추가 
      }
    } catch (EOFException e) {
      // 파일을 모두 읽었다.
    } catch (Exception e) {
      System.out.println("학생 데이터 로딩 중 오류 발생!");
    } finally {
      try {
        in.close();
        in0.close();
      } catch (Exception e) {
        // close하다가 예외 발생하면 무시한다.
      }
    }
  }

  public void save() throws Exception {
    FileOutputStream out0 = new FileOutputStream(this.filename);
    DataOutputStream out = new DataOutputStream(out0);

    for (Student student : this.list) {
      out.writeUTF(student.userId);
      out.writeUTF(student.password);
      out.writeUTF(student.name);
      out.writeUTF(student.email);
      out.writeUTF(student.tel);
      out.writeBoolean(student.working);
      out.writeInt(student.birthYear);
      out.writeUTF(student.school);
    }
    changed = false;

    out.close();
    out0.close();
  }

  public void service() {
    loop:
    while (true) {
      System.out.print("학생관리> ");
      String command = keyScan.nextLine().toLowerCase();

      try {
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
      } catch (IndexOutOfBoundsException e) {
        System.out.println("인덱스가 유효하지 않습니다.");
      } catch (Exception e) {
        System.out.println("실행 중 오류가 발생했습니다.");
      } // try
    } // while
  }

  private void doList() {
    for (Student student : list) {
      System.out.printf("%s,%s,%s,%s,%s,%s,%d,%s\n",
        student.userId,
        student.password,
        student.name,
        student.tel,
        student.email,
        ((student.working)?"yes":"no"),
        student.birthYear,
        student.school);
    }
  }

  private void doUpdate() {
    System.out.print("변경할 학생의 인덱스? ");
    int index = Integer.parseInt(this.keyScan.nextLine());

    Student oldStudent = list.get(index);

    // 새 학생 정보를 입력 받는다.
    Student student = new Student();
    System.out.print("암호(예:1111)? ");
    student.password = this.keyScan.nextLine();

    System.out.printf("이름(%s)? ", oldStudent.name);
    student.name = this.keyScan.nextLine();

    System.out.printf("전화(%s)? ", oldStudent.tel);
    student.tel = this.keyScan.nextLine();

    System.out.printf("이메일(%s)? ", oldStudent.email);
    student.email = this.keyScan.nextLine();

    System.out.print("재직중(y/n)? ");
    student.working = (this.keyScan.nextLine().equals("y")) ? true : false;

    while (true) {
      try {
        System.out.printf("태어난해(%d)? ", oldStudent.birthYear);
        student.birthYear = Integer.parseInt(this.keyScan.nextLine());
        break;
      } catch (Exception e) {
        System.out.println("정수 값을 입력하세요.");
      }
    }

    System.out.printf("최종학교(%s)? ", oldStudent.school);
    student.school = this.keyScan.nextLine();

    System.out.print("저장하시겠습니까(y/n)? ");
    if (keyScan.nextLine().toLowerCase().equals("y")) {
      student.userId = oldStudent.userId;
      list.set(index, student);
      changed = true;
      System.out.println("저장하였습니다.");
    } else {
      System.out.println("변경을 취소하였습니다.");
    }
  }

  private void doAdd() {
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
      
      while (true) {
        try {      
          System.out.print("태어난해(예:1980)? ");
          student.birthYear = Integer.parseInt(this.keyScan.nextLine());
          break;
        } catch (Exception e) {
          System.out.println("정수 값을 입력하세요.");
        }
      }
      
      System.out.print("최종학교(예:비트고등학교)? ");
      student.school = this.keyScan.nextLine();

      list.add(student);
      changed = true;

      System.out.print("계속 입력하시겠습니까(y/n)? ");
      if (!this.keyScan.nextLine().equals("y"))
        break;
    } // while
  }

  private void doView() {
    System.out.print("학생의 인덱스? ");
    int index = Integer.parseInt(this.keyScan.nextLine());

    Student student = list.get(index);

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
    Student deletedStudent = list.remove(index);
    changed = true;
    System.out.printf("%s 학생 정보를 삭제하였습니다.\n", deletedStudent.userId);
  }
}
