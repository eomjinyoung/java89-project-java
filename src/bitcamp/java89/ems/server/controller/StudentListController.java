package bitcamp.java89.ems.server.controller;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

import bitcamp.java89.ems.server.Command;
import bitcamp.java89.ems.server.dao.StudentDao;
import bitcamp.java89.ems.server.vo.Student;

public class StudentListController implements Command {

  public void service(HashMap<String,String> paramMap, PrintStream out) {
    try {
      StudentDao studentDao = StudentDao.getInstance();
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
      
    } catch (Exception e) {
      out.println("작업중 예외가 발생하였습니다.");
      e.printStackTrace();
    }
  }
}
