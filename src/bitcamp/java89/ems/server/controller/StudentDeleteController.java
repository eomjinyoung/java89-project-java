package bitcamp.java89.ems.server.controller;

import java.io.PrintStream;
import java.util.HashMap;

import bitcamp.java89.ems.server.Command;
import bitcamp.java89.ems.server.dao.StudentDao;

public class StudentDeleteController implements Command {
  private StudentDao studentDao;

  public StudentDeleteController() {
    studentDao = StudentDao.getInstance();
  }

  //delete?userId=aaa
  public void service(HashMap<String,String> paramMap, PrintStream out) {
    if (!studentDao.existUserId(paramMap.get("userId"))) {
      out.println("해당 아이디의 학생이 없습니다.");
      return;
    }
    
    studentDao.delete(paramMap.get("userId"));
    out.printf("%s 학생 정보를 삭제하였습니다.\n", paramMap.get("userId"));
  }
}
