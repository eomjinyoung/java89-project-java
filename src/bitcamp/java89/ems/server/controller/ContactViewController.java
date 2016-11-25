package bitcamp.java89.ems.server.controller;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

import bitcamp.java89.ems.server.AbstractCommand;
import bitcamp.java89.ems.server.dao.ContactDao;
import bitcamp.java89.ems.server.vo.Contact;

public class ContactViewController extends AbstractCommand {
  @Override
  public String getCommandString() {
    return "contact/view";
  }
  
  //클라이언트에서 보낸 데이터 형식
  // => view?name=홍길동
  @Override
  protected void doResponse(HashMap<String,String> paramMap, PrintStream out) 
      throws Exception {
    ContactDao contactDao = ContactDao.getInstance(); 
    ArrayList<Contact> list = contactDao.getListByName(paramMap.get("name"));
    for (Contact contact : list) {
      out.println("--------------------------");
      out.printf("이름: %s\n", contact.getName());
      out.printf("직위: %s\n", contact.getPosition());
      out.printf("전화: %s\n", contact.getTel());
      out.printf("이메일: %s\n", contact.getEmail());
    }
  }
}




