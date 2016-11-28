package bitcamp.java89.ems.server.controller;

import java.io.PrintStream;
import java.util.HashMap;

import bitcamp.java89.ems.server.AbstractCommand;
import bitcamp.java89.ems.server.dao.StudentDao;
import bitcamp.java89.ems.server.vo.Student;

public class StudentUpdateController extends AbstractCommand {
  // 의존 객체 DAO를 저장할 변수 선언
  StudentDao studentDao;
  
  // 의존 객체 주입할 때 호출할 셋터 추가
  public void setStudentDao(StudentDao studentDao) {
    this.studentDao = studentDao;
  } 
  
  @Override
  public String getCommandString() {
    return "student/update";
  }
  
  //update?userId=aaa&password=1111&name=홍길동2&tel=1111-2222&email=hong2@test.com&working=n&birthYear=2000&school=비트대학
  @Override
  protected void doResponse(HashMap<String,String> paramMap, PrintStream out) 
      throws Exception {
    // 주입 받은 studentDao를 사용할 것이기 때문에 
    // 더이상 이 메서드에서 StudentDao 객체를 준비하지 않는다.
    // => 단 이 메서드가 호출되기 전에 반드시 StudentDao가 주입되어 있어야 한다.
    if (!studentDao.existUserId(paramMap.get("userId"))) {
      out.println("해당 아이디의 학생이 없습니다.");
      return;
    }
    
    Student student = new Student();
    student.setUserId(paramMap.get("userId"));
    student.setPassword(paramMap.get("password"));
    student.setName(paramMap.get("name"));
    student.setTel(paramMap.get("tel"));
    student.setEmail(paramMap.get("email"));
    student.setWorking(paramMap.get("working").equals("y") ? true : false);
    student.setBirthYear(Integer.parseInt(paramMap.get("birthYear")));
    student.setSchool(paramMap.get("school"));
    
    studentDao.update(student);
    out.println("학생 정보를 변경하였습니다.");
  }  
}
