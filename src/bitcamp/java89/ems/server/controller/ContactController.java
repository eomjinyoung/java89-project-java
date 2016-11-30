package bitcamp.java89.ems.server.controller;

import java.io.PrintStream;
import java.util.ArrayList;

import bitcamp.java89.ems.server.annotation.Component;
import bitcamp.java89.ems.server.annotation.RequestMapping;
import bitcamp.java89.ems.server.annotation.RequestParam;
import bitcamp.java89.ems.server.dao.ContactDao;
import bitcamp.java89.ems.server.vo.Contact;

@Component // ApplicationContext가 관리하는 대상 클래스임을 태깅한다.
public class ContactController {
  // 의존 객체 DAO를 저장할 변수 선언
  ContactDao contactDao;
  
  // 의존 객체 주입할 때 호출할 셋터 추가
  public void setContactDao(ContactDao contactDao) {
    this.contactDao = contactDao;
  }
  
  // => add?name=홍길동&position=대리&tel=111-1111&email=hong@test.com
  @RequestMapping(value="contact/add")
  public void add(
      @RequestParam("name") String name,
      @RequestParam("tel") String tel,
      @RequestParam("position") String position,
      @RequestParam("email") String email,
      PrintStream out) 
      throws Exception {
    if (contactDao.existEmail(email)) {
      out.println("같은 이메일이 존재합니다. 등록을 취소합니다.");
      return;
    }
    
    Contact contact = new Contact();
    contact.setName(name);
    contact.setPosition(position);
    contact.setTel(tel);
    contact.setEmail(email);
    
    contactDao.insert(contact);
    out.println("등록하였습니다.");
  }
  
  //클라이언트에서 보낸 데이터 형식
  // => delete?email=hong@test.com
  @RequestMapping(value="contact/delete")
  public void delete(@RequestParam("email") String email, PrintStream out) 
      throws Exception {
    if (!contactDao.existEmail(email)) {
      out.println("해당 데이터가 없습니다.");
      return;
    }
      
    contactDao.delete(email);
    out.println("해당 데이터를 삭제 완료하였습니다.");
  }
  
  @RequestMapping(value="contact/list")
  public void list(PrintStream out) 
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
  
  //클라이언트에서 보낸 데이터 형식
  // => update?name=홍길동&position=대리&tel=111-1111&email=hong@test.com
  // 이메일이 일치하는 사용자를 찾아 나머지 항목의 값을 변경한다.
  // 단 이메일은 변경할 수 없다.
  @RequestMapping(value="contact/update")
  public void update(
      @RequestParam("name") String name,
      @RequestParam("tel") String tel,
      @RequestParam("position") String position,
      @RequestParam("email") String email,
      PrintStream out) 
      throws Exception {
    if (!contactDao.existEmail(email)) {
      out.println("이메일을 찾지 못했습니다.");
      return;
    }
    
    Contact contact = new Contact();
    contact.setEmail(email);
    contact.setName(name);
    contact.setPosition(position);
    contact.setTel(tel);
    
    contactDao.update(contact);
    out.println("변경 하였습니다.");
  }
  
  //클라이언트에서 보낸 데이터 형식
  // => view?name=홍길동
  @RequestMapping(value="contact/view")
  public void view(@RequestParam("name") String name, PrintStream out) 
      throws Exception {
    ArrayList<Contact> list = contactDao.getListByName(name);
    for (Contact contact : list) {
      out.println("--------------------------");
      out.printf("이름: %s\n", contact.getName());
      out.printf("직위: %s\n", contact.getPosition());
      out.printf("전화: %s\n", contact.getTel());
      out.printf("이메일: %s\n", contact.getEmail());
    }
  }
}




