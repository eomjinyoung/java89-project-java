package bitcamp.java89.ems.server.controller;

import java.io.PrintStream;
import java.util.HashMap;

import bitcamp.java89.ems.server.AbstractCommand;
import bitcamp.java89.ems.server.dao.ContactDao;

public class ContactDeleteController extends AbstractCommand {
  @Override
  public String getCommandString() {
    return "contact/delete";
  }

  //클라이언트에서 보낸 데이터 형식
  // => delete?email=hong@test.com
  @Override
  protected void doResponse(HashMap<String,String> paramMap, PrintStream out) 
      throws Exception {
    ContactDao contactDao = ContactDao.getInstance(); 
    if (!contactDao.existEmail(paramMap.get("email"))) {
      out.println("해당 데이터가 없습니다.");
      return;
    }
      
    contactDao.delete(paramMap.get("email"));
    out.println("해당 데이터를 삭제 완료하였습니다.");
  }
}




