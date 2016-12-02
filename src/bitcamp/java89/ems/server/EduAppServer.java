package bitcamp.java89.ems.server;

import java.net.ServerSocket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;

import bitcamp.java89.ems.server.context.ApplicationContext;
import bitcamp.java89.ems.server.context.RequestHandlerMapping;

public class EduAppServer {
  // IoC 컨테이너
  ApplicationContext appContext;
  RequestHandlerMapping handlerMapping;
  
  public EduAppServer() {
    // ApplicationContext가 만들지 못하는 객체를 여기에서 미리 생성하여 
    // ApplicationContext에게 전달한다.
    HashMap<String,Object> builtInObjMap = new HashMap<>();
    
    try {
      Class.forName("com.mysql.jdbc.Driver");
      Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/java89db", 
          "java89", "1111");
      builtInObjMap.put("dbcon", con);
      
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    // 지정된 패키지의 클래스들을 조사하여 객체를 생성한다.
    appContext = new ApplicationContext(new String[]{
        "bitcamp.java89.ems.server.controller", 
        "bitcamp.java89.ems.server.dao"}, builtInObjMap);
    
    // 객체를 조사하여 @RequestMapping이 붙은 메서드를 따로 관리한다.
    handlerMapping = new RequestHandlerMapping(appContext.getAllBeans());
  }
  
  private void service() throws Exception {
    ServerSocket ss = new ServerSocket(8888);
    System.out.println("서버 실행 중...");
    
    while (true) {
      new RequestThread(ss.accept(), handlerMapping).start();
    }
    //ss.close();
  }

  public static void main(String[] args) throws Exception {
    EduAppServer eduServer = new EduAppServer();
    eduServer.service();
  }
}

