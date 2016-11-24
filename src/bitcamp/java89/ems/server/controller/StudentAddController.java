package bitcamp.java89.ems.server.controller;

import java.io.PrintStream;
import java.util.HashMap;

import bitcamp.java89.ems.server.Command;
import bitcamp.java89.ems.server.dao.StudentDao;
import bitcamp.java89.ems.server.vo.Student;

public class StudentAddController implements Command {
  private StudentDao studentDao;

  public StudentAddController() {
    studentDao = StudentDao.getInstance();
  }

  //add?userId=aaa&password=1111&name=홍길동&tel=1111-1111&email=hong@test.com&working=y&birthYear=1999&school=비트대학
  public void service(HashMap<String,String> paramMap, PrintStream out) {
    if (studentDao.existUserId(paramMap.get("userId"))) {
      out.println("이미 해당 아이디의 학생이 있습니다. 등록을 취소하겠습니다.");
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

    studentDao.insert(student);
    out.println("등록하였습니다.");
  }
}
