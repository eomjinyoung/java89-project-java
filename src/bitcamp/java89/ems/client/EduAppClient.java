package bitcamp.java89.ems.client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class EduAppClient {
  public static void main(String[] args) {
    Scanner keyScan = new Scanner(System.in);
    System.out.print("서버주소? ");
    String serverAddr = keyScan.nextLine();
    
    try (
        Socket socket = new Socket(serverAddr, 8888);
        Scanner in = new Scanner(new BufferedInputStream(socket.getInputStream()));
        PrintStream out = new PrintStream(
            new BufferedOutputStream(socket.getOutputStream()), true)) {
      
      while (true) {
        // 서버가 보낸 데이터를 읽는다. 빈 줄을 입력 받을 때까지.
        boolean firstLine = true;
        while (true) {
          String message = in.nextLine();
          if (message.length() == 0) {
            break;
          }
          System.out.printf("%s%s", ((firstLine)?"":"\n"), message);
          firstLine = false;
        }
        
        // 사용자로부터 명령을 입력 받아 출력한다.
        String command = keyScan.nextLine();
        out.println(command);
        
        if (command.toLowerCase().equals("quit")) {
          break;
        }
      }
      
    } catch (Exception e) {
      e.printStackTrace();
      
    } finally {
      keyScan.close();
    }
  }
}









