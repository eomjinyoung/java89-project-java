package bitcamp.java89.ems.server.controller;

import java.io.PrintStream;
import java.util.HashMap;

import bitcamp.java89.ems.server.Command;
import bitcamp.java89.ems.server.dao.ContactDao;
import bitcamp.java89.ems.server.vo.Contact;

public class ContactAddController implements Command {
  private ContactDao contactDao;

  public ContactAddController() {
    contactDao = ContactDao.getInstance();
  }

  //클라이언트에서 보낸 데이터 형식
  // => add?name=홍길동&position=대리&tel=111-1111&email=hong@test.com
  public void service(HashMap<String,String> paramMap, PrintStream out) {
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
}




