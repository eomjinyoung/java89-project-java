package bitcamp.java89.ems.server.controller;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

import bitcamp.java89.ems.server.AbstractCommand;
import bitcamp.java89.ems.server.dao.StudentDao;
import bitcamp.java89.ems.server.vo.Student;

public class Hello2Controller extends AbstractCommand {
  StudentDao studentDao;
  
  public void setStudentDao(StudentDao studentDao) {
    this.studentDao = studentDao;
  }
  
  @Override
  public String getCommandString() {
    return "hello2";
  }

  @Override
  protected void doResponse(HashMap<String, String> paramMap, PrintStream out) throws Exception {
    ArrayList<Student> list = studentDao.getList();
    for (Student s : list) {
      out.println(s.getName());
    }
  }
}








