package bitcamp.java89.ems.server.controller;

import java.io.PrintStream;
import java.util.HashMap;

import bitcamp.java89.ems.server.annotation.Component;
import bitcamp.java89.ems.server.annotation.RequestMapping;
import bitcamp.java89.ems.server.dao.StudentDao;

@Component(value="student/delete") // ApplicationContext가 관리하는 대상 클래스임을 태깅한다.
public class StudentDeleteController {
  // 의존 객체 DAO를 저장할 변수 선언
  StudentDao studentDao;
  
  // 의존 객체 주입할 때 호출할 셋터 추가
  public void setStudentDao(StudentDao studentDao) {
    this.studentDao = studentDao;
  } 
  
  //delete?userId=aaa
  @RequestMapping
  public void delete(HashMap<String,String> paramMap, PrintStream out) 
      throws Exception {
    if (!studentDao.existUserId(paramMap.get("userId"))) {
      out.println("해당 아이디의 학생이 없습니다.");
      return;
    }
    
    studentDao.delete(paramMap.get("userId"));
    out.printf("%s 학생 정보를 삭제하였습니다.\n", paramMap.get("userId"));
  }
}
