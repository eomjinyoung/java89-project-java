package bitcamp.java89.ems.server.controller;

import java.io.PrintStream;
import java.util.HashMap;

import bitcamp.java89.ems.server.AbstractCommand;
import bitcamp.java89.ems.server.annotation.Component;

@Component(value="hello") // ApplicationContext가 관리하는 대상 클래스임을 태깅한다.
public class HelloController extends AbstractCommand {
  @Override
  protected void doResponse(HashMap<String, String> paramMap, PrintStream out) throws Exception {
    out.println("안뇽?뿅");
  }
}
