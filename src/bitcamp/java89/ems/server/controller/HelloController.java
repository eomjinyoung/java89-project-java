package bitcamp.java89.ems.server.controller;

import java.io.PrintStream;
import java.util.HashMap;

import bitcamp.java89.ems.server.AbstractCommand;

public class HelloController extends AbstractCommand {
  @Override
  public String getCommandString() {
    return "hello";
  }

  @Override
  protected void doResponse(HashMap<String, String> paramMap, PrintStream out) throws Exception {
    out.println("안뇽?뿅");
  }
}
