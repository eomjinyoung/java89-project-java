package bitcamp.java89.ems.server;

import java.net.ServerSocket;
import java.util.ArrayList;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import bitcamp.java89.ems.server.context.RequestHandlerMapping;

public class EduAppServer {
  // IoC 컨테이너
  ApplicationContext appContext;
  RequestHandlerMapping handlerMapping;
  
  public EduAppServer() {
    
    // 설정 파일의 정보를 보고 클래스를 찾아 객체를 생성한다.
    appContext = new ClassPathXmlApplicationContext(
        new String[] {"bitcamp/java89/ems/server/application-context.xml"} // 스프링 설정파일 경로정보
    );
    
    // 스프링 IoC 컨테이너에 들어있는 객체들의 이름을 가져온다.
    String[] names = appContext.getBeanDefinitionNames();
    
    // 객체를 저장할 바구니를 준비한다.
    ArrayList<Object> objList = new ArrayList<>();
    for (String name : names) {
      System.out.println(name);
      objList.add(appContext.getBean(name)); // 이름으로 객체를 찾아 objList에 담는다.
    }
    
    // 객체를 조사하여 @RequestMapping이 붙은 메서드를 따로 관리한다.
    handlerMapping = new RequestHandlerMapping(objList);
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

