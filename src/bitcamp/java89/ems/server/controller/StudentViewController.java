package bitcamp.java89.ems.server.controller;

import java.io.PrintStream;
import java.util.HashMap;

import bitcamp.java89.ems.server.Command;
import bitcamp.java89.ems.server.dao.StudentDao;
import bitcamp.java89.ems.server.vo.Student;

public class StudentViewController implements Command {
  private StudentDao studentDao;

  public StudentViewController() {
    studentDao = StudentDao.getInstance();
  }

  //view?userId=aaa
  public void service(HashMap<String,String> paramMap, PrintStream out) {
    if (!studentDao.existUserId(paramMap.get("userId"))) {
      out.println("해당 아이디의 학생이 없습니다.");
      return;
    }
    
    Student student = studentDao.getOne(paramMap.get("userId"));
    out.printf("아이디: %s\n", student.getUserId());
    out.printf("암호: (***)\n");
    out.printf("이름: %s\n", student.getName());
    out.printf("전화: %s\n", student.getTel());
    out.printf("이메일: %s\n", student.getEmail());
    out.printf("재직중: %s\n", (student.isWorking()) ? "Yes" : "No");
    out.printf("태어난 해: %d\n", student.getBirthYear());
    out.printf("학교: %s\n", student.getSchool());
  }
}
