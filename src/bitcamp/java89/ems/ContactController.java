package bitcamp.java89.ems;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class ContactController {
  private String filename = "contactv1.5.data";
  private ArrayList<Contact> list;
  private boolean changed;
  private Scanner keyScan;

  public ContactController(Scanner keyScan) {
    list = new ArrayList<Contact>();
    this.keyScan = keyScan;

    this.load(); 
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

      list = (ArrayList<Contact>)in.readObject();
      
    } catch (EOFException e) {
      // 파일을 모두 읽었다.
    } catch (Exception e) {
      System.out.println("데이터 로딩 중 오류 발생!");
    } finally {
      try {
        in.close();
        in0.close();
      } catch (Exception e) {}
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
      System.out.print("연락처관리> ");
      String command = keyScan.nextLine().toLowerCase();

      try {
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
      } catch (IndexOutOfBoundsException e) {
        System.out.println("인덱스가 유효하지 않습니다.");
      } catch (Exception e) {
        System.out.println("실행 중 오류가 발생했습니다.");
        e.printStackTrace();
      } // try
    } // while
  }

  private void doList() {
    for (Contact contact : list) {
      System.out.printf("%s,%s,%s,%s\n",
        contact.getName(),
        contact.getPosition(),
        contact.getTel(),
        contact.getEmail());
    }
  }
/*
  private void doUpdate() {
    System.out.print("변경할 학생의 인덱스? ");
    int index = Integer.parseInt(this.keyScan.nextLine());

    Contact oldContact = list.get(index);

    // 새 학생 정보를 입력 받는다.
    Contact contact = new Contact();
    System.out.print("암호(예:1111)? ");
    contact.password = this.keyScan.nextLine();

    System.out.printf("이름(%s)? ", oldContact.name);
    contact.name = this.keyScan.nextLine();

    System.out.printf("전화(%s)? ", oldContact.tel);
    contact.tel = this.keyScan.nextLine();

    System.out.printf("이메일(%s)? ", oldContact.email);
    contact.email = this.keyScan.nextLine();

    System.out.print("재직중(y/n)? ");
    contact.working = (this.keyScan.nextLine().equals("y")) ? true : false;

    while (true) {
      try {
        System.out.printf("태어난해(%d)? ", oldContact.birthYear);
        contact.birthYear = Integer.parseInt(this.keyScan.nextLine());
        break;
      } catch (Exception e) {
        System.out.println("정수 값을 입력하세요.");
      }
    }

    System.out.printf("최종학교(%s)? ", oldContact.school);
    contact.school = this.keyScan.nextLine();

    System.out.print("저장하시겠습니까(y/n)? ");
    if (keyScan.nextLine().toLowerCase().equals("y")) {
      contact.userId = oldContact.userId;
      list.set(index, contact);
      changed = true;
      System.out.println("저장하였습니다.");
    } else {
      System.out.println("변경을 취소하였습니다.");
    }
  }
*/
  private void doAdd() {
    while (true) {
      Contact contact = new Contact();
      System.out.print("이름(예:홍길동)? ");
      contact.setName(this.keyScan.nextLine());

      System.out.print("직위(예:대리)? ");
      contact.setPosition(this.keyScan.nextLine());

      System.out.print("전화(예:010-1111-2222)? ");
      contact.setTel(this.keyScan.nextLine());

      System.out.print("이메일(예:hong@test.com)? ");
      contact.setEmail(this.keyScan.nextLine());
      
      boolean save = true;
      if (existEmail(contact.getEmail())) {
        System.out.print("같은 이메일이 존재합니다. 저장하시겠습니까?(y/n)");
        if (!keyScan.nextLine().toLowerCase().equals("y")) {
          save = false;
          System.out.println("저장을 취소하였습니다.");
        }
      }
      
      if (save) {
        list.add(contact);
        changed = true;
      }

      System.out.print("계속 입력하시겠습니까(y/n)? ");
      if (!this.keyScan.nextLine().equals("y"))
        break;
    } // while
  }

  private boolean existEmail(String email) {
    for (Contact contact : list) {
      if (contact.getEmail().toLowerCase().equals(email.toLowerCase())) {
        return true;
      }
    }
    return false;
  }
  
  private void doView() {
    System.out.print("이름? ");
    String name = this.keyScan.nextLine();

    for (Contact contact : list) {
      if (contact.getName().equals(name)) {
        System.out.println("--------------------------");
        System.out.printf("이름: %s\n", contact.getName());
        System.out.printf("직위: %s\n", contact.getPosition());
        System.out.printf("전화: %s\n", contact.getTel());
        System.out.printf("이메일: %s\n", contact.getEmail());
      }
    }
  }

  private void doDelete() { // 마지막 버전
    System.out.print("이름? ");
    String name = keyScan.nextLine();
    
    ArrayList<Contact> deleteList = searchByName(name);
    
    System.out.println("------------------------");
    for (int i = 0; i < deleteList.size(); i++) {
      Contact contact = deleteList.get(i);
      System.out.printf("%d. %s(%s)\n", (i + 1), contact.getName(), contact.getEmail());
    }
    System.out.println("------------------------");
    
    System.out.print("삭제할 연락처의 번호? ");
    int deleteNo = Integer.parseInt(keyScan.nextLine());
    
    if (deleteNo < 1 || deleteNo > deleteList.size()) {
      System.out.println("유효하지 않은 번호입니다.");
      return;
    }
    
    list.remove(deleteList.get(deleteNo - 1));
    changed = true;
    System.out.println("삭제하였습니다.");
  }
  
  private ArrayList<Contact> searchByName(String name) {
    ArrayList<Contact> searchList = new ArrayList<>();
    for (Contact contact : list) {
      if (contact.getName().toLowerCase().equals(name.toLowerCase())) {
        searchList.add(contact);
      }
    }
    return searchList;
  }
  
  /*
  private void doDelete() { // 방법1
    System.out.print("이름? ");
    String name = keyScan.nextLine();
    
    for (int i = list.size() - 1; i >= 0; i--) {
      if (list.get(i).getName().equals(name)) {
        list.remove(i);
      }
    }
    
    changed = true;
    System.out.println("삭제하였습니다.");
  }
  */
  /*
  private void doDelete() { // 방법2
    System.out.print("이름? ");
    String name = keyScan.nextLine();
    
    ArrayList<Contact> deleteList = new ArrayList<>();
    
    for (Contact contact : list) {
      if (contact.getName().equals(name)) {
        deleteList.add(contact);
      }
    }
    
    for (Contact contact : deleteList) {
      list.remove(contact);
    }
    
    changed = true;
    System.out.println("삭제하였습니다.");
  }
  */

}
