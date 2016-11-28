package bitcamp.java89.ems.server.controller;

import java.io.PrintStream;
import java.util.HashMap;

import bitcamp.java89.ems.server.AbstractCommand;
import bitcamp.java89.ems.server.dao.ContactDao;
import bitcamp.java89.ems.server.vo.Contact;

public class ContactUpdateController extends AbstractCommand {
  // 의존 객체 DAO를 저장할 변수 선언
  ContactDao contactDao;
  
  // 의존 객체 주입할 때 호출할 셋터 추가
  public void setContactDao(ContactDao contactDao) {
    this.contactDao = contactDao;
  }
  
  @Override
  public String getCommandString() {
    return "contact/update";
  }
  
  //클라이언트에서 보낸 데이터 형식
  // => update?name=홍길동&position=대리&tel=111-1111&email=hong@test.com
  // 이메일이 일치하는 사용자를 찾아 나머지 항목의 값을 변경한다.
  // 단 이메일은 변경할 수 없다.
  @Override
  protected void doResponse(HashMap<String,String> paramMap, PrintStream out) 
      throws Exception {
    // 주입 받은 contactDao를 사용할 것이기 때문에 
    // 더이상 이 메서드에서 ContactDao 객체를 준비하지 않는다.
    // => 단 이 메서드가 호출되기 전에 반드시 ContactDao가 주입되어 있어야 한다.
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
}




