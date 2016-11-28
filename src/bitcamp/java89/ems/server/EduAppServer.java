package bitcamp.java89.ems.server;

import java.io.File;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;

import bitcamp.java89.ems.server.dao.ContactDao;
import bitcamp.java89.ems.server.dao.StudentDao;

public class EduAppServer {
  // Command 구현체 보관소
  // => HashMap<명령문자열,요청처리객체> commandMap
  HashMap<String,Command> commandMap = new HashMap<>();
  
  public EduAppServer() {
    // Controller가 사용할 DAO 객체 준비
    ContactDao contactDao = new ContactDao();
    contactDao.setFilename("contact-v1.9.data");
    try {
      contactDao.load();
    } catch (Exception e) {
      System.out.println("연락처 로딩 중 오류 발생!");
    }
    
    StudentDao studentDao = new StudentDao();
    studentDao.setFilename("student-v1.9.data");
    try {
      studentDao.load();
    } catch (Exception e) {
      System.out.println("학생 정보 로딩 중 오류 발생!");
    }
    
    // bin 폴더를 뒤져서 AbstractCommand의 서브 클래스를 찾아낸다.
    ArrayList<Class> classList = new ArrayList<>();
    ReflectionUtil.getCommandClasses(new File("./bin"), classList);
    
    for (Class c : classList) {
      try {
        // 클라이언트 요청을 처리할 Command 객체 등록
        AbstractCommand command = (AbstractCommand)c.newInstance();
        
        // commandMap에 저장하기 전에 각 Controller에 대해 DAO를 주입한다.
        try {
          Method m = command.getClass().getMethod("setContactDao", ContactDao.class);
          m.invoke(command, contactDao);
        } catch (Exception e) {} // setContactDao() 가 없을 때 예외 발생 ==> 무시한다.
        
        try {
          Method m = command.getClass().getMethod("setStudentDao", StudentDao.class);
          m.invoke(command, studentDao);
        } catch (Exception e) {} // setStudentDao() 가 없을 때 예외 발생 ==> 무시한다.
        
        // 클라이언트 요청을 처리할 Controller를 맵에 등록한다.
        commandMap.put(command.getCommandString(), command);
        
      } catch (Exception e) {}
    }
  }
  
  private void service() throws Exception {
    ServerSocket ss = new ServerSocket(8888);
    System.out.println("서버 실행 중...");
    
    while (true) {
      new RequestThread(ss.accept(), commandMap).start();
    }
    //ss.close();
  }

  public static void main(String[] args) throws Exception {
    EduAppServer eduServer = new EduAppServer();
    eduServer.service();
  }
}

