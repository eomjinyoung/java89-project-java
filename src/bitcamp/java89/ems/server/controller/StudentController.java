package bitcamp.java89.ems.server.controller;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

import bitcamp.java89.ems.server.vo.Student;

public class StudentController {
  private Scanner in;
  private PrintStream out;
  
  private String filename = "student-v1.6.data";
  private ArrayList<Student> list;
  private boolean changed;

  public StudentController(Scanner in, PrintStream out) {
    list = new ArrayList<Student>();
    this.in = in;
    this.out = out;

    this.load(); // 기존의 데이터 파일을 읽어서 ArrayList에 학생 정보를 로딩한다.
  }

  public boolean isChanged() {
    return changed;
  }

  @SuppressWarnings("unchecked")
  private void load() {
    FileInputStream in0 = null;
    ObjectInputStream in = null;
    
    try {
      in0 = new FileInputStream(this.filename);
      in = new ObjectInputStream(in0);

      list = (ArrayList<Student>)in.readObject();
      
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
    ObjectOutputStream out = new ObjectOutputStream(out0);

    out.writeObject(list);
    
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
        student.getUserId(),
        student.getPassword(),
        student.getName(),
        student.getTel(),
        student.getEmail(),
        ((student.isWorking())?"yes":"no"),
        student.getBirthYear(),
        student.getSchool());
    }
  }

  // update?userId=...&
  private void doUpdate() {
    System.out.print("변경할 학생의 인덱스? ");
    int index = Integer.parseInt(this.keyScan.nextLine());

    Student oldStudent = list.get(index);

    // 새 학생 정보를 입력 받는다.
    Student student = new Student();
    System.out.print("암호(예:1111)? ");
    student.setPassword(this.keyScan.nextLine());

    System.out.printf("이름(%s)? ", oldStudent.getName());
    student.setName(this.keyScan.nextLine());

    System.out.printf("전화(%s)? ", oldStudent.getTel());
    student.setTel(this.keyScan.nextLine());

    System.out.printf("이메일(%s)? ", oldStudent.getEmail());
    student.setEmail(this.keyScan.nextLine());

    System.out.print("재직중(y/n)? ");
    student.setWorking(this.keyScan.nextLine().equals("y") ? true : false);

    while (true) {
      try {
        System.out.printf("태어난해(%d)? ", oldStudent.getBirthYear());
        student.setBirthYear(Integer.parseInt(this.keyScan.nextLine()));
        break;
      } catch (Exception e) {
        System.out.println("정수 값을 입력하세요.");
      }
    }

    System.out.printf("최종학교(%s)? ", oldStudent.getSchool());
    student.setSchool(this.keyScan.nextLine());

    System.out.print("저장하시겠습니까(y/n)? ");
    if (keyScan.nextLine().toLowerCase().equals("y")) {
      student.setUserId(oldStudent.getUserId());
      list.set(index, student);
      changed = true;
      System.out.println("저장하였습니다.");
    } else {
      System.out.println("변경을 취소하였습니다.");
    }
  }

  // add?userId=...&
  private void doAdd() {
    while (true) {
      Student student = new Student();
      System.out.print("아이디(:hong)? ");
      student.setUserId(this.keyScan.nextLine());

      System.out.print("암호(예:1111)? ");
      student.setPassword(this.keyScan.nextLine());

      System.out.print("이름(예:홍길동)? ");
      student.setName(this.keyScan.nextLine());

      System.out.print("전화(예:010-1111-2222)? ");
      student.setTel(this.keyScan.nextLine());

      System.out.print("이메일(예:hong@test.com)? ");
      student.setEmail(this.keyScan.nextLine());

      System.out.print("재직중(y/n)? ");
      student.setWorking(this.keyScan.nextLine().equals("y") ? true : false);
      
      while (true) {
        try {      
          System.out.print("태어난해(예:1980)? ");
          student.setBirthYear(Integer.parseInt(this.keyScan.nextLine()));
          break;
        } catch (Exception e) {
          System.out.println("정수 값을 입력하세요.");
        }
      }
      
      System.out.print("최종학교(예:비트고등학교)? ");
      student.setSchool(this.keyScan.nextLine());

      list.add(student);
      changed = true;

      System.out.print("계속 입력하시겠습니까(y/n)? ");
      if (!this.keyScan.nextLine().equals("y"))
        break;
    } // while
  }

  // view?userId=hong
  private void doView() {
    System.out.print("학생의 인덱스? ");
    int index = Integer.parseInt(this.keyScan.nextLine());

    Student student = list.get(index);

    System.out.printf("아이디: %s\n", student.getUserId());
    System.out.printf("암호: (***)\n");
    System.out.printf("이름: %s\n", student.getName());
    System.out.printf("전화: %s\n", student.getTel());
    System.out.printf("이메일: %s\n", student.getEmail());
    System.out.printf("재직중: %s\n", (student.isWorking()) ? "Yes" : "No");
    System.out.printf("태어난 해: %d\n", student.getBirthYear());
    System.out.printf("학교: %s\n", student.getSchool());
  }

  // delete?userId=
  private void doDelete() {
    System.out.print("삭제할 학생의 인덱스? ");
    int index = Integer.parseInt(keyScan.nextLine());
    Student deletedStudent = list.remove(index);
    changed = true;
    System.out.printf("%s 학생 정보를 삭제하였습니다.\n", deletedStudent.getUserId());
  }
}
