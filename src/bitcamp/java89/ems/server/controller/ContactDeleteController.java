package bitcamp.java89.ems.server.controller;

import java.io.PrintStream;
import java.util.HashMap;

import bitcamp.java89.ems.server.annotation.Component;
import bitcamp.java89.ems.server.annotation.RequestMapping;
import bitcamp.java89.ems.server.dao.ContactDao;

@Component(value="contact/delete") // ApplicationContext가 관리하는 클래스임을 표시한다.
public class ContactDeleteController {
  // 의존 객체 DAO를 저장할 변수 선언
  ContactDao contactDao;
  
  // 의존 객체 주입할 때 호출할 셋터 추가
  public void setContactDao(ContactDao contactDao) {
    this.contactDao = contactDao;
  }
  
  //클라이언트에서 보낸 데이터 형식
  // => delete?email=hong@test.com
  @RequestMapping
  public void delete(HashMap<String,String> paramMap, PrintStream out) 
      throws Exception {
    if (!contactDao.existEmail(paramMap.get("email"))) {
      out.println("해당 데이터가 없습니다.");
      return;
    }
      
    contactDao.delete(paramMap.get("email"));
    out.println("해당 데이터를 삭제 완료하였습니다.");
  }
}




