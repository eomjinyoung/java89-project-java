package bitcamp.java89.ems.server;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

public class ReflectionUtil {
  public static void getCommandClasses(File dir, ArrayList<Class> classList) {
    if (!dir.isDirectory()) {
      return;
    }
    
    File[] files = dir.listFiles(new FileFilter() {
      @Override
      public boolean accept(File pathname) {
        if (pathname.isDirectory())
          return true;
        if (pathname.getName().endsWith(".class") && !pathname.getName().contains("$"))
          return true;
        return false;
      }
    });
    
    for (File file : files) {
      if (file.isDirectory()) {
        getCommandClasses(file, classList);
      } else {
        // 파일명에서 "\\"를 "/"로 바꿔서 OS 간의 차이가 없도록 한다.
        String path = file.getAbsolutePath().replaceAll("\\\\", "/")
                                            .replaceAll(".class", ""); // .class 확장자 제거
        // "패키지명 + 클래스명" 만 추출
        int pos = path.indexOf("/bin/");  
        try {
          // "패키지명 + 클래스명"에 해당하는 클래스 파일을 찾아서 Method Aread 영역에 로딩한다.
          // => 리턴 값은 로딩된 클래스의 정보이다.
          //System.out.println(path); // 클래스 파일 절대 경로
          String classname = path.substring(pos + 5).replaceAll("/", "."); // Fully-qualified class name
          Class c = Class.forName(classname);
          
          // 로딩된 클래스가 AbstractCommand를 상속 받았는지 검사한다.
          Class superClass = c.getSuperclass();
          
          if (superClass == AbstractCommand.class) {
            //System.out.println("=>>>" + c.getName());
            classList.add(c); // AbstractCommand의 서브 클래스라면 목록에 저장한다.
          }
          
        } catch (Exception e) {
          // 만약 클래스를 로딩하지 못하면 무시한다.
          e.printStackTrace();
        }
      }
    }
  }
  
  

}






