package bitcamp.java89.ems.server.controller;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

import bitcamp.java89.ems.server.AbstractCommand;
import bitcamp.java89.ems.server.dao.StudentDao;
import bitcamp.java89.ems.server.vo.Student;

public class StudentListController extends AbstractCommand {
  // 의존 객체 DAO를 저장할 변수 선언
  StudentDao studentDao;
  
  // 의존 객체 주입할 때 호출할 셋터 추가
  public void setStudentDao(StudentDao studentDao) {
    this.studentDao = studentDao;
  } 
  
  @Override
  public String getCommandString() {
    return "student/list";
  }
  
  @Override
  protected void doResponse(HashMap<String,String> paramMap, PrintStream out) 
      throws Exception {
    // 주입 받은 studentDao를 사용할 것이기 때문에 
    // 더이상 이 메서드에서 StudentDao 객체를 준비하지 않는다.
    // => 단 이 메서드가 호출되기 전에 반드시 StudentDao가 주입되어 있어야 한다.
    ArrayList<Student> list = studentDao.getList();
    for (Student student : list) {
      out.printf("%s,%s,%s,%s,%s,%s,%d,%s\n",
        student.getUserId(),
        student.getPassword(),
        student.getName(),
        student.getTel(),
        student.getEmail(),
        ((student.isWorking())?"yes":"no"),
        student.getBirthYear(),
        student.getSchool());
    }
  }
}
