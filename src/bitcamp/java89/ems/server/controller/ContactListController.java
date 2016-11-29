package bitcamp.java89.ems.server.controller;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

import bitcamp.java89.ems.server.annotation.Component;
import bitcamp.java89.ems.server.annotation.RequestMapping;
import bitcamp.java89.ems.server.dao.ContactDao;
import bitcamp.java89.ems.server.vo.Contact;

@Component(value="contact/list") // ApplicationContext가 관리하는 클래스임을 표시한다.
public class ContactListController {
  // 의존 객체 DAO를 저장할 변수 선언
  ContactDao contactDao;
  
  // 의존 객체 주입할 때 호출할 셋터 추가
  public void setContactDao(ContactDao contactDao) {
    this.contactDao = contactDao;
  }
  
  @RequestMapping
  public void list(HashMap<String,String> paramMap, PrintStream out) 
      throws Exception {
    ArrayList<Contact> list = contactDao.getList();
    for (Contact contact : list) {
      out.printf("%s,%s,%s,%s\n",
        contact.getName(),
        contact.getPosition(),
        contact.getTel(),
        contact.getEmail());
    }
  }
}




