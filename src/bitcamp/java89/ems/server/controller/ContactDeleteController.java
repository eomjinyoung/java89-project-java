package bitcamp.java89.ems.server.controller;

import java.io.PrintStream;
import java.util.HashMap;

import bitcamp.java89.ems.server.AbstractCommand;
import bitcamp.java89.ems.server.dao.ContactDao;

public class ContactDeleteController extends AbstractCommand {
  // 의존 객체 DAO를 저장할 변수 선언
  ContactDao contactDao;
  
  // 의존 객체 주입할 때 호출할 셋터 추가
  public void setContactDao(ContactDao contactDao) {
    this.contactDao = contactDao;
  }
  
  @Override
  public String getCommandString() {
    return "contact/delete";
  }

  //클라이언트에서 보낸 데이터 형식
  // => delete?email=hong@test.com
  @Override
  protected void doResponse(HashMap<String,String> paramMap, PrintStream out) 
      throws Exception {
    // 주입 받은 contactDao를 사용할 것이기 때문에 
    // 더이상 이 메서드에서 ContactDao 객체를 준비하지 않는다.
    // => 단 이 메서드가 호출되기 전에 반드시 ContactDao가 주입되어 있어야 한다.
    if (!contactDao.existEmail(paramMap.get("email"))) {
      out.println("해당 데이터가 없습니다.");
      return;
    }
      
    contactDao.delete(paramMap.get("email"));
    out.println("해당 데이터를 삭제 완료하였습니다.");
  }
}




