package bitcamp.java89.ems.server.controller;

import java.io.PrintStream;
import java.util.HashMap;

import bitcamp.java89.ems.server.Command;
import bitcamp.java89.ems.server.dao.ContactDao;

public class ContactDeleteController implements Command {
  private ContactDao contactDao;

  public ContactDeleteController() {
    contactDao = ContactDao.getInstance();
  }

  //클라이언트에서 보낸 데이터 형식
  // => delete?email=hong@test.com
  public void service(HashMap<String,String> paramMap, PrintStream out) {
      
    if (!contactDao.existEmail(paramMap.get("email"))) {
      out.println("해당 데이터가 없습니다.");
      return;
    }
      
    contactDao.delete(paramMap.get("email"));
    out.println("해당 데이터를 삭제 완료하였습니다.");
  }
}




