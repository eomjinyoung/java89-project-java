package bitcamp.java89.ems.server.controller;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

import bitcamp.java89.ems.server.annotation.Component;
import bitcamp.java89.ems.server.annotation.RequestMapping;
import bitcamp.java89.ems.server.dao.StudentDao;
import bitcamp.java89.ems.server.vo.Student;

@Component // ApplicationContext가 관리하는 대상 클래스임을 태깅한다.
public class StudentController {
  // 의존 객체 DAO를 저장할 변수 선언
  StudentDao studentDao;
  
  // 의존 객체 주입할 때 호출할 셋터 추가
  public void setStudentDao(StudentDao studentDao) {
    this.studentDao = studentDao;
  } 
  
  //add?userId=aaa&password=1111&name=홍길동&tel=1111-1111&email=hong@test.com&working=y&birthYear=1999&school=비트대학
  @RequestMapping(value="student/add")
  public void add(HashMap<String,String> paramMap, PrintStream out) 
      throws Exception {
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
  
  //delete?userId=aaa
  @RequestMapping(value="student/delete")
  public void delete(HashMap<String,String> paramMap, PrintStream out) 
      throws Exception {
    if (!studentDao.existUserId(paramMap.get("userId"))) {
      out.println("해당 아이디의 학생이 없습니다.");
      return;
    }
    
    studentDao.delete(paramMap.get("userId"));
    out.printf("%s 학생 정보를 삭제하였습니다.\n", paramMap.get("userId"));
  }
  
  @RequestMapping(value="student/list")
  public void list(PrintStream out) 
      throws Exception {
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
  
  //update?userId=aaa&password=1111&name=홍길동2&tel=1111-2222&email=hong2@test.com&working=n&birthYear=2000&school=비트대학
  @RequestMapping(value="student/update")
  public void update(HashMap<String,String> paramMap, PrintStream out) 
      throws Exception {
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

  //view?userId=aaa
  @RequestMapping(value="student/view")
  public void view(HashMap<String,String> paramMap, PrintStream out) 
      throws Exception {
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
