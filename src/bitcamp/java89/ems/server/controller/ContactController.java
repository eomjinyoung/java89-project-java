package bitcamp.java89.ems.server.controller;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import bitcamp.java89.ems.server.dao.ContactDao;
import bitcamp.java89.ems.server.vo.Contact;

public class ContactController {
  private Scanner in;
  private PrintStream out;
  
  private ContactDao contactDao;

  public ContactController(Scanner in, PrintStream out) {
    this.in = in;
    this.out = out;

    contactDao = ContactDao.getInstance();
  }

  public void save() throws Exception {
    if (contactDao.isChanged()) {
      contactDao.save();
    }
  }

  public boolean service() {
    while (true) {
      // 클라이언트로 데이터 출력 
      out.println("연락처관리> ");
      out.println(); // 보내는 데이터의 끝을 의미
      
      // 클라이언트가 보낸 데이터 읽기
      String[] commands = in.nextLine().split("\\?");

      try {
        switch (commands[0]) {
        case "add": this.doAdd(commands[1]); break;
        case "list": this.doList(); break;
        case "view": this.doView(commands[1]); break;
        case "delete": this.doDelete(commands[1]); break;
        case "update": this.doUpdate(commands[1]); break;
        case "main": return true;
        case "quit": return false;
        default:
          out.println("지원하지 않는 명령어입니다.");
        }
      } catch (IndexOutOfBoundsException e) {
        out.println("인덱스가 유효하지 않습니다.");
      } catch (Exception e) {
        out.println("실행 중 오류가 발생했습니다.");
        e.printStackTrace();
      } // try
    } // while
  }
  
  
  private void doList() {
    ArrayList<Contact> list = contactDao.getList();
    for (Contact contact : list) {
      out.printf("%s,%s,%s,%s\n",
        contact.getName(),
        contact.getPosition(),
        contact.getTel(),
        contact.getEmail());
    }
  }
  
  //클라이언트에서 보낸 데이터 형식
  // => update?name=홍길동&position=대리&tel=111-1111&email=hong@test.com
  // 이메일이 일치하는 사용자를 찾아 나머지 항목의 값을 변경한다.
  // 단 이메일은 변경할 수 없다.
  private void doUpdate(String params) {
    String[] values = params.split("&");
    HashMap<String,String> paramMap = new HashMap<>();
    
    for (String value : values) {
      String[] kv = value.split("=");
      paramMap.put(kv[0], kv[1]);
    }
    
    if (!contactDao.existEmail(paramMap.get("email"))) {
      out.println("이메일을 찾지 못했습니다.");
      return;
    }
    
    Contact contact = new Contact();
    contact.setEmail(paramMap.get("email"));
    contact.setName(paramMap.get("name"));
    contact.setPosition(paramMap.get("position"));
    contact.setTel(paramMap.get("tel"));
    
    contactDao.update(contact);
    out.println("변경 하였습니다.");
  }
  
  // 클라이언트에서 보낸 데이터 형식
  // => add?name=홍길동&position=대리&tel=111-1111&email=hong@test.com
  // => doAdd("name=홍길동&position=대리&tel=111-1111&email=hong@test.com") 
  private void doAdd(String params) {
    String[] values = params.split("&");
    HashMap<String,String> paramMap = new HashMap<>();
    
    for (String value : values) {
      String[] kv = value.split("=");
      paramMap.put(kv[0], kv[1]);
    }
    
    if (contactDao.existEmail(paramMap.get("email"))) {
      out.println("같은 이메일이 존재합니다. 등록을 취소합니다.");
      return;
    }
    
    Contact contact = new Contact();
    contact.setName(paramMap.get("name"));
    contact.setPosition(paramMap.get("position"));
    contact.setTel(paramMap.get("tel"));
    contact.setEmail(paramMap.get("email"));
    
    contactDao.insert(contact);
    out.println("등록하였습니다.");
  }

  // 클라이언트에서 보낸 데이터 형식
  // => view?name=홍길동
  private void doView(String params) {
    String[] kv = params.split("=");
    
    ArrayList<Contact> list = contactDao.getListByName(kv[1]);
    for (Contact contact : list) {
      out.println("--------------------------");
      out.printf("이름: %s\n", contact.getName());
      out.printf("직위: %s\n", contact.getPosition());
      out.printf("전화: %s\n", contact.getTel());
      out.printf("이메일: %s\n", contact.getEmail());
    }
  }

  // 클라이언트에서 보낸 데이터 형식
  // => delete?email=hong@test.com
  private void doDelete(String params) { 
    String[] kv = params.split("=");
    
    if (!contactDao.existEmail(kv[1])) {
      out.println("해당 데이터가 없습니다.");
      return;
    }
    
    contactDao.delete(kv[1]);
    out.println("해당 데이터를 삭제 완료하였습니다.");
  }
}




