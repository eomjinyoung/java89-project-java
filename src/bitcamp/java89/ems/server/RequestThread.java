package bitcamp.java89.ems.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

import bitcamp.java89.ems.server.controller.ContactController;
import bitcamp.java89.ems.server.controller.StudentController;

public class RequestThread extends Thread {
  private Socket socket;
  private Scanner in;
  private PrintStream out;
  
  private StudentController studentController;
  private ContactController contactController;
  
  public RequestThread(Socket socket) {
    this.socket = socket;
  }
  
  @Override
  public void run() {
    // 스레드가 독립적으로 실행할 코드를 두는 곳.
    try {
      in = new Scanner(
        new BufferedInputStream(socket.getInputStream()));
      out = new PrintStream(
        new BufferedOutputStream(socket.getOutputStream()), true);
      
      studentController = new StudentController(in, out);
      contactController = new ContactController(in, out);
   
      out.println("비트캠프 관리시스템에 오신걸 환영합니다.");
   
      loop:
      while (true) {
        // 클라이언트에게 데이터를 전송한다.
        out.println("명령>");
        out.println(); // 빈 줄은 보내는 데이터의 끝을 의미한다.
        
        // 클라이언트로부터 명령을 읽는다.
        String command = in.nextLine().toLowerCase();
   
        boolean running = true;
        switch (command) {
        case "menu": doMenu(); break;
        case "go 1": running = studentController.service(); break;
        case "go 5": running = contactController.service(); break;
        case "save": doSave(); break;
        case "quit": 
          if (doQuit()) 
            break loop;
          break;
        default:
          out.println("지원하지 않는 명령어입니다.");
        }
        
        if (!running) {
          doQuit();
          break;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      // 클라이언트 요청 처리중 예외 발생!
    } finally {
      try {in.close();} catch (Exception e) {}
      try {out.close();} catch (Exception e) {}
      try {socket.close();} catch (Exception e) {}
    }
  }
  
  private void doMenu() {
    out.println("[메뉴]");
    out.println("1. 학생관리");
    //out.println("2. 강좌관리");
    //out.println("3. 교재관리");
    //out.println("4. 강의실관리");
    out.println("5. 연락처관리");
    out.println("메뉴 이동은 'go 메뉴번호'를 입력하세요.");
    out.println("[명령]");
    out.println("save   데이터 저장");
    out.println("quit   프로그램 종료");
  }

  private boolean doQuit() {
    doSave();
    System.out.println("클라이언트 연결 종료!");
    return true;
  }

  private void doSave() {
    try {
      studentController.save();
    } catch (Exception e) {
      System.out.println("학생 정보 저장 중에 오류가 발생했습니다.");
    }
    
    try {
      contactController.save();
    } catch (Exception e) {
      System.out.println("연락처 정보 저장 중에 오류가 발생했습니다.");
    }
  }
}











