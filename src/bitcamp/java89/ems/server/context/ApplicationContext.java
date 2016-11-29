/* 역할: 빈 컨테이너(Bean Container) == IoC 컨테이너 
 * => bean = object = instance
 * => 객체의 생성과 소멸을 관리한다.
 * => 또한 객체가 사용하는 의존 객체 주입을 수행한다.
 */
package bitcamp.java89.ems.server.context;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;

import bitcamp.java89.ems.server.AbstractCommand;

public class ApplicationContext {
  HashMap<String,Object> objPool = new HashMap<>();
  
  public ApplicationContext(String[] packages) {
    for (String packageName : packages) {
      // 클래스 파일을 찾을 디렉토리 경로를 정의한다.
      // 그런데 파라미터로 넘어오는 값은 순수한 패키지 이름
      // (예: bitcamp.java89.ems.server.controller)으로 되어 있다.
      // 그래서 다음과 같이 파일 경로로 만들려면 "."을 "/"로 변경해야 한다.
      // 예) ./bin/bitcamp/java89/ems/server/controller
      try {
        File dir = new File("./bin/" + packageName.replaceAll("\\.", "/"));
        System.out.println(dir.getCanonicalPath());
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
  
  public void getClasses(File dir, ArrayList<Class<?>> classList) {
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
        getClasses(file, classList);
        
      } else {
        try {
          Class<?> c = getClass(file);
          if (!isAbstract(c)) {
            classList.add(c); 
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
  }

  private Class<?> getClass(File file) throws IOException, ClassNotFoundException {
    String path = file.getCanonicalPath().replaceAll("\\\\", "/")
        .replaceAll(".class", ""); 
    int pos = path.indexOf("/bin/");  
    return Class.forName(path.substring(pos + 5).replaceAll("/", "."));
  }
  
  private boolean isAbstract(Class<?> clazz) {
    if ((clazz.getModifiers() & Modifier.ABSTRACT) == Modifier.ABSTRACT) {
      return true;
    }
    return false;
  }
  
  public static void main(String[] args) throws Exception {
    ApplicationContext appContext = new ApplicationContext(new String[]{
        "bitcamp.java89.ems.server.controller", 
        "bitcamp.java89.ems.server.dao"});
    
  }
}








